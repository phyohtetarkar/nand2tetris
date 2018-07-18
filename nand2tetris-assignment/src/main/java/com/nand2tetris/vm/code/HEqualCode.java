package com.nand2tetris.vm.code;

import com.nand2tetris.vm.VMCode;

public class HEqualCode extends AbstractArithmeticCode {
	
	private VMCode vmCode;
	private int eq;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return vmCode;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("eq")) {
			StringBuilder sb = generateBaseCode();
			
			sb.append("D=M-D\n");
			sb.append("M=-1\n");
			sb.append(String.format("@EQUAL%d\n", ++eq));
			sb.append("D;JEQ\n");
			sb.append("@SP\n");
			sb.append("A=M-1\n");
			sb.append("M=0\n");
			sb.append(String.format("(EQUAL%d)\n", eq));
			
			return sb.toString();
		}
		
		return vmCode.generate(args);
	}

}
