package edu.illinois.jflow.benchmark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.CRC32;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

public class Benchmark {

	// TODO: Do repeated measurements
	// TODO: Create Google Caliper measurement version
	public static final long CRC= 2368259493l;

	public static void main(String[] args) throws FileNotFoundException, BitstreamException, DecoderException {

		long startDecode= System.currentTimeMillis();

		decodeWithoutCRC();

		long stopDecode= System.currentTimeMillis();
		System.out.println((stopDecode - startDecode) + "ms");

		if (shouldCRC()) {
			decodeWithCRC();
		}
	}

	private static void decodeWithoutCRC() throws FileNotFoundException, BitstreamException, DecoderException {
		Bitstream stream= new Bitstream(new FileInputStream("inputs/media.mp3"));
		Decoder decoder= new Decoder();
		Header header;

		while ((header= stream.readFrame()) != null) {
			decoder.decodeFrame(header, stream);
			stream.closeFrame();
		}

		stream.close();
	}

	private static void decodeWithCRC() throws FileNotFoundException, BitstreamException, DecoderException {
		Bitstream stream= new Bitstream(new FileInputStream("inputs/media.mp3"));
		Decoder decoder= new Decoder();
		Header header;
		CRC32 crc= new CRC32();
		while ((header= stream.readFrame()) != null) {
			updateCRC32(crc, ((SampleBuffer)decoder.decodeFrame(header, stream)).getBuffer());
			stream.closeFrame();
		}
		long value= crc.getValue();
		if (value != CRC) {
			System.out.println("Got " + value + " but expected " + CRC);
		}
		stream.close();
	}

	private static void updateCRC32(CRC32 crc32, short[] buffer) {
		int length= buffer.length;
		byte[] b= new byte[length * 2];
		for (int i= 0; i < length; i++) {
			short value= buffer[i];
			b[i]= (byte)buffer[i];
			b[i + length]= (byte)((value & 0xff00) >> 8);
		}

		crc32.update(b, 0, b.length);
	}

	private static boolean shouldCRC() {
		return true;
	}
}
