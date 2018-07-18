package com.nand2tetris.vm.code;

import com.nand2tetris.vm.VMCode;

/**
 * Abstract arithmetic operation for [add|sub|and|or]
 * 
 * @author Phyo Htet Arkar
 */
abstract class AbstractArithmeticCode implements VMCode {
	
	protected StringBuilder generateBaseCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("M=M-1\n");
		sb.append("A=M\n");
		sb.append("D=M\n");
		sb.append("@SP\n");
		sb.append("A=M-1\n");

		return sb;
	}
	
}
