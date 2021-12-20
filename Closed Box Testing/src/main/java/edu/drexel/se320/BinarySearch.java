package edu.drexel.se320;

import java.util.NoSuchElementException;

public class BinarySearch {

    public static <T extends Comparable<T>> int find(T[] array, T elem) {

        //handling in valid input
        if(array == null)
            throw new IllegalArgumentException("Entered array is null");
        else if(array.length == 0)
            throw new IllegalArgumentException("Entered array is empty");
        else if(elem == null)
            throw new IllegalArgumentException("Entered element is null");

        //performing binary search
        else {
            int min = 0;
            int max = array.length - 1;
            int mid;
            while (min <= max){
                mid = min + (max - min) / 2;
                if(array[mid].compareTo(elem) == 0)
                    return mid;
                else if(array[mid].compareTo(elem) > 0)
                    max = mid - 1;
                else
                    min = mid + 1;
            }
        }

        //throwing an exception if the element is not found in the array
        throw new NoSuchElementException("Entered element is not present in the array");

    }

    public static void main(String[] args) {

	// Java generics do not treat primitives the same as object types.
	// To pass a primitive type (int, double, etc.) to the find method, you need to actually use the corresponding "boxed" version (Integer, Double, etc.) which is a class-based version of each primitive.
	// For types which are already/always objects, like String, everything will just work.

        Integer[] arr = { 0, 1, 2 };
        System.out.println(find(arr, 1));

        String[] arr2 = { "a", "b", "c", "foo" };
        System.out.println(find(arr2, "c"));

    }
}
