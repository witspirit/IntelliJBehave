package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.JBehaveBundle;
import com.github.kumaraman21.intellijbehave.jbehave.core.steps.PatternVariantBuilder;
import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.github.kumaraman21.intellijbehave.parser.StoryElementType;
import com.github.kumaraman21.intellijbehave.parser.StoryFile;
import com.github.kumaraman21.intellijbehave.utility.DirectoryScope;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.lang.jvm.JvmModifier;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.TextOccurenceProcessor;
import com.intellij.psi.search.searches.AllClassesSearch;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import org.jbehave.core.Embeddable;
import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.When;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.intellij.openapi.application.ReadAction.compute;
import static com.intellij.openapi.util.text.StringUtil.isEmptyOrSpaces;

public final class JBehaveUtil {

    private static final Set<String> JBEHAVE_ANNOTATIONS_SET = Set.of(Given.class.getName(), When.class.getName(),
        Then.class.getName());

    /**
     * Returns if the provided annotation is one of {@link #JBEHAVE_ANNOTATIONS_SET}.
     */
    public static boolean isJBehaveStepAnnotation(@NotNull PsiAnnotation annotation) {
        String annotationName = compute(annotation::getQualifiedName);

        return annotationName != null && JBEHAVE_ANNOTATIONS_SET.contains(annotationName);
    }

    /**
     * Returns if the provided annotation is of type {@code annotationClass}.
     * <p>
     * For example, if the {@code @org.jbehave.core.annotations.Given} annotation is of type {@link Given}.
     */
    public static boolean isAnnotationOfClass(
        @NotNull PsiAnnotation annotation,
        @NotNull Class<? extends Annotation> annotationClass
    ) {
        String annotationName = compute(annotation::getQualifiedName);

        return annotationName != null && annotationName.equals(annotationClass.getName());
    }

    /**
     * Returns all {@code @Given}, {@code @When} and {@code @Then} step annotations on the provided {@code method}.
     */
    @NotNull
    private static List<PsiAnnotation> getJBehaveStepAnnotations(@NotNull PsiMethod method) {
        var annotations = compute(() -> method.getModifierList().getAnnotations());

        //Optimizations to avoid creating unnecessary Streams
        if (annotations.length == 0) {
            return Collections.emptyList();
        }
        if (annotations.length == 1 && JBehaveUtil.isJBehaveStepAnnotation(annotations[0])) {
            return Collections.singletonList(annotations[0]);
        }

        return Stream.of(annotations)
            .filter(JBehaveUtil::isJBehaveStepAnnotation)
            .collect(Collectors.toList());
    }

    /**
     * Returns if the provided method is step definition, meaning has at least one annotation that
     * <ul>
     *     <li>is annotated with one of {@link #JBEHAVE_ANNOTATIONS_SET},</li>
     *     <li>and its {@code value} attribute is not null.</li>
     * </ul>
     */
    public static boolean isStepDefinition(@NotNull PsiMethod method) {
        return ReadAction.compute(() -> {
            var jBehaveStepAnnotations = getJBehaveStepAnnotations(method);

            //Optimizations to avoid creating unnecessary Streams
            if (jBehaveStepAnnotations.isEmpty()) {
                return false;
            }
            if (jBehaveStepAnnotations.size() == 1) {
                var attributeValue = compute(() -> jBehaveStepAnnotations.getFirst().findAttributeValue("value"));
                return attributeValue != null;
            } else {
                return jBehaveStepAnnotations.stream()
                    .map(stepAnnotation -> compute(() -> stepAnnotation.findAttributeValue("value")))
                    .anyMatch(Objects::nonNull);
            }
        });
    }

    /**
     * Collects all possible step annotation pattern variants for {@code stepAnnotation},
     * as well as the {@link Alias} and {@link Aliases} annotations on the parent
     * step definition method.
     *
     * @param stepAnnotation annotation for one of {@link #JBEHAVE_ANNOTATIONS_SET}
     * @return the collection of step patterns
     */
    @NotNull
    public static Set<String> getAnnotationTexts(
        @NotNull PsiAnnotation stepAnnotation,
        @Nullable PsiMethod parentMethod
    ) {
        var annotationTexts = new HashSet<String>(4);
        getAnnotationText(stepAnnotation).ifPresent(annotationTexts::add);

        //If the parent method is available, e.g. from JBehaveJavaStepDefinitionSearch, then use that, otherwise
        // compute it
        PsiMethod method = parentMethod != null
                           ? parentMethod
                           : compute(() -> PsiTreeUtil.getParentOfType(stepAnnotation, PsiMethod.class));
        if (method != null) {
            for (PsiAnnotation annotation : compute(() -> method.getModifierList().getAnnotations())) {
                if (isAnnotationOfClass(annotation, Alias.class)) {
                    getAnnotationText(annotation).ifPresent(annotationTexts::add);
                } else if (isAnnotationOfClass(annotation, Aliases.class)) {
                    annotationTexts.addAll(getAliasesAnnotationTexts(annotation));
                }
            }
        }

        return annotationTexts.stream()
            .map(PatternVariantBuilder::new)
            .flatMap(builder -> builder.allVariants().stream())
            .collect(Collectors.toSet());
    }

    /**
     * Returns the value of {@code annotation}'s {@code value} attribute, the step pattern.
     *
     * @param annotation a JBehave annotation: Given, When, Then, Alias or Aliases
     */
    private static Optional<String> getAnnotationText(@NotNull PsiAnnotation annotation) {
        return Optional.ofNullable(compute(() -> AnnotationUtil.getStringAttributeValue(annotation, "value")));
    }

    @NotNull
    private static Set<String> getAliasesAnnotationTexts(@NotNull PsiAnnotation aliasAnnotation) {
        return compute(() -> AnnotationUtil.arrayAttributeValues(aliasAnnotation.findAttributeValue("values")))
            .stream()
            .map(attr -> compute(() -> AnnotationUtil.getStringAttributeValue(attr)))
            .collect(Collectors.toSet());
    }

    /**
     * Collects all possible step annotation pattern variants for the annotations on {@code method}.
     *
     * @param method the step definition method
     * @return the list of step patterns
     */
    @NotNull
    public static List<String> getAnnotationTexts(@NotNull PsiMethod method) {
        var annotations = getJBehaveStepAnnotations(method);

        //Optimization to avoid creating unnecessary Streams
        if (annotations.isEmpty()) {
            return Collections.emptyList();
        }

        return annotations
            .stream()
            .map(annotation -> JBehaveUtil.getAnnotationTexts(annotation, method))
            .flatMap(Set::stream)
            .collect(Collectors.toList());
    }

    /**
     * Returns the value of {@code stepAnnotation}'s {@code priority} attribute, or -1 if the
     * priority is not an integer.
     */
    @NotNull
    public static Integer getAnnotationPriority(@NotNull PsiAnnotation stepAnnotation) {
        PsiAnnotationMemberValue attrValue = compute(() -> stepAnnotation.findAttributeValue("priority"));
        // TODO test change doesn't break other languages; this change works as a quick fix for Kotlin
        //Object constValue = JavaPsiFacade.getInstance(stepAnnotation.getProject()).getConstantEvaluationHelper()
        // .computeConstantExpression(attrValue);
        Object constValue = compute(() -> JavaPsiFacade.getInstance(compute(stepAnnotation::getProject))
            .getConstantEvaluationHelper()
            .computeConstantExpression(attrValue.getOriginalElement()));
        Integer priority = constValue instanceof Integer ? (Integer) constValue : null;

        return priority != null ? priority : -1;
    }

    /**
     * Performs word based reference search for {@code stepDefinitionElement}.
     *
     * @param stepDefinitionElement a step definition method
     * @param stepText              the step pattern value of the step annotation
     * @param consumer
     * @param searchScope           the search scope to find references in. Already restricted to JBehave Story files.
     *                              See {@link JBehaveJavaStepDefinitionSearch}.
     * @return true if the corresponding query execution in {@link JBehaveJavaStepDefinitionSearch} should continue,
     * false if it should stop
     */
    public static boolean findJBehaveReferencesToElement(
        @NotNull PsiElement stepDefinitionElement,
        @NotNull String stepText,
        @NotNull Processor<? super PsiReference> consumer,
        @NotNull final SearchScope searchScope
    ) {
        String word = getTheBiggestWordToSearchByIndex(stepText);

        return isEmptyOrSpaces(word)
            || PsiSearchHelper.getInstance(compute(stepDefinitionElement::getProject))
            .processElementsWithWord(new MyReferenceCheckingProcessor(stepDefinitionElement, consumer),
                searchScope,
                word,
                (short) 5,
                true);
    }

    /**
     * Returns a search scope that is based on the {@code originalScopeComputation} but that is restricted to JBehave
     * Story file types.
     */
    public static SearchScope restrictScopeToJBehaveFiles(final SearchScope originalScope) {
        return compute(() ->
            originalScope instanceof GlobalSearchScope globalSearchScope
            ? GlobalSearchScope.getScopeRestrictedByFileTypes(globalSearchScope, StoryFileType.STORY_FILE_TYPE)
            : originalScope);
    }

    /**
     * Returns the longest non-placeholder (e.g. {@code $price}) word from {@code stepText}.
     *
     * @param stepText the step pattern value from a step annotation
     */
    public static String getTheBiggestWordToSearchByIndex(@NotNull String stepText) {
        //This helps avoid the creation of the variables below in case of empty and blank step text
        if (stepText.isBlank()) {
            return "";
        }

        String result = "";

        int par = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < stepText.length(); ++i) {
            char c = stepText.charAt(i);

            if (c == '$') {
                ++par;
            }

            if (c == ' ' && par > 0) {
                --par;
            }

            if (par > 0) {
                if (par == 1) {
                    sb = new StringBuilder();
                }
            } else if (Character.isLetterOrDigit(c)) {
                sb.append(c);
                if (!sb.isEmpty() && sb.toString().length() > result.length()) {
                    result = sb.toString();
                }
            } else {
                sb = new StringBuilder();
            }
        }

        if (!sb.isEmpty() && sb.toString().length() > result.length()) {
            result = sb.toString();
        }

        return result;
    }

    private static final class MyReferenceCheckingProcessor implements TextOccurenceProcessor {

        @NotNull
        private final PsiElement myElementToFind;
        @NotNull
        private final Processor<? super PsiReference> myConsumer;

        private MyReferenceCheckingProcessor(
            @NotNull PsiElement elementToFind,
            @NotNull Processor<? super PsiReference> consumer
        ) {
            myElementToFind = elementToFind;
            myConsumer = consumer;
        }

        @Override
        public boolean execute(@NotNull PsiElement element, int offsetInElement) {
            boolean result = executeInternal(element);
            //This avoids calculating the parent element when result is false
            if (result) {
                PsiElement parent = element.getParent();
                return parent == null || executeInternal(parent);
            }
            return false;
        }

        private boolean executeInternal(@NotNull PsiElement referenceOwner) {
            for (PsiReference ref : referenceOwner.getReferences()) {
                if (ref != null && ref.isReferenceTo(myElementToFind) && !myConsumer.process(ref)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Checks whether the given element is an PSI Class and either it is {@link Embedder} or a class that can provide
     * an instance of {@link Embedder} either via annotations or by implementing the different versions of
     * {@link Embeddable} interface and provide embedder via, for example,
     * {@link InjectableEmbedder#injectedEmbedder()} method.
     *
     * @param psiElement the element to check
     * @return {@code true} if the element can be used as an {@link Embedder}, {@code false} otherwise
     * @see #isEmbedderClassOrEmbedderProvider(PsiClass)
     */
    public static boolean isEmbedderClassOrEmbedderProvider(PsiElement psiElement) {
        return psiElement instanceof PsiClass psiClass && isEmbedderClassOrEmbedderProvider(psiClass);
    }

    /**
     * Checks whether the given class either is {@link Embedder} or a class that can provide an instance of
     * {@link Embedder} either via annotations or by implementing the different versions of {@link Embeddable}
     * interface and provide embedder via, for example, {@link InjectableEmbedder#injectedEmbedder()} method.
     *
     * @param psiClass the class to check
     * @return {@code true} if the class can be used as an {@link Embedder}, {@code false} otherwise
     */
    public static boolean isEmbedderClassOrEmbedderProvider(PsiClass psiClass) {
        return isEmbedderClass(psiClass) || isAnnotatedEmbedderClass(psiClass) || isEmbeddableClass(psiClass);
    }

    /**
     * Checks whether the given class is a class that inherited from {@link Embedder}.
     *
     * @param psiClass the class to check
     * @return {@code true} if the class is an {@link Embedder}, {@code false} otherwise
     */
    public static boolean isEmbedderClass(PsiClass psiClass) {
        return compute(() -> InheritanceUtil.isInheritor(psiClass, Embedder.class.getName()));
    }

    /**
     * Checks whether the given class is annotated with {@link UsingEmbedder} annotation.
     *
     * @param psiClass the class to check
     * @return {@code true} if the class is annotated, {@code false} otherwise
     */
    public static boolean isAnnotatedEmbedderClass(@NotNull PsiClass psiClass) {
        return compute(() -> AnnotationUtil.isAnnotated(psiClass, UsingEmbedder.class.getName(),
            AnnotationUtil.CHECK_HIERARCHY));
    }

    /**
     * Checks whether the given class is a child of {@link Embeddable} class and is not abstract, interface,
     * enum or annotation type, meaning it can provide an instance of {@link Embedder} via, for example,
     * {@link InjectableEmbedder#injectedEmbedder()} method.
     *
     * @param psiClass the class to check
     * @return {@code true} if the class can provide an instance of {@link Embedder}, {@code false} otherwise
     */
    public static boolean isEmbeddableClass(@NotNull PsiClass psiClass) {
        return compute(() -> InheritanceUtil.isInheritor(psiClass, Embeddable.class.getName())
            && !psiClass.hasModifier(JvmModifier.ABSTRACT)
            && !psiClass.isInterface()
            && !psiClass.isEnum()
            && !psiClass.isAnnotationType());
    }

    /**
     * Searches for a class that either is an {@link Embedder} or a class that can provide
     * an instance of {@link Embedder} either via annotations or by implementing the different versions of
     * {@link Embeddable} interface and provide embedder via, for example,
     * {@link InjectableEmbedder#injectedEmbedder()} method.
     *
     * <p>Is it safe to invoke this method on EDT.</p>
     *
     * @param module the module where search will be executed
     * @return Embedder class or class that can provide it, or {@code null} if such class is not found
     * @see #isEmbedderClassOrEmbedderProvider(PsiClass)
     */
    @Nullable
    public static PsiClass findEmbedderClassOrEmbedderProvider(@NotNull Module module) {
        return ApplicationManager.getApplication().isDispatchThread()
               ? findEmbedderClassOrEmbedderProviderWithProgress(module)
               : doFindEmbedderClassOrEmbedderProvider(module);
    }

    @Nullable
    private static PsiClass findEmbedderClassOrEmbedderProviderWithProgress(Module module) {
        return ProgressManager.getInstance().run(new Task.WithResult<>(module.getProject(),
            JBehaveBundle.message("progress.title.finding.embedder.class"),
            true) {
            @Override
            protected PsiClass compute(@NotNull ProgressIndicator indicator) {
                return doFindEmbedderClassOrEmbedderProvider(module);
            }
        });
    }

    @Nullable
    private static PsiClass doFindEmbedderClassOrEmbedderProvider(Module module) {
        GlobalSearchScope scope = GlobalSearchScope.moduleWithDependenciesScope(module);
        return AllClassesSearch.search(scope, module.getProject())
            .filtering(JBehaveUtil::isEmbedderClassOrEmbedderProvider)
            .findFirst();
    }

    /**
     * Checks if the given PSI element is a {@link StoryFinder} class, or it's child.
     *
     * @param psiElement PSI element to check
     * @return {@code true} if PSI element is PSI class, and it is a {@link StoryFinder} or its child, {@code false}
     * otherwise
     * @see StoryFinder
     */
    public static boolean isStoryFinderClass(PsiElement psiElement) {
        return psiElement instanceof PsiClass psiClass &&
            InheritanceUtil.isInheritor(psiClass, StoryFinder.class.getName());
    }

    /**
     * Checks if the given PSI element is located in a Story file, and is not a directory or a file itself.
     *
     * @param element PSI element to check
     * @return {@code true} if element is a part of Story file, {@code false} otherwise
     * @see StoryElementType#STORY_FILE
     */
    public static boolean isStory(@NotNull PsiElement element) {
        if (element instanceof PsiFile || element instanceof PsiDirectory) {
            return false;
        }

        PsiFile file = element.getContainingFile();
        if (file == null) {
            return false;
        }

        return Objects.equals(file.getFileElementType(), StoryElementType.STORY_FILE);
    }

    /**
     * Checks if the given PSI element is a Story file itself.
     *
     * @param element PSI element to check
     * @return {@code true} if element is Story File, {@code false} otherwise
     * @see StoryFile
     */
    public static boolean isStoryFile(@NotNull PsiElement element) {
        return element instanceof PsiFile file && file instanceof StoryFile;
    }

    /**
     * Checks if the given PSI element is a directory that contains Story files.
     *
     * <p>It is safe to invoke this method on EDT.</p>
     *
     * @param element PSI element to check
     * @return {@code true} if element is a directory with Story files, {@code false} otherwise
     * @see StoryFile
     */
    public static boolean isDirectoryWithStories(@NotNull PsiElement element) {
        if (!(element instanceof PsiDirectory directory)) {
            return false;
        }

        return ApplicationManager.getApplication().isDispatchThread()
               ? isDirectoryWithStoriesWithProgress(directory)
               : isDirectoryWithStories(directory);
    }

    private static boolean isDirectoryWithStoriesWithProgress(@NotNull PsiDirectory directory) {
        return ProgressManager.getInstance().run(new Task.WithResult<>(directory.getProject(),
            JBehaveBundle.message("progress.title.searching.story.files"),
            true) {
            @Override
            protected Boolean compute(@NotNull ProgressIndicator indicator) {
                return isDirectoryWithStories(directory);
            }
        });
    }

    private static boolean isDirectoryWithStories(@NotNull PsiDirectory directory) {
        VirtualFile virtualDirectory = directory.getVirtualFile();
        Project project = directory.getProject();
        StoryFileType fileType = StoryFileType.STORY_FILE_TYPE;
        GlobalSearchScope scope = DirectoryScope.directoryScope(project, virtualDirectory);
        return compute(() -> FileTypeIndex.containsFileOfType(fileType, scope));
    }

    private JBehaveUtil() {
    }
}
