package com.cloud.bookshop.web.async;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// Streaming in JDK primarily focuses on computationally intensive scenarios.
public class ParallelStreamDemo {

    public static long longStreamParallelSum(long n) {
        return LongStream.rangeClosed(0, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long sequenceSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    public static long iterativeSum(long n) {
        long ret = 0L;
        for (int i = 0; i < n; i++) {
            ret += i;
        }
        return ret;
    }

    public static long test(Function<Long, Long> computer, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            computer.apply(n);
            long cost = System.currentTimeMillis() - start;
            if (cost < fastest) {
                fastest = cost;
            }
        }
        return fastest;
    }

    public static long lowEffectiveStreamDemo(long n) {
        Counter counter = new Counter();
        LongStream.rangeClosed(0, n).parallel().forEach(counter::add);
        return counter.total;
    }

    public static void main(String[] args) {
        long n = 20_200_200;
        System.out.println("low effective parallel operator: " + test(ParallelStreamDemo::lowEffectiveStreamDemo,
                n));
        System.out.println("sequence operator: " + test(ParallelStreamDemo::sequenceSum, n));
        System.out.println("parallel operator: " + test(ParallelStreamDemo::parallelSum, n));
        System.out.println("iterator  operator: " + test(ParallelStreamDemo::iterativeSum, n));
        System.out.println("long stream parallel  operator: " + test(ParallelStreamDemo::iterativeSum,
                n));
    }
}
