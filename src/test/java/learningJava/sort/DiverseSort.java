package learningJava.sort;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DiverseSort {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private StringTokenizer st;


    private void run() throws Exception {
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        selectionSort(arr);
//        bubbleSort(arr);
//        insertionSort(arr);
//        mergeSort(0, arr.length - 1, arr);

        for (int i : arr) {
            System.out.println(i);
        }

    }

    private void selectionSort(int[] arr) {
        int start = 0; // 아직 처리되지 않은 영역의 맨 앞

        // nextIdx 를 언제까지 반복하는가 -> cur 이 arr.length -1 에 도달할 때 까지
        while (start < arr.length - 1) {
            int next = select(arr, start); // 처리되지 않은 영역에서 기준을 만족하는 index
            swap(arr, start++, next); // 처리되지 않은 영역의 첫 번째 원소와, 선택된 원소를 swap
        }
    }

    // arr 의 start index 부터 시작해 기준을 만족하는 index 를 뽑아 낸다
    // 내림차순이라면 start ~ arr.length -1 영역에서의 max 값의 index 를 뽑아 내는 것.
    private int select(int[] arr, int start) {
        // max 값을 추출하고 싶은 경우라면
        int nextIdx = start;
        int max = Integer.MIN_VALUE;
        for (int idx = start; idx < arr.length; idx++) {
            if (max < arr[idx]) {
                max = arr[idx];
                nextIdx = idx;
            }
        }

        return nextIdx;
    }

    private void swap(int[] arr, int idx1, int idx2) {
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }

    public static void main(String[] args) throws Exception {
        DiverseSort sort = new DiverseSort();

        sort.run();
    }
}

