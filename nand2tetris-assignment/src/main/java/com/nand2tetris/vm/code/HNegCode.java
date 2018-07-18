package com.nand2tetris.vm.code;

import com.nand2tetris.vm.VMCode;

public class HNegCode implements VMCode {
	
	private VMCode vmCode;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return vmCode;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("neg")) {
			StringBuilder sb = new StringBuilder();
			sb.append("@SP\n");
			sb.append("A=M-1\n");
			sb.append("M=-M\n");
			
			return sb.toString();
		}
		
		return vmCode.generate(args);
	}

}
