package com.nand2tetris.vm.code;

import com.nand2tetris.vm.Parser.VMCommand;

public interface VirtualMachineCode {

	VirtualMachineCode next(VirtualMachineCode code);

	String generate(VMCommand type, Object...args);

	public static VirtualMachineCode create() {
		VirtualMachineCode code = new ArithmeticCode();
		
		code.next(new HPushCode())
			.next(new HPopCode());
		
		return code;
	}
	
}
