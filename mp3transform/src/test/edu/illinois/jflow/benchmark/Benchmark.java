package edu.illinois.jflow.benchmark;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

import org.mp3transform.Bitstream;
import org.mp3transform.Decoder;
import org.mp3transform.Header;

public class Benchmark {

	private static final String TRACK= "inputs/track5.mp3";

	// TODO: Do repeated measurements
	// TODO: Create Google Caliper measurement version

	public static void main(String[] args) throws IOException {
		decodeWithoutCRC();

		if (shouldCRC()) {
			decodeWithCRC();
		}

	}

	private static void decodeWithoutCRC() throws IOException {
		Bitstream stream= new Bitstream(new BufferedInputStream(new FileInputStream(TRACK), 128 * 1024));
		Decoder decoder= Decoder.createBenchmarkDecoder();
		Header header;

		long startDecode= System.currentTimeMillis();

		while ((header= stream.readFrame()) != null) {
			decoder.decodeFrame(header, stream);
			stream.closeFrame();
		}

		long stopDecode= System.currentTimeMillis();
		System.out.println((stopDecode - startDecode) + "ms");

		stream.close();
	}

	private static void decodeWithCRC() throws IOException {
		Bitstream stream= new Bitstream(new FileInputStream(TRACK));
		Decoder decoder= Decoder.createBenchmarkDecoder();
		Header header;
		CRC32 crc= new CRC32();
		long startDecode= System.currentTimeMillis();

		while ((header= stream.readFrame()) != null) {
			updateCRC32(crc, decoder.decodeFrame(header, stream));
			stream.closeFrame();
		}

		long stopDecode= System.currentTimeMillis();

		System.out.println((stopDecode - startDecode) + "ms");
		System.out.println("CRC: " + crc.getValue());

		stream.close();
	}

	private static void updateCRC32(CRC32 crc32, byte[] buffer) {
		crc32.update(buffer, 0, buffer.length);
	}

	private static boolean shouldCRC() {
		return false;
	}

}
