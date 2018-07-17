package com.nand2tetris.assembler;

import java.io.IOException;
import java.util.List;

/**
 * Parser class for handling Hack machine language
 * 
 * @author Phyo Htet Arkar
 *
 */
public class HackParser {
	
	enum CommandType {
		A_COMMAND, C_COMMAND, L_COMMAND
	}
	
	private List<String> instructions;
	private int nextLine = 0;
	
	private String addr;
	
	private String comp;
	private String dest;
	private String jump;
	
	public HackParser(List<String> instructions) {
		this.instructions = instructions;
	}
	
	public boolean hasNextCommand() throws IOException {
		return nextLine < instructions.size();
	}

	public CommandType parse() {
		reset();
		
		String cmd = instructions.get(nextLine);
		nextLine += 1;
		
		cmd = cmd.replaceAll("\\s+", "");
		
		if (cmd.isEmpty() || cmd.startsWith("//") || cmd.startsWith("(")) {
			return CommandType.L_COMMAND;
		}
		
		cmd = cmd.contains("//") ? cmd.split("//", 1)[0] : cmd;
		
		System.out.println("Command: " + cmd);
		
		if (cmd.startsWith("@")) {
			
			addr = cmd.replace("@", "");
			
			return CommandType.A_COMMAND;
			
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
			
			return CommandType.C_COMMAND;
			
		}
		
	}
	
	public String addr() {
		return addr;
	}

	public String comp() {
		return comp;
	}

	public String dest() {
		return dest;
	}

	public String jump() {
		return jump;
	}

	private void reset() {
		addr = null;
		comp = null;
		dest = "null";
		jump = "null";
	}
	
}
