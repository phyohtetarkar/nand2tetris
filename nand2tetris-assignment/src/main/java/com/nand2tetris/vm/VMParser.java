package com.nand2tetris.vm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class VMParser implements AutoCloseable {
	
	private BufferedReader reader;
	private String cmd;

	public VMParser(File file) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(file));
	}

	@Override
	public void close() throws Exception {
		reader.close();
		reader = null;
	}
	
	public boolean hasNextCommand() throws IOException {
		cmd = reader.readLine();
		return cmd != null;
	}
	
	public void parse() {
		reset();
		
		cmd = cmd.replaceAll("\\s+", "");
		
	}
	
	private void reset() {
		
	}
	
}
