package hr.fer.zemris.java.hw11;

import org.junit.Assert;

public class Test {

	public String bestFootballerOnEarth = "Iniesta";
	
	@org.junit.Test
	public void noStress () {
		Assert.assertEquals("Iniesta", bestFootballerOnEarth);
	}
	
	
}
