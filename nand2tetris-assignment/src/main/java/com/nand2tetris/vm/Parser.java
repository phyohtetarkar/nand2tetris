package com.nand2tetris.vm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Parser class for handling Hack VM command
 * 
 * @author Phyo Htet Arkar
 *
 */
public class Parser implements AutoCloseable {
	
	public enum VMCommand {
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
	
	private static final String ARITHMETIC_REGEX = "add|sub|neg|eq|gt|lt|and|or|not";
	private static final String MEMORY_ACCESS_REGEX = 
			"(push|pop)\\s(constant|local|static|argument|this|that|temp|pointer)\\s[0-9]+";

	private BufferedReader reader;
	private String cmd;
	private int line;
	private String fileName;

	private String arg1;
	private Integer arg2;
	
	public Parser(File file) throws FileNotFoundException {
		this.fileName = file.getName().split("\\.")[0];
		this.reader = new BufferedReader(new FileReader(file));
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
			arg2 = Integer.parseInt(ms[2]);
			arg1 = formatSegment(ms[1]);
			
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
	
	private String formatSegment(String segment) {
		switch (segment) {
		case "local":
			return "LCL";
		case "argument":
			return "ARG";
		case "this":
			return "THIS";
		case "that":
			return "THAT";
		case "temp":
			return "TEMP";
		case "static":
			return String.format("%s.%d", fileName, arg2);
		}
		
		return segment;
	}

	private void reset() {
		arg1 = null;
		arg2 = null;
	}

	@Override
	public void close() throws Exception {
		reader.close();
	}

}
