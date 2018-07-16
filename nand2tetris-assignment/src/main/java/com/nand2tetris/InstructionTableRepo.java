package com.nand2tetris;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Instruction table repository based on Hack CPU standard
 * 
 * @author Phyo Htet Arkar
 *
 */
public class InstructionTableRepo {

	private Map<String, String> compMap = new HashMap<>();
	private Map<String, String> destMap = new HashMap<>();
	private Map<String, String> jumpMap = new HashMap<>();

	public InstructionTableRepo() throws IOException {

		Properties prop = new Properties();

		// load computation
		prop.load(getClass().getClassLoader().getResourceAsStream("computation.properties"));
		prop.entrySet().forEach(en -> compMap.put((String) en.getKey(), (String) en.getValue()));
		
		prop.clear();

		// load destination
		prop.load(getClass().getClassLoader().getResourceAsStream("destination.properties"));
		prop.entrySet().forEach(en -> destMap.put((String) en.getKey(), (String) en.getValue()));

		prop.clear();

		prop.load(getClass().getClassLoader().getResourceAsStream("jump.properties"));
		prop.entrySet().forEach(en -> jumpMap.put((String) en.getKey(), (String) en.getValue()));
		
	}

	public String findComp(String cmd) {
		return compMap.get(cmd);
	}

	public String findDest(String cmd) {
		return destMap.get(cmd);
	}

	public String findJump(String cmd) {
		return jumpMap.get(cmd);
	}

}
