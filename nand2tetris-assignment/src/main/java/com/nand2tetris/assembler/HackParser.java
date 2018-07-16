package com.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser class for handling 'Hack' machine language
 * 
 * @author Phyo Htet Arkar
 *
 */
public class HackParser implements AutoCloseable {
	
	private BufferedReader reader;
	private String cmd;
	
	private String addr;
	
	private String comp;
	private String dest;
	private String jump;
	
	private boolean aInstruction;
	private boolean cInstruction;
	
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

	public void parse() {
		reset();
		
		cmd = cmd.replaceAll("\\s+", "");
		
		if (isNeedToSkip()) {
			return;
		}
		
		cmd = cmd.contains("//") ? cmd.split("//", 1)[0] : cmd;
		
		System.out.println("Command: " + cmd);
		
		if (cmd.startsWith("@")) {
			aInstruction = true;
			addr = cmd.replace("@", "");
		} else {
			cInstruction = true;
			dest = "null";
			jump = "null";
			
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
			
		}
		
	}

	public boolean aInstruction() {
		return aInstruction;
	}

	public boolean cInstruction() {
		return cInstruction;
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
		aInstruction = false;
		cInstruction = false;
		
		addr = null;
		comp = null;
		dest = null;
		jump = null;
	}
	
}
