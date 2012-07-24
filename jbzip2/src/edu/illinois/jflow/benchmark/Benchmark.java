package edu.illinois.jflow.benchmark;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.itadaki.bzip2.BZip2OutputStream;

public class Benchmark {

	// TODO: Do repeated measurements
	// TODO: Create Google Caliper measurement version

	public static void main(String[] args) throws Exception {
		final File dir= mkdir("dir");
		final File inputFile= new File("inputs/media.dat");
		final File outputFile= new File("media.compressed.bz2");

		InputStream fileInputStream= new BufferedInputStream(new FileInputStream(inputFile));
		BufferedOutputStream bufferedOutputStream= new BufferedOutputStream(new FileOutputStream(outputFile));
		BZip2OutputStream compressorStream= new BZip2OutputStream(bufferedOutputStream);

		// Compression - need to use buffered streams or the results will be too I/O intensive
		long startCompressed= System.currentTimeMillis();

		byte[] buffer= new byte[5242880];
		int bytesRead;
		while ((bytesRead= fileInputStream.read(buffer)) != -1) {
			compressorStream.write(buffer, 0, bytesRead);
		}
        compressorStream.shutDownExecutor();
        long stopCompressed= System.currentTimeMillis();
        System.out.println((stopCompressed - startCompressed) + "ms");

		fileInputStream.close();
		compressorStream.close();


	}

	static File mkdir(String name) throws IOException {
		File f= File.createTempFile(name, "");
		f.delete();
		f.mkdir();
		return f;
	}
}
