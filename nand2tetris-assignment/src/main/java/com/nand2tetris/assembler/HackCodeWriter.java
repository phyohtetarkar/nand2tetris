package com.nand2tetris.assembler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.nand2tetris.InstructionTableRepo;

/**
 * Code writer class for handling Hack command to machine code
 * 
 * @author Phyo Htet Arkar
 *
 */
public class HackCodeWriter implements AutoCloseable {
	
	private InstructionTableRepo repo;
	private Map<String, String> symbolTable;
	private BufferedWriter writer;
	private int alloc = 16;
	
	public HackCodeWriter(InstructionTableRepo repo, Map<String, String> symbolTable, File out) throws IOException {
		this.repo = repo;
		this.symbolTable = symbolTable;
		this.writer = new BufferedWriter(new FileWriter(out));
	}
	
	/**
	 * 
	 * @param cmd address command e.g <code>@Xxx where xxx eq number or symbol<code>
	 * @throws IOException
	 */
	public void writeAInstruction(String cmd) throws IOException {
		if (!HackAssembler.isInteger(cmd) && !symbolTable.containsKey(cmd)) {
			symbolTable.put(cmd, HackAssembler.intToBinary(alloc));
			alloc++;

		}
		
		String code = symbolTable.get(cmd);
		String aIns = "0" + code;
		
		write(aIns);
	}
	
	/**
	 * [dest]=[comp];[jump]
	 * 
	 * @param comp computation command
	 * @param dest destination command
	 * @param jump jump command
	 * @throws IOException
	 */
	public void writeCInstruction(String comp, String dest, String jump) throws IOException {
		
		String c = repo.findComp(comp);
		String d = repo.findDest(dest);
		String j = repo.findJump(jump);
		
		String cIns = "111" + c + d + j;
		write(cIns);
	}
	
	private void write(String code) throws IOException {
		writer.write(code);
		writer.newLine();
	}

	@Override
	public void close() throws IOException {
		writer.close();
		writer = null;
	}

}
