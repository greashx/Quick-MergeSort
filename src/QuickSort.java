public class QuickSort {


    //Method to perform quick sort
    public static void quickSort(String[] array, int start, int end) {

        //If start is greater or equal to end then stop recursion
        if (start >= end) {
            return;
        }

        //Get the pivot index after partitioning
        int pivotIndex = partition(array, start, end);

        //Recursively sort the left side of the pivot
        quickSort(array, start, pivotIndex - 1);

        //Recursively sort the right side of the pivot
        quickSort(array, pivotIndex + 1, end);
    }

    //Method to partition the array
    public static int partition(String[] array, int start, int end) {

        //Choose the last element as pivot
        String pivotValue = array[end];

        int smallerElementIndex = start - 1;

        //Loop through the array and swap elements
        for (int currentIndex = start; currentIndex <= end - 1; currentIndex++) {

            //If current element is less than pivot
            if (array[currentIndex].compareTo(pivotValue) < 0) {

                //Move smallerElementIndex forward
                smallerElementIndex = smallerElementIndex + 1;

                //Swap array[smallerElementIndex] and array[currentIndex]
                String temp = array[smallerElementIndex];
                array[smallerElementIndex] = array[currentIndex];
                array[currentIndex] = temp;
            }
        }

        //Put pivot in the correct position
        smallerElementIndex = smallerElementIndex + 1;
        String temp = array[smallerElementIndex];
        array[smallerElementIndex] = array[end];
        array[end] = temp;

        //Return the index of the pivot
        return smallerElementIndex;
    }
}
