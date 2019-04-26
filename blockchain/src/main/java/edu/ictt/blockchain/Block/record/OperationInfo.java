package edu.ictt.blockchain.Block.record;

public class OperationInfo {

	private String operator;
	private int count;
	private boolean result;
	
	public OperationInfo(){
		
	}
	
	
	public String getOperator() {
		return operator;
	}
	public int getCount() {
		return count;
	}
	public boolean isResult() {
		return result;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
}
