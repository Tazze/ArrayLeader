package com.soprasteria.arrayleader;

import java.util.Collections;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    public Integer[] solution(int K, int M, int[] A){
        int N = A.length;

        inputCheck(K, M, N);

        SortedSet<Integer> answer = Collections.synchronizedSortedSet(new TreeSet<>());
        HashMap<Integer, Integer> counter = new HashMap<>(M);

        IntStream.of(A)
            .forEach(num -> {
                if(num<1 || num > M)
                    throw new IllegalArgumentException("Array Element is out of bounds [1..."+ M +"]: " + num);
                counter.put(num, counter.getOrDefault(num, 0)+1);
            });

        IntStream.rangeClosed(0, N-K)
            .parallel()
            .forEach(index -> {
                HashMap<Integer, Integer> segmentCounter = new HashMap<>(K);
                IntStream.range(0, K)
                    .forEach(offset -> 
                        segmentCounter.put(A[index+offset]+1, segmentCounter.getOrDefault(A[index+offset]+1, counter.getOrDefault(A[index+offset]+1, 0)) +1));
                answer.addAll(segmentCounter.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > N/2.0)
                    .map(entry -> entry.getKey())
                    .collect(Collectors.toList()));
            });
        return answer.toArray(new Integer[answer.size()]);
    }

    private void inputCheck(int K, int M, int N){
        if(!(N > 0 && N <= 100000))
            throw new IllegalArgumentException("Parameter N is out of bounds [1...100,000]: " + N);
        if(!(N > 0 && M <= 100000))
            throw new IllegalArgumentException("Parameter M is out of bounds [1...100,000]: " + M);
        if(!(K > 0 && K <= N))
            throw new IllegalArgumentException("Parameter K is out of bounds [1..." + N + "]: " + K);
    }

}
