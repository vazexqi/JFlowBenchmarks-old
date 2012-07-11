package edu.illinois.jflow.benchmark;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

public class Benchmark {

	// TODO: Do repeated measurements
	// TODO: Create Google Caliper measurement version

	public static void main(String[] args) throws Exception {
		final File dir= mkdir("dir");
		final File input= new File("inputs/media.dat");
		final File compressed= new File(dir, "media.compressed.bz2");

		// Compression - need to use buffered streams or the results will be too I/O intensive
		long startCompressed= System.currentTimeMillis();

		OutputStream out= new FileOutputStream(compressed);
		BufferedOutputStream bufferedOutputStream= new BufferedOutputStream(out);
		final CompressorOutputStream bzip2Compressor= new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.BZIP2, bufferedOutputStream);

		IOUtils.copy(new BufferedInputStream(new FileInputStream(input)), bzip2Compressor);
		bzip2Compressor.close();

		long stopCompressed= System.currentTimeMillis();
		System.out.println((stopCompressed - startCompressed) + "ms");

		// Decompression - need to use buffered streams or the results will be too I/O intensive
//		long startDecompressed= System.currentTimeMillis();
//
//		final File decompressed= new File(dir, "media.decompressed");
//		final CompressorInputStream bzip2Decompressor= new CompressorStreamFactory().createCompressorInputStream(CompressorStreamFactory.BZIP2,
//				new BufferedInputStream(new FileInputStream(compressed)));
//
//		IOUtils.copy(bzip2Decompressor, new BufferedOutputStream(new FileOutputStream(decompressed)));
//		long stopDecompressed= System.currentTimeMillis();
//		System.out.println((stopDecompressed - startDecompressed) + "ms");

	}

	static File mkdir(String name) throws IOException {
		File f= File.createTempFile(name, "");
		f.delete();
		f.mkdir();
		return f;
	}
}
