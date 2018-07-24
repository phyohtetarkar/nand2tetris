package com.nand2tetris.vm.code;

/**
 * Abstract arithmetic operation for [add|sub|and|or|gt|lt|eq]
 * 
 * @author Phyo Htet Arkar
 */
abstract class AbstractArithmeticCode implements VirtualMachineCode {
	
	protected int gt, lt, eq;
	
	protected StringBuilder generateBaseCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("M=M-1\n");
		sb.append("A=M\n");
		sb.append("D=M\n");
		sb.append("@SP\n");
		sb.append("A=M-1\n");

		return sb;
	}
	
	protected String operation(String cmd) {
		switch (cmd) {
		case "add":
			return "M=D+M\n";
		case "sub":
			return "M=M-D\n";
		case "and":
			return "M=D&M\n";
		case "or":
			return "M=D|M\n";
		case "neg":
			return "M=-M\n";
		case "not":
			return "M=!M\n";
		}
		
		return "";
	}
	
	protected String jump(String cmd) {
		switch (cmd) {
		case "gt":
			return "D;JGT\n";
		case "lt":
			return "D;JLT\n";
		case "eq":
			return "D;JEQ\n";
		}
		
		return "";
	}
	
	protected String logicType(String cmd) {
		switch (cmd) {
		case "gt":
			++gt;
			return "GT";
		case "lt":
			++lt;
			return "LT";
		case "eq":
			++eq;
			return "EQ";
		}
		
		return "";
	}
	
	protected int count(String cmd) {
		switch (cmd) {
		case "gt":
			return gt;
		case "lt":
			return lt;
		case "eq":
			return eq;
		}
		
		return 0;
	}
	
}
