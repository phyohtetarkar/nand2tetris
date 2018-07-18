package com.nand2tetris.vm.code;

import com.nand2tetris.vm.VMCode;

public class HGreaterThanCode extends AbstractArithmeticCode {
	
	private VMCode vmCode;
	private int gt;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return vmCode;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("gt")) {
			StringBuilder sb = generateBaseCode();
			
			sb.append("D=M-D\n");
			sb.append("M=-1\n");
			sb.append(String.format("@GREATER%d\n", ++gt));
			sb.append("D;JGT\n");
			sb.append("@SP\n");
			sb.append("A=M-1\n");
			sb.append("M=0\n");
			sb.append(String.format("(GREATER%d)\n", gt));
			
			return sb.toString();
		}
		
		return vmCode.generate(args);
	}

}
