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

        this.globalCounter = computeGlobalCounter();
    }

    public int[] findPotentialLeaders(){
        return sortedSetToIntArray(
            IntStream.rangeClosed(0, N-K)
                .parallel()
                .mapToObj(this::findSegmentLeader)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(() -> 
                    Collections.synchronizedSortedSet(new TreeSet<Integer>())
                ))
            );
    }

    private Optional<Integer> findSegmentLeader(int index)
    {
        return computeSegmentCounter(index)
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() > N/2.0)
            .map(entry -> entry.getKey())
            .findAny();
    }
    
    private Map<Integer, Integer> computeGlobalCounter(){
        return IntStream.of(A)
            .boxed()
            .collect(Collectors.toMap(
                Function.identity(), 
                value -> 1,
                (prev, next) -> prev + 1
            ));
    }

    private Map<Integer, Integer> computeSegmentCounter(int index){
        HashMap<Integer, Integer> segmentCounter = new HashMap<>();
        for(int offset = 0; offset < K; offset++){
            Integer keyToDecrease = A[index + offset];
            Integer keyToIncrease = A[index + offset] + 1;
            segmentCounter.put(
                keyToDecrease,
                getSegmentCountForNumber(keyToDecrease, segmentCounter) -1);
            segmentCounter.put(
                keyToIncrease,
                getSegmentCountForNumber(keyToIncrease, segmentCounter) +1);
        }
        return segmentCounter;
    }

    private Integer getSegmentCountForNumber(Integer key, Map<Integer, Integer> segmentCounter){
        return segmentCounter.getOrDefault(key, globalCounter.getOrDefault(key, 0));
    }

    private int[] sortedSetToIntArray(SortedSet<Integer> set){
        int[] result = new int[set.size()];
        int i = 0;
        for(int value: set){
            result[i] = value;
            i++;
        }
        return result;
    }

    private void inputCheck(){
        checkParameterBounds(N, "N", 1, 100000);
        checkParameterBounds(M, "M", 1, 100000);
        checkParameterBounds(K, "K", 1, N);
        checkArrayElementBounds();
    }

    private void checkParameterBounds(int parameterValue, String parameterName, int lowerBounds, int upperBounds){
        if(parameterValue < lowerBounds || parameterValue > upperBounds)
            throw new IllegalArgumentException("Parameter "+parameterName+" is out of bounds ["+lowerBounds+"..."+upperBounds+"]: " + parameterValue);
    }

    private void checkArrayElementBounds(){
        if(anyArrayElementOutOfBounds())
            throw new IllegalArgumentException("An Array Element is out of bounds [1..."+ M +"]");
    }

    private boolean anyArrayElementOutOfBounds(){
        return IntStream.of(A).anyMatch(value -> value < 1 || value > M);
    }
}
