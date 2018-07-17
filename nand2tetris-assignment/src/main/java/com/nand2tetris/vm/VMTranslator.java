package com.nand2tetris.vm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

		if (!file.getName().endsWith(".asm")) {
			System.out.println("Not a hack assembly file.");
			return;
		}

		VMTranslator trans = new VMTranslator();
	}

	public void process(File file) {
		
		File outFile = new File(file.getParent(), file.getName().replaceAll("vm", "asm"));

		try (FileInputStream in = new FileInputStream(file);
				FileOutputStream out = new FileOutputStream(outFile)) {

			VMParser parser = new VMParser(in);
			CodeWriter writer = new CodeWriter(out);

			while (parser.hasNextCommand()) {
				switch (parser.parse()) {
				case C_ARITHMETIC:
					writer.writeArithmetic(parser.getArg1());
					break;

				case C_PUSH:
					
					break;

				case C_POP:
					
					break;
				}
			}

			

			if (outFile.exists()) {
				outFile.delete();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
