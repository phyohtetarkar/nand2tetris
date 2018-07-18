package com.nand2tetris.vm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter implements AutoCloseable {

	private BufferedWriter writer;
	private VMCode vmCode;
	
	private int eq = 0;
	private int gt = 0;
	private int lt = 0;

	public CodeWriter(File file) throws IOException {
		writer = new BufferedWriter(new FileWriter(file));
		vmCode = VMCode.create();
	}

	public void writeArithmetic(String cmd) throws IOException {
		writer.write(String.format("// %s\n", cmd));
		System.out.println(String.format("// %s", cmd));
		
		String codes = vmCode.generate(cmd);
		
		writer.write(codes);
		
		writer.write("@SP\n");
		
		if (cmd.equals("neg")) {
			writer.write("A=M-1\n");
			writer.write("M=-M\n");
			return;
		} 
		
		if (cmd.equals("not")) {
			writer.write("A=M-1\n");
			writer.write("M=!M\n");
			return;
		}
		
		writer.write("M=M-1\n");
		writer.write("A=M\n");
		writer.write("D=M\n");
		writer.write("@SP\n");
		writer.write("A=M-1\n");
		
		switch (cmd) {
		case "add":
			writer.write("M=D+M\n");
			break;
			
		case "sub":
			writer.write("M=M-D\n");
			break;
			
		case "eq":
			writer.write("D=M-D\n");
			writer.write("M=-1\n");
			writer.write(String.format("@EQUAL%d\n", ++eq));
			writer.write("D;JEQ\n");
			writer.write("@SP\n");
			writer.write("A=M-1\n");
			writer.write("M=0\n");
			writer.write(String.format("(EQUAL%d)\n", eq));
			break;
			
		case "gt":
			writer.write("D=M-D\n");
			writer.write("M=-1\n");
			writer.write(String.format("@GREATER%d\n", ++gt));
			writer.write("D;JGT\n");
			writer.write("@SP\n");
			writer.write("A=M-1\n");
			writer.write("M=0\n");
			writer.write(String.format("(GREATER%d)\n", gt));
			break;
			
		case "lt":
			writer.write("D=M-D\n");
			writer.write("M=-1\n");
			writer.write(String.format("@LESS%d\n", ++lt));
			writer.write("D;JLT\n");
			writer.write("@SP\n");
			writer.write("A=M-1\n");
			writer.write("M=0\n");
			writer.write(String.format("(LESS%d)\n", lt));
			break;
			
		case "and":
			writer.write("M=D&M\n");
			break;
			
		case "or":
			writer.write("M=D|M\n");
			break;
		}

	}

	public void writePushPop(String cmd, String segment, int index) throws IOException {
		writer.write(String.format("// %s %s %d\n", cmd, segment, index));
		System.out.println(String.format("// %s %s %d", cmd, segment, index));
		
		String codes = vmCode.generate(cmd, segment, index);
		
		writer.write(codes);
		
		if (index > 1) {
			writer.write("@" + index + "\n");
			writer.write("D=A\n");
		} else {
			writer.write("D=" + index + "\n");
		}

		switch (cmd) {
		case "push":
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
			writer.write("@" + segment + "\n");
			writer.write("D=D+M\n");
			writer.write("@R13\n");
			writer.write("M=D\n");

			writer.write("@SP\n");
			writer.write("M=M-1\n");
			writer.write("A=M\n");
			writer.write("D=M\n");
			
			writer.write("@R13\n");
			writer.write("A=M\n");
			writer.write("M=D\n");
			break;
		}
	}

	@Override
	public void close() throws Exception {
		writer.close();
	}

}
