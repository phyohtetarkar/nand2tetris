package com.nand2tetris.vm;

public class HOrCode implements VMCode {
	
	private VMCode vmCode;

	@Override
	public VMCode next(VMCode vmCode) {
		this.vmCode = vmCode;
		return this;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("sub")) {
			
		}
		
		return vmCode.generate(args);
	}

}
