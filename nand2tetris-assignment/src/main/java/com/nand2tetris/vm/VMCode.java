package com.nand2tetris.vm;

import com.nand2tetris.vm.code.HAddCode;
import com.nand2tetris.vm.code.HAndCode;
import com.nand2tetris.vm.code.HEqualCode;
import com.nand2tetris.vm.code.HGreaterThanCode;
import com.nand2tetris.vm.code.HLessThanCode;
import com.nand2tetris.vm.code.HNegCode;
import com.nand2tetris.vm.code.HNotCode;
import com.nand2tetris.vm.code.HOrCode;
import com.nand2tetris.vm.code.HPopCode;
import com.nand2tetris.vm.code.HPushCode;
import com.nand2tetris.vm.code.HSubCode;

public interface VMCode {

	VMCode next(VMCode vmCode);

	String generate(Object... args);

	static VMCode create() {
		VMCode code = new HPushCode();
		
		code.next(new HPopCode())
			.next(new HAddCode())
			.next(new HSubCode())
			.next(new HEqualCode())
			.next(new HGreaterThanCode())
			.next(new HLessThanCode())
			.next(new HNegCode())
			.next(new HNotCode())
			.next(new HAndCode())
			.next(new HOrCode());
		
		return code;
				
	}

}
