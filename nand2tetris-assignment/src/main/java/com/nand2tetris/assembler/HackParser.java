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
public class HackParser {
	
	enum HackCommand {
		A_COMMAND, C_COMMAND, L_COMMAND, COMMENT_OR_EMPTY
	}
	
	enum CommandSyntax {
		
	}
	
	private BufferedReader reader;
	private String cmd;
	
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
		
		if (cmd.startsWith("(")) {
			
			symbol = cmd.replaceAll("[\\(\\)]", "");
			
			return HackCommand.L_COMMAND;
		} else if (cmd.startsWith("@")) {
			
			symbol = cmd.replace("@", "");
			
			return HackCommand.A_COMMAND;
			
		} else {
			
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
		return Pattern.matches("@[a-zA-Z0-9]+", cmd);
	}
	
	private boolean isCSyntax() {
		return Pattern.matches("", cmd);
	}
	
	private boolean isLSyntax() {
		return Pattern.matches("\\([A-Z]+\\)", cmd);
	}
	
	private void reset() {
		symbol = null;
		comp = null;
		dest = "null";
		jump = "null";
	}
}
