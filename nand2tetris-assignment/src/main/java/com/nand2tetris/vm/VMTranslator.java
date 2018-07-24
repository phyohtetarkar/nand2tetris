package com.nand2tetris.vm;

import java.io.File;
import java.io.FileNotFoundException;

import com.nand2tetris.vm.Parser.VMCommand;

public class VMTranslator {

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

		if (!file.getName().endsWith(".vm")) {
			System.out.println("Not a hack vm file.");
			return;
		}

		VMTranslator translator = new VMTranslator();
		translator.process(file);
	}

	public void process(File file) {
		
		File outFile = new File(file.getParent(), file.getName().replaceAll("vm", "asm"));

		try (Parser parser = new Parser(file);
				CodeWriter writer = new CodeWriter(outFile)) {

			while (parser.hasNextCommand()) {
				VMCommand cmd = parser.parse();
				
				switch (cmd) {
				case C_ARITHMETIC:
					writer.writeArithmetic(parser.getArg1());
					break;

				case C_PUSH:
				case C_POP:
					writer.writePushPop(cmd, parser.getArg1(), parser.getArg2());
					break;
					
				default:
					break;
				}
			}

			System.out.println("Translation successful!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

}
