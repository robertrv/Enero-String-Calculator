package org.home;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {
	
	private String delimitersRe = ",";
	private String numbers;
	
	
	public int Add(String numbers) throws NegativeNumber {
		if (numbers.trim().isEmpty()) {
			return 0;
		}
		this.numbers = numbers;
		
		findValidDelimiters();
		int result = 0;
		String[] numberParts = findNumbers();
		List<String> negativeNumbers = new LinkedList<String>();
		for (String numberString : numberParts) {
			try {
				result += parseNumber(numberString);
			} catch (NegativeNumber e) {
				negativeNumbers.add(numberString);
			}
		}
		
		if (negativeNumbers.size() > 0) {
			throw new NegativeNumber("negatives not allowed", negativeNumbers);
		}
		
		return result;
	}

	private int parseNumber(String numberString) throws NegativeNumber {
		if (!numberString.isEmpty()) {
			int intNumber = Integer.valueOf(numberString.trim());
			if (intNumber < 0) {
				throw new NegativeNumber("Negative numbers not allowed");
			} else if (intNumber <= 1000) {
				return intNumber;				
			}
		}
		return 0;
	}

	private String[] findNumbers() {
		return numbers.split(escapeRE(delimitersRe)+"|\\n");
	}
	
	static Pattern escaper = Pattern.compile("([^a-zA-z0-9])");
	private String escapeRE(String str) {
	    return StringCalculator.escaper.matcher(str).replaceAll("\\\\$1");
	}

	private void findValidDelimiters() {
		if (numbers.startsWith("//")) {
			int firstCarryReturn = numbers.indexOf('\n');
			delimitersRe = numbers.substring(2,firstCarryReturn);
			numbers = numbers.substring(firstCarryReturn+1);
		}
	}
	
	@SuppressWarnings("serial")
	class NegativeNumber extends RuntimeException {
		private List<String> negativeNumbers;

		public NegativeNumber(String message) {
			super(message);
		}
		
		public NegativeNumber(String message, List<String> negatives) {
			this(message);
			this.negativeNumbers = negatives;
		}
		
		@Override
		public String getMessage() {
			if (negativeNumbers == null) {
				return super.getMessage();
			} else {
				return super.getMessage() + " - " + this.negativeNumbers;
			}
		}
		
	}

}

