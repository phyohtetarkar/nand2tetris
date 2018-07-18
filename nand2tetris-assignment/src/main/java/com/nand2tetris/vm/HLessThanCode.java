package com.nand2tetris.vm;

public class HLessThanCode implements VMCode {
	
	private VMCode vmCode;
	private int lt;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return this;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("lt")) {
			
		}
		
		return vmCode.generate(args);
	}

}
