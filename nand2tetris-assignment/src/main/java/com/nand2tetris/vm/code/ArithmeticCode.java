package com.nand2tetris.vm.code;

public class ArithmeticCode extends AbstractArithmeticCode {
	
	private VirtualMachineCode code;
	
	@Override
	public VirtualMachineCode next(VirtualMachineCode code) {
		this.code = code;
		return code;
	}
	
	@Override
	public String generate(Object... args) {
		String cmd = (String) args[0];
		
		if (cmd.matches("add|sub|and|or|not|neg")) {
			StringBuilder sb;
			
			if (cmd.matches("neg|not")) {
				sb = new StringBuilder();
				sb.append("@SP\n");
				sb.append("A=M-1\n");
			} else {
				sb = super.generateBaseCode();
			}
			
			sb.append(operation(cmd));
			return sb.toString();
		}
		
		return code.generate(args);
	}
	
	private String operation(String cmd) {
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
	
}
