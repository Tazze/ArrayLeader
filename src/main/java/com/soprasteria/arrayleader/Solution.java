package com.soprasteria.arrayleader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    private final int K;
    private final int M;
    private final int[] A;
    private final int N;
    private final Map<Integer, Integer> globalCounter;

    public Solution(int K, int M, int[] A){
        this.K = K;
        this.M = M;
        this.A = A;
        this.N = A.length;

        inputCheck();

        this.globalCounter = IntStream.of(A)
            .boxed()
            .collect(Collectors.toMap(
                Function.identity(), 
                value -> 1,
                (prev, next) -> prev + 1
            ));
    }

    public int[] findPotentialLeaders(){
        return toIntArray(
            IntStream.rangeClosed(0, N-K)
                .parallel()
                .mapToObj(this::getSegmentLeader)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(() -> 
                    Collections.synchronizedSortedSet(new TreeSet<Integer>())
                ))
            );
    }

    private Optional<Integer> getSegmentLeader(int index)
    {
        HashMap<Integer, Integer> segmentCounter = new HashMap<>();
        for(int offset = 0; offset < K; offset++){
            Integer keyToDecrease = A[index + offset];
            Integer keyToIncrease = A[index + offset] + 1;
            segmentCounter.put(
                keyToDecrease,
                getCount(keyToDecrease, segmentCounter) -1);
            segmentCounter.put(
                keyToIncrease,
                getCount(keyToIncrease, segmentCounter) +1);
        }

        return segmentCounter.entrySet()
            .stream()
            .filter(entry -> entry.getValue() > N/2.0)
            .map(entry -> entry.getKey())
            .findAny();
    }

    private Integer getCount(Integer key, Map<Integer, Integer> segmentCounter){
        return segmentCounter.getOrDefault(key, globalCounter.getOrDefault(key, 0));
    }  

    private void inputCheck(){
        if(!(N > 0 && N <= 100000))
            throw new IllegalArgumentException("Parameter N is out of bounds [1...100,000]: " + N);
        if(!(N > 0 && M <= 100000))
            throw new IllegalArgumentException("Parameter M is out of bounds [1...100,000]: " + M);
        if(!(K > 0 && K <= N))
            throw new IllegalArgumentException("Parameter K is out of bounds [1..." + N + "]: " + K);
        if(anyArrayElementOutOfBounds())
            throw new IllegalArgumentException("Array Element is out of bounds [1..."+ M +"]");
    }

    private boolean anyArrayElementOutOfBounds(){
        return IntStream.of(A).anyMatch(value -> value < 1 || value > M);
    }

    private int[] toIntArray(SortedSet<Integer> set){
        int[] result = new int[set.size()];
        int i = 0;
        for(int value: set){
            result[i] = value;
            i++;
        }
        return result;
    }
}
