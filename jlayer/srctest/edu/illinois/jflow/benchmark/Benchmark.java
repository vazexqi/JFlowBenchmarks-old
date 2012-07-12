package edu.illinois.jflow.benchmark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.Header;

public class Benchmark {

	// TODO: Do repeated measurements
	// TODO: Create Google Caliper measurement version

	public static void main(String[] args) throws FileNotFoundException, BitstreamException, DecoderException {
		Bitstream stream= new Bitstream(new FileInputStream("inputs/media.mp3"));
		Decoder decoder= new Decoder();
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
}
