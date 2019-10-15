package com.soprasteria.arrayleader;

import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    public Integer[] solution(int K, int M, int[] A){
        int N = A.length;

        inputCheck(K, M, N, A);

        SortedSet<Integer> answer = Collections.synchronizedSortedSet(new TreeSet<>());
        Map<Integer, Integer> counter = IntStream.of(A)
            .boxed()
            .collect(Collectors.toMap(
                Function.identity(), 
                value -> 1,
                (prev, next) -> prev + 1
            ));

        IntStream.rangeClosed(0, N-K)
            .parallel()
            .forEach(index -> {
                Map<Integer, Integer> segmentCounter = IntStream.range(0, K)
                    .boxed()
                    .collect(Collectors.toMap(
                        offset -> A[index + offset] + 1, 
                        offset -> counter.getOrDefault(A[index + offset] + 1, 0) + 1,
                        (prev, next) -> prev + 1
                ));
                answer.addAll(segmentCounter.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > N/2.0)
                    .map(entry -> entry.getKey())
                    .collect(Collectors.toList()));
            });
        return answer.toArray(new Integer[answer.size()]);
    }

    private void inputCheck(int K, int M, int N, int[] A){
        if(!(N > 0 && N <= 100000))
            throw new IllegalArgumentException("Parameter N is out of bounds [1...100,000]: " + N);
        if(!(N > 0 && M <= 100000))
            throw new IllegalArgumentException("Parameter M is out of bounds [1...100,000]: " + M);
        if(!(K > 0 && K <= N))
            throw new IllegalArgumentException("Parameter K is out of bounds [1..." + N + "]: " + K);
        for(int i = 0; i<N; i++){
            int num = A[i];
            if(num<1 || num > M)
                throw new IllegalArgumentException("Array Element is out of bounds [1..."+ M +"]: " + num);
        }
    }

}
