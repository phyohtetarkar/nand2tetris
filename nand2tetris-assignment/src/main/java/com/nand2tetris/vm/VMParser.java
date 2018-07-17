package com.nand2tetris.vm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class VMParser {
	
	enum VMCommand {
		C_ARITHMETIC, 
		C_PUSH, 
		C_POP, 
		C_LABEL, 
		C_GOTO, 
		C_IF, 
		C_FUNCTION, 
		C_RETURN, 
		C_CALL,
		COMMENT_OR_EMPTY,
	}
	
	private BufferedReader reader;
	private String cmd;
	
	private String arg1;
	private String arg2;
	
	public boolean hasNextCommand() throws IOException {
		cmd = reader.readLine();
		return cmd != null;
	}
	
	public VMCommand parse() {
		reset();
		
		cmd = cmd.trim();
		
		if (cmd.isEmpty() || cmd.startsWith("//")) {
			return VMCommand.COMMENT_OR_EMPTY;
		}
		
		return null;
	}
	
	private void reset() {
		arg1 = null;
		arg2 = null;
	}

	public String getArg1() {
		return arg1;
	}

	public String getArg2() {
		return arg2;
	}
	
	public void setInputStream(FileInputStream in) throws FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(in));
	}
	
}
