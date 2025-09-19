package org.example.Exe5;

import java.util.PriorityQueue;

public final class PriorityQueueSort {

    private PriorityQueueSort() {}

    public static int[] sort(int[] arr) {
        if (arr == null || arr.length == 0) {    // Decisão 1
            return arr;                          // Ramificação A
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : arr) {                    // Loop 1
            pq.offer(num);
        }

        int i = 0;
        while (!pq.isEmpty()) {                  // Loop 2
            arr[i++] = pq.poll();               // Ramificação B
        }

        return arr;
    }
}

