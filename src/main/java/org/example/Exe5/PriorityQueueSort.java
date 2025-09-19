package org.example.Exe5;

import java.util.PriorityQueue;

public final class PriorityQueueSort {

    private PriorityQueueSort() {}

    public static int[] sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : arr) {
            pq.offer(num);
        }

        int i = 0;
        while (!pq.isEmpty()) {
            arr[i++] = pq.poll();
        }

        return arr;
    }
}

