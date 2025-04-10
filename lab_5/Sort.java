package lab_5;

public class Sort {
  public static void main(String[] args) {
    int[] arr1 = {7, 4, 5, 1, 3};
    int[] arr2 = {9, 2, 2, 5, 4, 3, 1, 6, 7, 3, 8, 0, 3};

    printArr(arr1); bubbleSort(arr1); printArr(arr1);
    printArr(arr2); bubbleSort(arr2); printArr(arr2);
  }
  public static void bubbleSort(int arr[]) {
    bubbleSort(arr, arr.length);
  }
  public static void bubbleSort(int arr[], int n) {
    if (n == 1) return;
    for (int i=0; i<n-1; i++) {
      if (arr[i] > arr[i+1]) {
        int temp = arr[i];
        arr[i] = arr[i+1];
        arr[i+1] = temp;
      }
    }
    bubbleSort(arr, n-1);
    return;
  }
  public static void printArr(int arr[]) {
    for (int i=0; i<arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
    return;
  }
}
