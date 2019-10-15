package com.soprasteria.arrayleader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    public Integer[] solution(int K, int M, int[] A){
        int N = A.length;

        inputCheck(K, M, N, A);

        Map<Integer, Integer> counter = IntStream.of(A)
            .boxed()
            .collect(Collectors.toMap(
                Function.identity(), 
                value -> 1,
                (prev, next) -> prev + 1
            ));

        TreeSet<Integer> answer = new TreeSet<>();
        answer.addAll(IntStream.rangeClosed(0, N-K)
            .parallel()
            .mapToObj(index -> 
                {
                HashMap<Integer, Integer> segmentCounter = new HashMap<>();
                for(int offset = 0; offset < K; offset++){
                    segmentCounter.put(
                        A[index + offset], 
                        segmentCounter.getOrDefault(
                            A[index + offset], 
                            counter.get(A[index + offset])) -1);
                    segmentCounter.put(
                        A[index + offset] + 1, 
                        segmentCounter.getOrDefault(
                            A[index + offset] + 1, 
                            counter.getOrDefault(A[index + offset] + 1, 1)) +1);
                }
                return segmentCounter.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > N/2.0)
                    .map(entry -> entry.getKey())
                    .findAny();
                }
            )
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet())
        );
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
