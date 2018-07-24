package com.nand2tetris.vm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.nand2tetris.vm.Parser.VMCommand;
import com.nand2tetris.vm.code.VirtualMachineCode;

public class CodeWriter implements AutoCloseable {

	private BufferedWriter writer;
	private VirtualMachineCode vmCode;

	public CodeWriter(File file) throws IOException {
		writer = new BufferedWriter(new FileWriter(file));
		vmCode = VirtualMachineCode.create();
	}

	public void writeArithmetic(String cmd) throws IOException {
		writer.write(String.format("// %s\n", cmd));
		System.out.println(String.format("// %s", cmd));
		
		String codes = vmCode.generate(VMCommand.C_ARITHMETIC, cmd);
		
		writer.write(codes);
		
	}

	public void writePushPop(VMCommand cmd, String segment, int index) throws IOException {
		writer.write(String.format("// %s %s %d\n", cmd, segment, index));
		System.out.println(String.format("// %s %s %d", cmd, segment, index));
		
		String codes = vmCode.generate(cmd, segment, index);
		
		writer.write(codes);
		
	}

	@Override
	public void close() throws Exception {
		writer.close();
	}

}
