package edu.illinois.jflow.benchmark;

import com.google.caliper.Param;
import com.google.caliper.api.Benchmark;
import com.google.caliper.api.VmParam;
import com.google.caliper.runner.CaliperMain;
import org.itadaki.bzip2.BZip2OutputStream;

import java.io.*;

public class CaliperBenchmark extends Benchmark {
    @Param({"1", "2", "4", "8"})
    int numCores;

    @VmParam({"-Xms1024M"})
    String xms;
    @VmParam({"-Xmx1024M"})
    String xmx;

    public long timeCompression(int reps) throws Exception {
        long totalTime = 0;
        final File inputFile = new File("inputs/media.dat");
        final File outputFile = new File("media.compressed.bz2");
        for (int i = 0; i < reps; i++) {
            InputStream fileInputStream = new BufferedInputStream(new FileInputStream(inputFile));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
            BZip2OutputStream compressorStream = new BZip2OutputStream(bufferedOutputStream);
            // Compression - need to use buffered streams or the results will be too I/O intensive
            long startCompressed = System.currentTimeMillis();

            byte[] buffer = new byte[5242880];
            int bytesRead;
            compressorStream.setCores(numCores);
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                compressorStream.write(buffer, 0, bytesRead, numCores);
            }
            compressorStream.shutDownExecutor();
            long stopCompressed = System.currentTimeMillis();
            totalTime += stopCompressed - startCompressed;
            fileInputStream.close();
            compressorStream.close();

        }
        return totalTime;
    }

    public static void main(String[] args) throws Exception {
        String[] compressArg = {"-i", "compress"};
        CaliperMain.main(CaliperBenchmark.class, compressArg);

    }
}
