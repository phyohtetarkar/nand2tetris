package com.nand2tetris.vm.code;

public class LogicalCode extends AbstractArithmeticCode {
	
	private VirtualMachineCode code;
	
	private int gt, lt, eq;
	
	@Override
	public VirtualMachineCode next(VirtualMachineCode code) {
		this.code = code;
		return code;
	}
	
	@Override
	public String generate(Object... args) {
		String cmd = (String) args[0];
		
		if (cmd.matches("gt|lt|eq")) {
			StringBuilder sb = super.generateBaseCode();
			sb.append("D=M-D\n");
			sb.append("M=-1\n");
			
			String type = logicType(cmd);
			int c = count(cmd);
			
			sb.append(String.format("@%s%d\n", type, c));
			sb.append(jump(cmd));
			sb.append("@SP\n");
			sb.append("A=M-1\n");
			sb.append("M=0\n");
			sb.append(String.format("(%s%d)\n", type, c));
			return sb.toString();
		}
		
		return code.generate(args);
	}
	
	private String jump(String cmd) {
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
	
	private String logicType(String cmd) {
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
	
	private int count(String cmd) {
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
