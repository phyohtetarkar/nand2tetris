package com.nand2tetris.vm.code;

import com.nand2tetris.vm.Parser.VMCommand;

public interface VirtualMachineCode {

	void next(VirtualMachineCode vmCode);

	String generate(VMCommand type, Object...args);

	public static VirtualMachineCode create() {
		VirtualMachineCode code = new ArithmeticCode();
		
		code.next(new HPushCode());
		code.next(new HPopCode());
		
		return code;
	}
	
}
