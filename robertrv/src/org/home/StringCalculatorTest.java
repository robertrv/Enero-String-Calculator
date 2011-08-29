package org.home;

import static org.junit.Assert.*;

import org.home.StringCalculator.NegativeNumber;
import org.junit.Ignore;
import org.junit.Test;

public class StringCalculatorTest {

	
	private StringCalculator calculator = new StringCalculator();
	
	@Test
	public void testEmptyString() {
		assertEquals(0, calculator.Add(""));
		assertEquals(0, calculator.Add("  "));
	}
	
	@Test(expected=NullPointerException.class)
	public void testNull() {
		calculator.Add(null);
	}
	
	@Test
	public void oneNumber() {
		assertEquals(1,calculator.Add("1"));
	}
	
	@Test
	public void twoNumbers() {
		assertEquals(3, calculator.Add("1,2"));
		assertEquals(6, calculator.Add("1,2,3"));
	}
	
	@Test(expected=NumberFormatException.class)
	public void incorrectNumbers() {
		assertEquals(56, calculator.Add("1,,d,55"));
	}
	
	@Test
	public void emptyNumbers() {
		assertEquals(56, calculator.Add("1,,,55"));
	}
	
	@Test
	public void acceptCarryReturn() {
		assertEquals(5, calculator.Add("2\n2,1"));
	}

	@Test
	public void acceptCustomDelimiters() {
		assertEquals(3, calculator.Add("//;\n1;2"));
		assertEquals(9, calculator.Add("//.\n1.2.3.3"));
		assertEquals(0, calculator.Add("//;\n"));
		assertEquals(6, calculator.Add("//*\n1*2*3"));
	}
	
	@Test(expected=NegativeNumber.class)
	public void oneNegative() {
		calculator.Add("1,2,-2,2");
	}

	@Test
	public void multipleNegative() {
		try {
			calculator.Add("1,2,-2,2,-55, 56");
		} catch (NegativeNumber ex) {
			String exMessage = ex.getMessage();
			assertTrue(exMessage.contains("-2"));
			assertTrue(exMessage.contains("-55"));
			assertFalse(exMessage.contains("56"));
		}
	}
	
	@Test
	public void acceptCustomDelimitersMultipleChars() {
		assertEquals(6, calculator.Add("//[***]\n1***2***3"));		
		assertEquals(6, calculator.Add("//[*aa]\n1*aa2*aa3"));		
	}

	@Ignore
	@Test(expected=NumberFormatException.class)
	public void acceptIncorrectMultiCharDelimiter() {
		assertEquals(6, calculator.Add("//[***]\n1*2*3"));		
	}

	@Ignore
	@Test
	public void acceptMultipleDelimiter() {
		assertEquals(61, calculator.Add("//[%][;][^]\n1%2;3^55"));		
	}

}
