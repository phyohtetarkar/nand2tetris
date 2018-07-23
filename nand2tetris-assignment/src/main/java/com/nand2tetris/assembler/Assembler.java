package com.nand2tetris.assembler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
public class Assembler {

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

		Assembler assembler = new Assembler();
		assembler.process(file);
	}
	
	public Assembler() {
	}

	public void process(File file) {

		try (FileInputStream in = new FileInputStream(file)) {

			File outFile = new File(file.getParent(), file.getName().replaceAll("asm", "hack"));

			final List<String> machineCodes = new ArrayList<>();

			Parser parser = new Parser();
			parser.setInputStream(in);

			Map<String, Integer> symbolTable = generateSymbolTable(parser);
			CodeGenerator gen = new CodeGenerator(new InstructionTableRepo(), symbolTable);
			
			in.getChannel().position(0);
			parser.setInputStream(in);

			while (parser.hasNextCommand()) {
				switch (parser.parse()) {
				case A_COMMAND:
					machineCodes.add(gen.generateAInstruction(parser.symbol()));
					break;

				case C_COMMAND:
					machineCodes.add(gen.generateCInstruction(parser.comp(), parser.dest(), parser.jump()));
					break;

				default:
					break;
				}
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
	
	private Map<String, Integer> generateSymbolTable(Parser parser) throws IOException {
		Map<String, Integer> symbolTable = new HashMap<>();
		
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
		
		int index = 0;
		while (parser.hasNextCommand()) {
			switch (parser.parse()) {
			case L_COMMAND:
				symbolTable.put(parser.symbol(), index);
				break;

			case COMMENT_OR_EMPTY:
				break;

			default:
				index += 1;
				break;
			}
		}
		
		return symbolTable;
	}

}
