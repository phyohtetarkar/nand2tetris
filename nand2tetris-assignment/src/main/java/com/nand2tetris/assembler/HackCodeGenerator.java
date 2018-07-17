package com.nand2tetris.assembler;

import java.io.IOException;
import java.util.Map;

import com.nand2tetris.InstructionTableRepo;

/**
 * Code generator class for handling Hack command to machine code
 * 
 * @author Phyo Htet Arkar
 *
 */
public class HackCodeGenerator {
	
	private InstructionTableRepo repo;
	private Map<String, String> symbolTable;
	private int alloc = 16;
	
	public HackCodeGenerator(InstructionTableRepo repo, Map<String, String> symbolTable) throws IOException {
		this.repo = repo;
		this.symbolTable = symbolTable;
	}
	
	/**
	 * 
	 * @param cmd address command e.g <code>@Xxx where xxx eq number or symbol<code>
	 */
	public String generateAInstruction(String cmd) {
		String code = null;
		
		if (!HackAssembler.isInteger(cmd) && !symbolTable.containsKey(cmd)) {
			symbolTable.put(cmd, HackAssembler.intToBinary(alloc));
			alloc++;
		}
		
		if (HackAssembler.isInteger(cmd)) {
			code = HackAssembler.intToBinary(Integer.parseInt(cmd));
		} else {
			code = symbolTable.get(cmd);
		}
		
		String aIns = "0" + code;
		
		return aIns;
	}
	
	/**
	 * [dest]=[comp];[jump]
	 * 
	 * @param comp computation command
	 * @param dest destination command
	 * @param jump jump command
	 */
	public String generateCInstruction(String comp, String dest, String jump) {
		
		String c = repo.findComp(comp);
		String d = repo.findDest(dest);
		String j = repo.findJump(jump);
		
		String cIns = "111" + c + d + j;
		return cIns;
	}

}
