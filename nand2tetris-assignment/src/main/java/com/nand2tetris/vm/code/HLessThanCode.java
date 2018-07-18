package com.nand2tetris.vm.code;

import com.nand2tetris.vm.VMCode;

public class HLessThanCode extends AbstractArithmeticCode {
	
	private VMCode vmCode;
	private int lt;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return vmCode;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("lt")) {
			StringBuilder sb = generateBaseCode();
			
			sb.append("D=M-D\n");
			sb.append("M=-1\n");
			sb.append(String.format("@LESS%d\n", ++lt));
			sb.append("D;JLT\n");
			sb.append("@SP\n");
			sb.append("A=M-1\n");
			sb.append("M=0\n");
			sb.append(String.format("(LESS%d)\n", lt));
			
			return sb.toString();
		}
		
		return vmCode.generate(args);
	}

}
