package com.nand2tetris.vm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

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
	
	private static final String LOC = "[constant|local|static|argument|this|that|temp]{1}";
	
	private static final String ARITHMETIC_REGEX = "add|sub|neg|eq|gt|lt|and|or|not";
	private static final String MEMORY_ACCESS_REGEX = 
			String.format("(push\\s%s\\s\\d)|(pop\\s%s\\s\\d)", LOC, LOC);

	private BufferedReader reader;
	private String cmd;
	private int line;

	private String arg1;
	private Integer arg2;
	
	public VMParser(FileInputStream in) {
		this.reader = new BufferedReader(new InputStreamReader(in));
	}

	public boolean hasNextCommand() throws IOException {
		cmd = reader.readLine();
		line += 1;
		return cmd != null;
	}

	public VMCommand parse() {
		reset();

		cmd = cmd.trim();

		if (cmd.isEmpty() || cmd.startsWith("//")) {
			return VMCommand.COMMENT_OR_EMPTY;
		}
		
		cmd = cmd.contains("//") ? cmd.split("//", 1)[0].trim() : cmd;
		
		if (isMemoryAccessSyntax()) {
			String[] ms = cmd.split("\\s");
			arg1 = ms[1];
			arg2 = Integer.parseInt(ms[2]);
			
			switch (ms[0]) {
			case "push":
				return VMCommand.C_PUSH;

			case "pop":
				return VMCommand.C_POP;
			}
		} else if (isArithmeticSyntax()) {
			arg1 = cmd;
			return VMCommand.C_ARITHMETIC;
			
		}

		throw new RuntimeException(String.format("Syntax error at line: %s", line));
	}

	public String getArg1() {
		return arg1;
	}

	public Integer getArg2() {
		return arg2;
	}
	
	private boolean isArithmeticSyntax() {
		return Pattern.matches(ARITHMETIC_REGEX, cmd);
	}
	
	private boolean isMemoryAccessSyntax() {
		return Pattern.matches(MEMORY_ACCESS_REGEX, cmd);
	}

	private void reset() {
		arg1 = null;
		arg2 = null;
	}

}
