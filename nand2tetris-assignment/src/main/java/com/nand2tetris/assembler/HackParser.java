package com.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser class for handling Hack machine language
 * 
 * @author Phyo Htet Arkar
 *
 */
public class HackParser implements AutoCloseable {
	
	enum CommandType {
		A_COMMAND, C_COMMAND, L_COMMAND
	}
	
	private BufferedReader reader;
	private String cmd;
	
	private String addr;
	
	private String comp;
	private String dest;
	private String jump;
	
	public HackParser(File file) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(file));
	}

	@Override
	public void close() throws IOException {
		reader.close();
		reader = null;
	}

	public boolean hasNextCommand() throws IOException {
		cmd = reader.readLine();
		return cmd != null;
	}

	public CommandType parse() {
		reset();
		
		cmd = cmd.replaceAll("\\s+", "");
		
		if (isNeedToSkip()) {
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
	
	private boolean isNeedToSkip() {
		return cmd.isEmpty() || cmd.startsWith("//") || cmd.startsWith("(");
	}

	private void reset() {
		addr = null;
		comp = null;
		dest = "null";
		jump = "null";
	}
	
}
