package com.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.nand2tetris.InstructionTableRepo;

/**
 * Assembler class for translating Hack assembly codes to machine codes <br/>
 * those Hack computer understand 
 * 
 * @author Phyo Htet Arkar
 *
 */
public class HackAssembler {
	
	enum CheckMode {
		LABEL, SYMBOL
	}
	
	/**
	 * Symbol table based on Hack assembly syntax
	 * 
	 */
	private final Map<String, Integer> symbolTable = new HashMap<>();

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No file was specified.");
			return;
		}

		File file = new File(args[0]);

		if (!file.exists()) {
			System.out.println("File does not exists.");
			return;
		}

		if (!file.getName().endsWith(".asm")) {
			System.out.println("Not a hack assembly file.");
			return;
		}

		HackAssembler assembler = new HackAssembler();
		assembler.process(file);
	}
	
	public HackAssembler() {
		for (int i = 0; i < 16; i++) {
			symbolTable.put(String.format("R%d", i), i);
		}

		symbolTable.put("SCREEN", 16384);
		symbolTable.put("KBD", 24576);
		symbolTable.put("SP", 0);
		symbolTable.put("LCL", 1);
		symbolTable.put("ARG", 2);
		symbolTable.put("THIS", 3);
		symbolTable.put("THAT", 4);
	}
	
	public void process(File file) {
		
		checkCommand(file, CheckMode.LABEL);
		
		checkCommand(file, CheckMode.SYMBOL);
		
		File outFile = new File(file.getParent(), file.getName().replaceAll("asm", "hack"));
		
		try (HackParser parser = new HackParser(file);
				BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
			
			InstructionTableRepo repo = new InstructionTableRepo();
			HackCodeTranslator trans = new HackCodeTranslator(symbolTable, repo);
			
			while (parser.hasNextCommand()) {
				parser.parse();
				
				if (parser.aInstruction()) {
					
					String code = trans.translateAddr(parser.addr());
					
					String result = "0" + code;
					
					bw.write(result);
					bw.newLine();
					
				} else if (parser.cInstruction()) {
					
					String dest = trans.translateDest(parser.dest());
					String comp = trans.translateComp(parser.comp());
					String jump = trans.translateJump(parser.jump());
					
					String result = "111" + comp + dest + jump;
					
					bw.write(result);
					bw.newLine();
				}
				
			}
			
			System.out.println("Translation successful!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void checkCommand(File file, CheckMode mode) {
		
		int alloc = 16;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			
			String line;
			int count = 0;
			while ((line = br.readLine()) != null) {

				String cmd = line.replaceAll("\\s+", "");

				if (cmd.isEmpty() || cmd.startsWith("//")) {
					continue;
				}
				
				cmd = cmd.contains("//") ? cmd.split("//", 1)[0] : cmd;
				
				switch (mode) {
				case LABEL:
					if (cmd.startsWith("(")) {
						symbolTable.put(cmd.replaceAll("[\\(\\)]", ""), count);
						continue;
					} 
					
					count++;
					break;

				case SYMBOL:
					if (cmd.startsWith("@")) {
						cmd = cmd.replace("@", "");
						
						if (!isInteger(cmd) && !symbolTable.containsKey(cmd)) {
							symbolTable.put(cmd, alloc);
							alloc++;
							
						}
						
					}
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isInteger(String cmd) {
		return cmd.matches("[0-9]+");
	}
	
}
