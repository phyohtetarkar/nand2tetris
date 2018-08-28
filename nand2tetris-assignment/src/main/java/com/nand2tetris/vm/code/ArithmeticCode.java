package com.nand2tetris.vm.code;

import com.nand2tetris.vm.Parser.VMCommand;

public class ArithmeticCode extends AbstractArithmeticCode {
	
	private VirtualMachineCode vmCode;
	
	@Override
	public void next(VirtualMachineCode vmCode) {
		if (this.vmCode == null) {
			this.vmCode = vmCode;
		} else {
			this.vmCode.next(vmCode);
		}
	}
	
	@Override
	public String generate(VMCommand type, Object... args) {
		
		if (type == VMCommand.C_ARITHMETIC) {
			String cmd = (String) args[0];
			
			if (cmd.matches("eq|gt|lt")) {
				StringBuilder sb = super.generateBaseCode();
				sb.append("D=M-D\n");
				sb.append("M=-1\n");
				
				String logicType = logicType(cmd);
				int c = count(cmd);
				
				sb.append(String.format("@%s%d\n", logicType, c));
				sb.append(jump(cmd));
				sb.append("@SP\n");
				sb.append("A=M-1\n");
				sb.append("M=0\n");
				sb.append(String.format("(%s%d)\n", logicType, c));
				
				return sb.toString();
			} else {
				StringBuilder sb;
				
				if (cmd.matches("neg|not")) {
					sb = new StringBuilder();
					sb.append("@SP\n");
					sb.append("A=M-1\n");
				} else {
					sb = super.generateBaseCode();
				}
				
				sb.append(operation(cmd));
				
				return sb.toString();
			}
		}
		
		return vmCode.generate(type, args);
	}
	
}
