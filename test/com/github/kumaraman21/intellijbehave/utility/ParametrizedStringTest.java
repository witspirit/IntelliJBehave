package com.github.kumaraman21.intellijbehave.utility;

import com.intellij.openapi.util.Pair;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * ParametrizedString Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>03/19/2015</pre>
 */
public class ParametrizedStringTest extends TestCase {
    public ParametrizedStringTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: hashCode()
     */
    public void testHashCode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: toString()
     */
    public void testToString() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: equals(Object obj)
     */
    public void testEquals() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isSameAs(ParametrizedString other)
     */
    public void testIsSameAsOther() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getToken(int index)
     */
    public void testGetToken() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getTokenCount()
     */
    public void testGetTokenCount() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: calculateWeightChain(String input)
     */
    public void testCalculateWeightChain() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: tokenize(String stepInput)
     */
    public void testTokenizeStepInput() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isSameAs(String input)
     */
    public void testIsSameAsInput() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: complete(String input)
     */
    public void testComplete() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getTokensOf(String input)
     */
    public void testGetTokensOf() throws Exception {
        List<Pair<String, String>> expected = new ArrayList<Pair<String, String>>();
        expected.add(new Pair<String, String>("1 2 3", "p"));
        expected.add(new Pair<String, String>("a b", null));
        expected.add(new Pair<String, String>("4", "k"));
        expected.add(new Pair<String, String>("d", null));
        expected.add(new Pair<String, String>("5 6", "q"));
        String oldText = "$p a b $k d $q";
        String newText = "1 2 3 a b 4 d 5 6";
        ParametrizedString pOldText = new ParametrizedString(oldText);
        List<Pair<ParametrizedString.ContentToken, String>> actual = pOldText.getTokensOf(newText);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); ++i) {
            assertEquals(expected.get(i), actual.get(i));
        }

        expected.clear();
        expected.add(new Pair<String, String>("1 2 3", null));
        expected.add(new Pair<String, String>("a b", "a"));
        expected.add(new Pair<String, String>("4", null));
        expected.add(new Pair<String, String>("d", "d"));
        expected.add(new Pair<String, String>("5 6", null));
        oldText = "1 2 3 $a 4 $d 5 6";
        newText = "1 2 3 a b 4 d 5 6";
        pOldText = new ParametrizedString(oldText);
        actual = pOldText.getTokensOf(newText);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); ++i) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Method: value()
     */
    public void testValue() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: regionMatches(int toffset, String other, int ooffset, int len)
     */
    public void testRegionMatches() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getOffset()
     */
    public void testGetOffset() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getLength()
     */
    public void testGetLength() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isIdentifier()
     */
    public void testIsIdentifier() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getValue()
     */
    public void testGetValue() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: zero()
     */
    public void testZero() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: last()
     */
    public void testLast() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isZero()
     */
    public void testIsZero() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getNext()
     */
    public void testGetNext() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getTokenIndex()
     */
    public void testGetTokenIndex() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: hasMoreWeightThan(WeightChain pair)
     */
    public void testHasMoreWeightThan() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: collectWeights()
     */
    public void testCollectWeights() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: tokenize()
     */
    public void testTokenize() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: compileParameterPattern(String parameterPrefix)
     */
    public void testCompileParameterPattern() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ParametrizedString.getClass().getMethod("compileParameterPattern", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: parse(final Pattern parameterPattern)
     */
    public void testParse() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ParametrizedString.getClass().getMethod("parse", final.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: add(Token token)
     */
    public void testAdd() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ParametrizedString.getClass().getMethod("add", Token.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: acceptsBeginning(int inputIndex, String input, int tokenIndexStart)
     */
    public void testAcceptsBeginning() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ParametrizedString.getClass().getMethod("acceptsBeginning", int.class, String.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: normalize(final String input, final int offset, final int len)
     */
    public void testNormalize() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ParametrizedString.getClass().getMethod("normalize", final.class, final.class, final.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }


    public static Test suite() {
        return new TestSuite(ParametrizedStringTest.class);
    }
} 
