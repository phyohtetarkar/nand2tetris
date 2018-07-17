package com.nand2tetris.assembler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public HackAssembler() { }

	public void process(File file) {
		
		try {
			
			File outFile = new File(file.getParent(), file.getName().replaceAll("asm", "hack"));
			
			final Map<String, String> symbolTable = new HashMap<>();
			final List<String> instructions = Files.readAllLines(file.toPath());
			System.out.println(instructions);
			
			final List<String> machineCodes = new ArrayList<>();
			
			initSymbols(symbolTable);

			checkLabel(instructions, symbolTable);
			
			HackCodeGenerator gen = new HackCodeGenerator(new InstructionTableRepo(), symbolTable);
			HackParser parser = new HackParser(instructions);

			while (parser.hasNextCommand()) {
				switch (parser.parse()) {
				case A_COMMAND:
					machineCodes.add(gen.generateAInstruction(parser.addr()));
					break;

				case C_COMMAND:
					machineCodes.add(gen.generateCInstruction(parser.comp(), parser.dest(), parser.jump()));
					break;

				default:
					break;
				};

			}
			
			if (outFile.exists()) {
				outFile.delete();
			}
			
			Files.write(outFile.toPath(), machineCodes, StandardOpenOption.CREATE);

			System.out.println("Translation successful!");

		} catch (Exception e) {
			e.printStackTrace();
		}  

	}

	private void checkLabel(List<String> instructions, Map<String, String> symbolTable) {

		try {

			int index = 0;

			for (String cmd : instructions) {
				cmd = cmd.replaceAll("\\s+", "");

				if (cmd.isEmpty() || cmd.startsWith("//")) {
					continue;
				}

				cmd = cmd.contains("//") ? cmd.split("//", 1)[0] : cmd;

				if (cmd.startsWith("(")) {
					symbolTable.put(cmd.replaceAll("[\\(\\)]", ""), intToBinary(index));
					continue;
				}

				index++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initSymbols(Map<String, String> symbolTable) {
		for (int i = 0; i < 16; i++) {
			symbolTable.put(String.format("R%d", i), intToBinary(i));
		}

		symbolTable.put("SCREEN", intToBinary(16384));
		symbolTable.put("KBD", intToBinary(24576));
		symbolTable.put("SP", intToBinary(0));
		symbolTable.put("LCL", intToBinary(1));
		symbolTable.put("ARG", intToBinary(2));
		symbolTable.put("THIS", intToBinary(3));
		symbolTable.put("THAT", intToBinary(4));
	}

	static boolean isInteger(String cmd) {
		return cmd.matches("[0-9]+");
	}

	static String intToBinary(int val) {
		return String.format("%015d", Long.parseLong(Long.toBinaryString(val)));
	}

}
