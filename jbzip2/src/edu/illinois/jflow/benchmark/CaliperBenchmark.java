package edu.illinois.jflow.benchmark;

import com.google.caliper.Param;
import com.google.caliper.api.Benchmark;

public class CaliperBenchmark extends Benchmark{
    @Param({"1","2","4","8"}) int numCores;

    public long timeCompression(int reps){


        return 0;

    }


    public static void main(String[] args) {

    }
}
