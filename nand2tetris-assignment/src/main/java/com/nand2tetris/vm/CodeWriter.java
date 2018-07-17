package com.nand2tetris.vm;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CodeWriter {

	private BufferedWriter writer;
	
	public CodeWriter(FileOutputStream out) {
		this.writer = new BufferedWriter(new OutputStreamWriter(out));
	}
	
	public void writeArithmetic(String cmd) {
		
	}
	
	public void writePushPop(String cmd, String segment, int index) throws IOException {
		writer.write(String.format("// %s %s %d", cmd, segment, index));
		
		switch (cmd) {
		case "push":
			writeAddress(index);
			if (!segment.equals("constant")) {
				writer.write("@" + segment + "\n");
				writer.write("A=D+M\n");
				writer.write("D=M\n");
			}
			writer.write("@SP\n");
			writer.write("A=M\n");
			writer.write("M=D\n");
			writer.write("@SP\n");
			writer.write("M=M+1\n");
			
			break;

		case "pop":
			
			writer.write("@SP\n");
			writer.write("M=M-1\n");
			writer.write("A=M\n");
			writer.write("D=M\n");
			writeAddress(index);
			writer.write("@" + segment + "\n");
			writer.write("A=D+M\n");
			writer.write("M=D\n");
			
			break;
		}
	}
	
	private void writeAddress(int index) throws IOException {
		if (index > 1) {
			writer.write("@" + index + "\n");
			writer.write("D=A\n");
		} else {
			writer.write("D=" + index + "\n");
		}
	}
	
}
