package com.nand2tetris.vm;

public class HEqualCode implements VMCode {
	
	private VMCode vmCode;
	private int eq;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return this;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("eq")) {
			
		}
		
		return vmCode.generate(args);
	}

}
