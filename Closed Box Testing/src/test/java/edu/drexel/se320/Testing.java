package edu.drexel.se320;

// Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.lessThan;

// Core JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

// Jqwik
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import java.util.NoSuchElementException;

public class Testing {

    //tests based on Equivalence Partitioning

    @Test
    public void testNullArray() {
        Integer[] array = null;
        String[] array1 = null;

        Exception exception;

        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array, 0); }
        );

        assertEquals("Entered array is null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array1, "6"); }
        );

        assertEquals("Entered array is null", exception.getMessage());
    }

    @Test
    public void testEmptyArray() {
        Integer[] array = {};
        String[] array1 = {};

        Exception exception;
        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array, 0); }
        );

        assertEquals("Entered array is empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array1, "a"); }
        );

        assertEquals("Entered array is empty", exception.getMessage());
    }

    @Test
    public void testNullElement() {
        Integer[] array = {7};
        String[] array1 = {"a"};

        Exception exception;
        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array, null); }
        );

        assertEquals("Entered element is null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array1, null); }
        );
        assertEquals("Entered element is null", exception.getMessage());
    }

    @Test
    public void testArrayWithOneElement() {
        Integer[] array = {7};
        assertEquals(BinarySearch.find(array, 7), 0);

        String[] array1= {"a"};
        assertEquals(BinarySearch.find(array1, "a"), 0);
    }

    @Test
    public void testElementNotInArray() {
        Integer[] array = {7};
        String[] array1 = {"a"};

        Exception exception;
        exception = assertThrows(NoSuchElementException.class,
                () -> { BinarySearch.find(array, 0); }
        );

        assertEquals("Entered element is not present in the array", exception.getMessage());

        exception = assertThrows(NoSuchElementException.class,
                () -> { BinarySearch.find(array1, ""); }
        );
        assertEquals("Entered element is not present in the array", exception.getMessage());
    }

    @Test
    public void testElementPresentAtFirstIndex() {
        String[] array = {"a", "b", "c", "d"};
        assertEquals(BinarySearch.find(array, "a"), 0);

        Integer[] array1 = {7, 11, 47, 83, 87};
        assertEquals(BinarySearch.find(array1, 7), 0);
    }

    @Test
    public void testElementPresentAtMiddleIndex() {
        String[] array = {"a", "b", "c", "d", "e"};
        assertEquals(BinarySearch.find(array, "c"), (array.length - 1) / 2);

        Integer[] array1 = {7, 11, 47, 83, 87, 91};
        assertEquals(BinarySearch.find(array1, 47), (array.length - 1) / 2);
    }


    @Test
    public void testElementPresentAtLastIndex() {
        String[] array = {"a", "b", "c", "d"};
        assertEquals(BinarySearch.find(array, "d"), array.length - 1);

        Integer[] array1 = {7, 11, 47, 83, 87};
        assertEquals(BinarySearch.find(array1, 87), array1.length - 1);
    }


    //Tests based on Boundary Value Analysis

    @Test
    public void testArrayWithManyElements_NullElement() {
        Integer[] array = {7, 8, 9, 10};
        String[] array1 = {"a", "b", "c", "d"};

        Exception exception;
        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array, null); }
        );

        assertEquals("Entered element is null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> { BinarySearch.find(array1, null); }
        );
        assertEquals("Entered element is null", exception.getMessage());
    }

    @Test
    public void testArrayWithManyElements() {
        String[] array = {"a", "b", "c", "d","e", "f", "g"};
        assertEquals(BinarySearch.find(array, "f"), 5);

        Integer[] array1 = {7, 11, 47, 83, 87, 101, 120, 130, 140};
        assertEquals(BinarySearch.find(array1, 101), 5);
    }

    @Test
    public void testArrayWithManyElements_ElementNotInArray() {
        Integer[] array = {7, 8, 9, 10};
        String[] array1 = {"a", "b", "c", "d"};

        Exception exception;
        exception = assertThrows(NoSuchElementException.class,
                () -> { BinarySearch.find(array, 0); }
        );

        assertEquals("Entered element is not present in the array", exception.getMessage());

        exception = assertThrows(NoSuchElementException.class,
                () -> { BinarySearch.find(array1, ""); }
        );
        assertEquals("Entered element is not present in the array", exception.getMessage());
    }

    @Test
    public void testElementPresentAtSecondIndex() {
        String[] array = {"a", "b", "c", "d"};
        assertEquals(BinarySearch.find(array, "b"), 1);

        Integer[] array1 = {7, 11, 47, 83, 87};
        assertEquals(BinarySearch.find(array1, 11), 1);
    }

    @Test
    public void testElementPresentAtMiddlePlusOneIndex() {
        String[] array = {"a", "b", "c", "d", "e"};
        assertEquals(BinarySearch.find(array, "d"), (array.length - 1) / 2 + 1);

        Integer[] array1 = {7, 11, 47, 83, 87, 91};
        assertEquals(BinarySearch.find(array1, 83), (array.length - 1) / 2 + 1);
    }

    @Test
    public void testElementPresentAtSecondLastIndex() {
        String[] array = {"a", "b", "c", "d"};
        assertEquals(BinarySearch.find(array, "c"), array.length - 2);

        Integer[] array1 = {7, 11, 47, 83, 87};
        assertEquals(BinarySearch.find(array1, 83), array1.length - 2);
    }

}

