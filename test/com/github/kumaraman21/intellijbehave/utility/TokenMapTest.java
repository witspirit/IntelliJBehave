package com.github.kumaraman21.intellijbehave.utility;

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
                new String[]{"a b c d e f", "a b c d e g", "a b c d e h", "a b i", "a b j", "a b c d k", "a b c d l"};
        TokenMap<String> map = new TokenMap<String>();
        for (String test : putTests) {
            map.put(test, test);
        }
        String[] getTests =
                new String[]{"a b param c param d e f", "a b param c d e param g param", "a b c d param e h",
                        "a param b i", "a b j", "a b c d k", "a b param c d param l"};
        for (int i = 0; i < getTests.length; i++) {
            String getTest = getTests[i];
            String putTets = putTests[i];
            List<String> concerned = map.getConcerned(getTest, true);
            for (String result : concerned) {
                assertEquals(putTets, result);
            }

        }


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
     * Method: getConcerned(List<String> split, int count, boolean strict)
     */
    public void testGetConcerned() throws Exception {
        //TODO: Test goes here...
/* 
try { 
   Method method = TokenMap.getClass().getMethod("getConcerned", List<String>.class, int.class, boolean.class); 
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
