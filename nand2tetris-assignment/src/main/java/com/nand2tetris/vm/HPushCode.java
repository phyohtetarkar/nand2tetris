package com.nand2tetris.vm;

public class HPushCode implements VMCode {
	
	private VMCode vmCode;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return this;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		String seg = (String) args[1];
		int index = (int) args[2];
		
		if (cmd.equals("push")) {
			
		}
		
		return vmCode.generate(args);
	}

}
