package com.github.kumaraman21.intellijbehave.utility;

import com.intellij.openapi.util.text.StringUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

/**
 * TokenMap Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>05/26/2015</pre>
 */
public class TokenMapTest extends TestCase {
    public TokenMapTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: isEmpty()
     */
    public void testIsEmpty() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * Method: splitPath(final String path)
     */
    public void testSplitPath() throws Exception {
        //TODO: Test goes here...
/* 
try { 
   Method method = TokenMap.getClass().getMethod("splitPath", final.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: put(final Iterator<String> path, final V value)
     */
    public void testPut() throws Exception {
        String[] putTests =
                new String[]{"a b $param c $param d e f", "a b $param c d e $param g $param", "a b c d $param e h",
                        "a $param bbb i", "a b j", "a b c d k", "a b <param> c d $param l",
                        "aa bb $param cccc ddddd $param zzzzz", "1 $param 3 5", "$param 2 3 4"};
        //        String[] putTests =
        //                new String[]{"a b c d e f", "a b c d e g ", "a b c d e h", "a b i", "a b j", "a b c d k", "a b c d l"};
        String[] getTests = new String[]{"a b param <<blabla|c>> param d e f",
                "a b param c <blabla|d> e param param param g param param param", "a b c d param e h", "a param bbb i",
                "a b j", "a b c d k", "a b param c d param l",
                "aa bb p<ara>m c<blabal|c>cc d<1|d>d<1|d>d param<param zz<1|z><1|z>z", "1 2 3 5", "1 2 3 4"};
        TokenMap<String> map = new TokenMap<String>();
        for (String test : putTests) {
            map.put(test, test);
        }
        for (int i = 0; i < getTests.length; i++) {
            String getTest = getTests[i];
            String putTets = putTests[i];
            List<String> concerned = map.get(getTest, true);
            assertFalse(getTest, concerned.isEmpty());
            for (String result : concerned) {
                assertEquals(putTets, result);
            }

        }
        testCompletion(map, "a",
                       "$param 2 3 4; a b c d $param e h; a b c d k; a b $param c d e $param g $param; a b <param> c d $param l; a b $param c $param d e f; a b j; a $param bbb i; aa bb $param cccc ddddd $param zzzzz");
        //
        testCompletion(map, "aa", "$param 2 3 4; aa bb $param cccc ddddd $param zzzzz");
        testCompletion(map, "aa b", "aa bb $param cccc ddddd $param zzzzz");
        testCompletion(map, "a param param", "a $param bbb i");
        testCompletion(map, "a param param b", "a $param bbb i");
        testCompletion(map, "a b param c d e param g par", "a b $param c d e $param g $param");


/*
try { 
   Method method = TokenMap.getClass().getMethod("put", final.class, final.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    private void testCompletion(TokenMap<String> map, String input, String expected) {
        List<String> concerned = map.get(input, false);
        assertFalse(input, concerned.isEmpty());
        String result = StringUtil.join(concerned, "; ");
        if (expected != null) {
            assertEquals(expected, result);
        }
        System.out.println(result);
    }

    /**
     * Method: unwrapInject(String maybeInject)
     */
    public void testUnwrapInject() throws Exception {
        //TODO: Test goes here...
/* 
try { 
   Method method = TokenMap.getClass().getMethod("unwrapInject", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: get(List<String> split, int count, boolean strict)
     */
    public void testGetConcerned() throws Exception {
        //TODO: Test goes here...
/* 
try { 
   Method method = TokenMap.getClass().getMethod("get", List<String>.class, int.class, boolean.class);
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getAll()
     */
    public void testGetAll() throws Exception {
        //TODO: Test goes here...
/* 
try { 
   Method method = TokenMap.getClass().getMethod("getAll"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }


    public static Test suite() {
        return new TestSuite(TokenMapTest.class);
    }
} 
