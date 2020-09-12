package entitybuilder.pybuilder.pyvisitor;

import java.util.List;

import expression.ExpressionAtom;

public class ListToArray {
	/**
	 * All of the functions in this class aim to change list to array
	 */
	public ExpressionAtom[] exp_conversion(List<ExpressionAtom> input) {
		ExpressionAtom[] list = new ExpressionAtom[input.size()];
		return input.toArray(list);
	}
	
	public String[] str_conversion(List<String> input) {
		String[] list = new String[input.size()];
		return input.toArray(list);
	}
	
	public Integer[] int_conversion(List<Integer> input) {
		Integer[] list = new Integer[input.size()];
		return input.toArray(list);
	}
	
	
	
}
