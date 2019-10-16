package com.soprasteria.arrayleader;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class SolutionTest 
{

    @Test
    public void shouldReturnExpectedResultForTestCaseOne()
    {
        int K = 3;
        int M = 5;
        int[] A = new int[] {2, 1, 3, 1, 2, 2, 3};
        int[] expected = new int[] {2, 3};
        int[] actual = new Solution(K, M, A).findPotentialLeaders();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldReturnExpectedResultForTestCaseTwo()
    {
        int K = 4;
        int M = 2;
        int[] A = new int[] {1, 2, 2, 1, 2};
        int[] expected = new int[] {2, 3};
        int[] actual = new Solution(K, M, A).findPotentialLeaders();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldReturnExpectedResultForTestCaseThree()
    {
        int K = 4;
        int M = 5;
        int[] A = new int[] {2, 1, 2, 2};
        int[] expected = new int[] {3};
        int[] actual = new Solution(K, M, A).findPotentialLeaders();
        assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNIsOutsideLowerBounds(){
        int[] A = new int[] {};
        int K = 0;
        int M = 0;

        new Solution(K, M, A).findPotentialLeaders();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNIsOutsideUpperBounds(){
        int[] A = new int[100001];
        int K = 0;
        int M = 0;

        new Solution(K, M, A).findPotentialLeaders();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenMIsOutsideLowerBounds(){
        int M = 0;
        
        int K = 4;
        int[] A = new int[] {1, 2, 2, 1, 2};

        new Solution(K, M, A).findPotentialLeaders();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenMIsOutsideUpperBounds(){
        int M = 100001;

        int K = 4;
        int[] A = new int[] {1, 2, 2, 1, 2};

        new Solution(K, M, A).findPotentialLeaders();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenKIsOutsideLowerBounds(){
        int K = 0;

        int M = 2;
        int[] A = new int[] {1, 2, 2, 1, 2};

        new Solution(K, M, A).findPotentialLeaders();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenKIsOutsideUpperBounds(){
        int K = 6;

        int M = 2;
        int[] A = new int[] {1, 2, 2, 1, 2};

        new Solution(K, M, A).findPotentialLeaders();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArrayElementIsOutsideLowerBounds(){
        int K = 4;
        int M = 2;
        int[] A = new int[] {1, 2, 2, 1, 0};

        new Solution(K, M, A).findPotentialLeaders();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArrayElementIsOutsideUpperBounds(){
        int K = 4;
        int M = 2;
        int[] A = new int[] {1, 2, 2, 1, 3};

        new Solution(K, M, A).findPotentialLeaders();
    }
}
