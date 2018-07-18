package com.nand2tetris.vm.code;

import com.nand2tetris.vm.VMCode;

public class HSubCode extends AbstractArithmeticCode {
	
	private VMCode vmCode;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return vmCode;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("sub")) {
			StringBuilder sb = generateBaseCode();
			sb.append("M=M-D\n");
			
			return sb.toString();
		}
		
		return vmCode.generate(args);
	}

}
