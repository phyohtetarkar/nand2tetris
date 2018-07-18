package com.nand2tetris.vm.code;

import com.nand2tetris.vm.VMCode;

public class HOrCode extends AbstractArithmeticCode {
	
	private VMCode vmCode;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return vmCode;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("or")) {
			StringBuilder sb = generateBaseCode();
			sb.append("M=D|M\n");
			
			return sb.toString();
		}
		
		return vmCode.generate(args);
	}

}
