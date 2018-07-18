package com.nand2tetris.vm;

public interface VMCode {

	VMCode next(VMCode vmCode);

	String generate(Object... args);

	static VMCode create() {
		return new HPopCode()
				.next(new HAddCode())
				.next(new HSubCode())
				.next(new HEqualCode())
				.next(new HGreaterThanCode())
				.next(new HLessThanCode())
				.next(new HNegCode())
				.next(new HNotCode())
				.next(new HAndCode())
				.next(new HOrCode());
	}

}
