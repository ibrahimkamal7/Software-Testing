package edu.drexel.se320;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class RedBlackTests {

    @Test
    public void testBSTSize() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{1, 2, 3, 4, 5};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        assertEquals(tree.size(), 5);
    }

    @Test
    public void testPutNullKey() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.put(null, "Hello"); }
        );
        assertEquals("first argument to put() is null", exc.getMessage());
    }

    @Test
    public void testPut() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        assertEquals(tree.get(4), "4");

        tree.put(110, null);
        assertFalse(tree.contains(110));
    }

    @Test
    public void testGettingNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        tree.put(1, "Hello");
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.get(null); }
        );

        assertEquals("argument to get() is null", exc.getMessage());
    }

    @Test
    public void testGet() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        tree.put(1, "1");
        tree.put(3, "3");
        tree.put(8, "8");
        tree.put(10, "10");

        assertEquals(tree.get(1), "1");
        assertEquals(tree.get(3), "3");
        assertEquals(tree.get(10), "10");
        assertEquals(tree.get(8), "8");
        assertNull(tree.get(11));
    }

    @Test
    public void testContains() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        tree.put(1, "1");
        tree.put(3, "3");
        tree.put(8, "8");
        tree.put(10, "10");

        assertTrue(tree.contains(1));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(8));
        assertFalse(tree.contains(11));
    }

    @Test
    public void testDeleteMin() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        tree.put(1, "1");
        tree.put(3, "3");
        tree.put(8, "8");
        tree.put(8, "8");
        tree.put(10, "10");
        tree.deleteMin();

        assertFalse(tree.contains(1));

        tree = new RedBlackBST<>();

        tree.put(10, "10");
        tree.deleteMin();

        assertFalse(tree.contains(10));

        tree = new RedBlackBST<>();

        tree.put(0, "1");
        tree.put(1, "3");
        tree.deleteMin();

        assertFalse(tree.contains(0));
    }

    @Test
    public void testDeleteMinWithEmptyBST() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.deleteMin(); }
        );
        assertEquals("BST underflow", exc.getMessage());
    }

    @Test
    public void testDeleteMax() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4, 97};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        tree.deleteMax();
        assertFalse(tree.contains(99));

        tree = new RedBlackBST<>();

        tree.put(99, String.valueOf(99));
        tree.deleteMax();

        assertFalse(tree.contains(99));

        tree = new RedBlackBST<>();

        tree.put(0, "1");
        tree.put(1, "3");
        tree.deleteMax();

        assertFalse(tree.contains(1));
    }

    @Test
    public void testDeleteMaxWithEmptyBST() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.deleteMax(); }
        );
        assertEquals("BST underflow", exc.getMessage());
    }

    @Test
    public void testDelete() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        for (int j : array) {
            tree.delete(j);
        }

        for (int j : array) {
            assertFalse(tree.contains(j));
        }

        tree.delete(108);
        assertFalse(tree.contains(108));

        array = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        tree.delete(0);
        assertFalse(tree.contains(0));
    }

    @Test
    public void testDeleteWithNullKey() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.delete(null); }
        );
        assertEquals("argument to delete() is null", exc.getMessage());
    }

    @Test
    public void testMin() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }
        assertEquals(tree.min(), 0);
    }

    @Test
    public void testMinWithEmptyBST() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.min(); }
        );
        assertEquals("called min() with empty symbol table", exc.getMessage());
    }

    @Test
    public void testMax() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }
        assertEquals(tree.max(), 99);
    }

    @Test
    public void testMaxWithEmptyBST() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.max(); }
        );
        assertEquals("called max() with empty symbol table", exc.getMessage());
    }

    @Test
    public void testFloor() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }
        for (int j : array) {
            assertEquals(tree.floor(j), j);
        }

        assertEquals(tree.floor(-1), null);
        assertEquals(tree.floor(100), 99);
    }

    @Test
    public void testFloorWithEmptyBST() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.floor(1); }
        );
        assertEquals("called floor() with empty symbol table", exc.getMessage());
    }

    @Test
    public void testFloorWithNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.floor(null); }
        );
        assertEquals("argument to floor() is null", exc.getMessage());
    }

    @Test
    public void testCeiling() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        for (int j : array) {
            assertEquals(tree.ceiling(j), j);
        }

        assertEquals(tree.ceiling(85), 89);
        assertEquals(tree.ceiling(100), null);
    }

    @Test
    public void testCeilingWithEmptyBST() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.ceiling(1); }
        );
        assertEquals("called ceiling() with empty symbol table", exc.getMessage());
    }

    @Test
    public void testCeilingWithNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.ceiling(null); }
        );
        assertEquals("argument to ceiling() is null", exc.getMessage());
    }

    @Test
    public void testRank() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{1, 2, 3, 4, 5};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        assertEquals(tree.rank(5), 4);
        assertEquals(tree.rank(0), 0);
    }

    @Test
    public void testRankWithNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.rank(null); }
        );
        assertEquals("argument to rank() is null", exc.getMessage());
    }

    @Test
    public void testKeys() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Integer[] array = new Integer[]{32, 11, 45, 78, 98, 99, 0, 4, 78, 7, 5, 3, 89, 4};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        Iterable<Integer> result = tree.keys(0,4);
        Integer[] a= new Integer[]{0, 3, 4};
        Iterable<Integer> array1 = Arrays.asList(a);

        assertEquals(array1.toString(), result.toString());

        result = tree.keys(100, 101);
        a = new Integer[]{};
        array1 = Arrays.asList(a);

        assertEquals(array1.toString(), result.toString());
    }

    @Test
    public void testKeysWithNullLow() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.keys(null, 99); }
        );
        assertEquals("first argument to keys() is null", exc.getMessage());
    }

    @Test
    public void testKeysWithNullHigh() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.keys(99, null); }
        );
        assertEquals("second argument to keys() is null", exc.getMessage());
    }

    @Test
    public void testSize() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        int[] array = new int[]{1, 2, 3, 4, 5, 6};

        for (int j : array) {
            tree.put(j, String.valueOf(j));
        }

        assertEquals(tree.size(1, 6), 6);
        assertEquals(tree.size(1, 7), 6);
        assertEquals(tree.size(10, 25), 0);
        assertEquals(tree.size(30, 25), 0);
    }

    @Test
    public void testSizeWithNullLow() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.size(null, 99); }
        );
        assertEquals("first argument to size() is null", exc.getMessage());
    }

    @Test
    public void testSizeWithNullHigh() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();

        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.size(99, null); }
        );
        assertEquals("second argument to size() is null", exc.getMessage());
    }
}
 
