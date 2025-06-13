public class MergeSort {
    //Method to perform merge sort
    public static void mergeSort(String[] array) {
        int n = array.length;

        //If array has 1 or fewer elements then stop recursion
        if (n <= 1) {
            return;
        }

        int middle = n / 2;

        //Create left and right subarrays
        String[] left = new String[middle];
        String[] right = new String[n - middle];

        //Fill left and right subarrays
        for (int i = 0; i < n; i++) {
            if (i < middle) {
                left[i] = array[i];
            } else {
                right[i - middle] = array[i];
            }
        }

        //Recursively sort the left subarray
        mergeSort(left);
        //Recursively sort the right subarray
        mergeSort(right);

        //Merge the sorted subarrays
        merge(left, right, array);
    }

    //Method to merge subarrays
    public static void merge(String[] left, String[] right, String[] result) {
        int leftLength = left.length;
        int rightLength = right.length;

        int i = 0, l = 0, r = 0;

        //Compare elements from both subarrays and merge in sorted order
        while (l < leftLength && r < rightLength) {
            if (left[l].compareTo(right[r]) <= 0) {
                //Take element from left subarray
                result[i] = left[l];
                l++;
            } else {
                //Take element from right subarray
                result[i] = right[r];
                r++;
            }
            i++;
        }

        //Copy remaining elements from left subarray
        while (l < leftLength) {
            result[i] = left[l];
            l++;
            i++;
        }

        //Copy remaining elements from right subarray
        while (r < rightLength) {
            result[i] = right[r];
            r++;
            i++;
        }
    }
}