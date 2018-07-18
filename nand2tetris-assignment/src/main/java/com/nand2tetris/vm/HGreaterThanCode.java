package com.nand2tetris.vm;

public class HGreaterThanCode implements VMCode {
	
	private VMCode vmCode;
	private int gt;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return this;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("gt")) {
			
		}
		
		return vmCode.generate(args);
	}

}
