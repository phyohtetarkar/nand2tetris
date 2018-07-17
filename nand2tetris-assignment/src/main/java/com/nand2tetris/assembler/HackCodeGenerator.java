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
	private Map<String, Integer> symbolTable;
	private int alloc = 16;
	
	public HackCodeGenerator(InstructionTableRepo repo, Map<String, Integer> symbolTable) throws IOException {
		this.repo = repo;
		this.symbolTable = symbolTable;
	}
	
	/**
	 * 
	 * @param cmd address command e.g <code>@Xxx where xxx eq number or symbol<code>
	 */
	public String generateAInstruction(String cmd) {
		String code = null;
		
		if (!isInteger(cmd) && !symbolTable.containsKey(cmd)) {
			symbolTable.put(cmd, alloc);
			alloc++;
		}
		
		if (isInteger(cmd)) {
			code = intToBinary(Integer.parseInt(cmd));
		} else {
			code = intToBinary(symbolTable.get(cmd));
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
	
	private static boolean isInteger(String cmd) {
		return cmd.matches("[0-9]+");
	}

	private static String intToBinary(int val) {
		return String.format("%015d", Long.parseLong(Long.toBinaryString(val)));
	}

}
