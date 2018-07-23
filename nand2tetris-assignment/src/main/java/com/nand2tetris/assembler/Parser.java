package com.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Parser class for handling Hack machine language
 * 
 * @author Phyo Htet Arkar
 *
 */
public class Parser {
	
	enum HackCommand {
		A_COMMAND, C_COMMAND, L_COMMAND, COMMENT_OR_EMPTY
	}
	
	private static final String A_REGEX = "[@a-zA-Z0-9]+";
	private static final String C_REGEX = "[A-Z0-9]+={0,1}[A-Z0-9+\\-!|&]*;{0,1}[A-Z]*";
	private static final String L_REGEX = "\\({1}[A-Z]+\\){1}";
	
	private BufferedReader reader;
	private String cmd;
	private int line;
	
	private String symbol;
	
	private String comp;
	private String dest;
	private String jump;
	
	/**
	 * Check next command exists
	 * 
	 * @return true if next command exists
	 * @throws IOException 
	 */
	public boolean hasNextCommand() throws IOException {
		cmd = reader.readLine();
		line += 1;
		return cmd != null;
	}

	/**
	 * Parse current reading command
	 * 
	 * @return type of command
	 * @see HackCommand
	 */
	public HackCommand parse() {
		reset();
		
		cmd = cmd.replaceAll("\\s+", "");
		
		if (cmd.isEmpty() || cmd.startsWith("//")) {
			return HackCommand.COMMENT_OR_EMPTY;
		}
		
		cmd = cmd.contains("//") ? cmd.split("//", 1)[0] : cmd;
		
		if (isLSyntax()) {
			
			symbol = cmd.replaceAll("[\\(\\)]", "");
			
			return HackCommand.L_COMMAND;
		} else if (isASyntax()) {
			
			symbol = cmd.replace("@", "");
			
			return HackCommand.A_COMMAND;
			
		} else if (isCSyntax()) {
			
			comp = new String(cmd);
			
			if (comp.contains("=")) {
				String[] ary = comp.split("=");
				dest = ary[0];
				comp = ary[1];
			}
			
			if (cmd.contains(";")) {
				String[] ary = comp.split(";");
				jump = ary[1];
				comp = ary[0];
			}
			
			return HackCommand.C_COMMAND;
		}
		
		throw new RuntimeException(String.format("Syntax error at line: %d", line));
		
	}
	
	public void setInputStream(FileInputStream in) throws FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(in));
	}
	
	/**
	 * Call only if command is A_COMMAND or L_COMMAND
	 * 
	 * @return
	 */
	public String symbol() {
		return symbol;
	}
	
	/**
	 * Call only if command is C_COMMAND
	 * 
	 * @return
	 */
	public String comp() {
		return comp;
	}

	/**
	 * Call only if command is C_COMMAND
	 * 
	 * @return
	 */
	public String dest() {
		return dest;
	}

	/**
	 * Call only if command is C_COMMAND
	 * 
	 * @return
	 */
	public String jump() {
		return jump;
	}
	
	private boolean isASyntax() {
		return Pattern.matches(A_REGEX, cmd);
	}
	
	private boolean isCSyntax() {
		return Pattern.matches(C_REGEX, cmd);
	}
	
	private boolean isLSyntax() {
		return Pattern.matches(L_REGEX, cmd);
	}
	
	private void reset() {
		symbol = null;
		comp = null;
		dest = "null";
		jump = "null";
	}
}
