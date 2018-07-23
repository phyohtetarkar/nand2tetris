package com.nand2tetris.vm.code;

public interface VirtualMachineCode {

	VirtualMachineCode next(VirtualMachineCode code);

	String generate(Object...args);

	public static VirtualMachineCode create() {
		VirtualMachineCode code = new ArithmeticCode();
		
		code.next(new LogicalCode())
			.next(new HPushCode())
			.next(new HPopCode());
		
		return code;
	}
	
}
