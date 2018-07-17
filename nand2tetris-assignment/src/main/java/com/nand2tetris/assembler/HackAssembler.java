package com.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

	/**
	 * Symbol table based on Hack assembly syntax
	 * 
	 */
	private final Map<String, String> symbolTable = new HashMap<>();

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

	public void process(File file) {

		checkLabel(file);
		
		File outFile = new File(file.getParent(), file.getName().replaceAll("asm", "hack"));

		try (HackParser parser = new HackParser(file);
				HackCodeWriter writer = new HackCodeWriter(new InstructionTableRepo(), symbolTable, outFile)) {

			while (parser.hasNextCommand()) {
				switch (parser.parse()) {
				case A_COMMAND:
					writer.writeAInstruction(parser.addr());
					break;

				case C_COMMAND:
					writer.writeCInstruction(parser.comp(), parser.dest(), parser.jump());
					break;

				default:
					break;
				};

			}

			System.out.println("Translation successful!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

	private void checkLabel(File file) {

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String line;
			int count = 0;
			while ((line = br.readLine()) != null) {

				String cmd = line.replaceAll("\\s+", "");

				if (cmd.isEmpty() || cmd.startsWith("//")) {
					continue;
				}

				cmd = cmd.contains("//") ? cmd.split("//", 1)[0] : cmd;

				if (cmd.startsWith("(")) {
					symbolTable.put(cmd.replaceAll("[\\(\\)]", ""), intToBinary(count));
					continue;
				}

				count++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static boolean isInteger(String cmd) {
		return cmd.matches("[0-9]+");
	}
	
	static String intToBinary(int val) {
		return String.format("%015d", val > 1 ? Long.parseLong(Long.toBinaryString(val)) : val);
	}

}
