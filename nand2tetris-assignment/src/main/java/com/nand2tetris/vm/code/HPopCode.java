package com.nand2tetris.vm.code;

public class HPopCode implements VirtualMachineCode {
	
	private VirtualMachineCode vmCode;

	@Override
	public VirtualMachineCode next(VirtualMachineCode vmCode) {
		this.vmCode = vmCode;
		return vmCode;
	}

	@Override
	public String generate(Object...args) {
		String cmd = (String) args[0];
		
		if (cmd.equals("pop")) {
			String segment = (String) args[1];
			int index = (int) args[2];
			
			StringBuilder sb = new StringBuilder();
			
			if (segment.equals("pointer")) {
				sb.append("@SP\n");
				sb.append("M=M-1\n");
				sb.append("A=M\n");
				sb.append("D=M\n");
				sb.append(String.format("@%s\n", index == 0 ? "THIS": "THAT"));
				sb.append("M=D\n");
				
				return sb.toString();
			} 
			
			if (segment.equals("TEMP")) {
				sb.append("@SP\n");
				sb.append("M=M-1\n");
				sb.append("A=M\n");
				sb.append("D=M\n");
				sb.append(String.format("@%d\n", (index + 5)));
				sb.append("M=D\n");
				
				return sb.toString();
			} 
			
			if (index > 1) {
				sb.append("@" + index + "\n");
				sb.append("D=A\n");
			} else {
				sb.append("D=" + index + "\n");
			}
			
			sb.append("@" + segment + "\n");
			sb.append("D=D+M\n");
			
			sb.append("@R13\n");
			sb.append("M=D\n");

			sb.append("@SP\n");
			sb.append("M=M-1\n");
			sb.append("A=M\n");
			sb.append("D=M\n");
			
			sb.append("@R13\n");
			sb.append("A=M\n");
			sb.append("M=D\n");
			
			return sb.toString();
		}
		
		return vmCode.generate(args);
	}

}
