package com.nand2tetris.vm;

public class HAddCode implements VMCode {
	
	private VMCode vmCode;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return this;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("add")) {
			
		}
		
		return vmCode.generate(args);
	}

}
