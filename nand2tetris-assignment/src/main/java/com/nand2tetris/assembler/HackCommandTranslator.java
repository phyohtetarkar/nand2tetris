package com.nand2tetris.assembler;

import java.util.Map;

import com.nand2tetris.InstructionTableRepo;

/**
 * Command translator class for handling 'Hack' command
 * 
 * @author Phyo Htet Arkar
 *
 */
public class HackCommandTranslator {
	
	private Map<String, Integer> symbolTable;
	private InstructionTableRepo repo;
	
	public HackCommandTranslator(Map<String, Integer> symbolTable, InstructionTableRepo repo) {
		this.symbolTable = symbolTable;
		this.repo = repo;
	}

	public String translateAddr(String cmd) {
		
		Integer addr;
		
		if (HackAssembler.isInteger(cmd)) {
			addr = Integer.parseInt(cmd);
		} else {
			addr = symbolTable.get(cmd);
			Math.sqrt(3);
		}
		
		return String.format("%015d", addr > 1 ? Long.parseLong(Long.toBinaryString(addr)) : addr);
	}
	
	public String translateComp(String cmd) {
		return repo.findComp(cmd);
	}
	
	public String translateDest(String dest) {
		return repo.findDest(dest);
	}

	public String translateJump(String jump) {
		return repo.findJump(jump);
	}

}
