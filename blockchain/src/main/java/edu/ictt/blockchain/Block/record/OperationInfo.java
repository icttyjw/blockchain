package edu.ictt.blockchain.Block.record;

public class OperationInfo {

	/**
	 * 操作人员：这个是否用公钥表示会更好
	 */
	private OperationInfo operator;
	/**
	 * 操作次数
	 */
	private int count;
	/**
	 * 操作是否成功
	 */
	private boolean result;
	/**
	 * 操作涉及到的改动
	 */
	private String changedInfo;
	
	public OperationInfo(){
		
	}
	
	public OperationInfo(OperationInfo operator, int count, boolean result, String changedInfo) {
		super();
		this.operator = operator;
		this.count = count;
		this.result = result;
		this.changedInfo = changedInfo;
	}

	public OperationInfo getOperator() {
		return operator;
	}
	public int getCount() {
		return count;
	}
	public boolean isResult() {
		return result;
	}
	public void setOperator(OperationInfo operator) {
		this.operator = operator;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setResult(boolean result) {
		this.result = result;
	}

	public String getChangedInfo() {
		return changedInfo;
	}

	public void setChangedInfo(String changedInfo) {
		this.changedInfo = changedInfo;
	}

	@Override
	public String toString() {
		return "OperationInfo [operator=" + operator + ", count=" + count + ", result=" + result + ", changedInfo="
				+ changedInfo + "]";
	}
}
