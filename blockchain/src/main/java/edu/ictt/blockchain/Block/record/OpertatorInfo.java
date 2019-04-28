package edu.ictt.blockchain.Block.record;

public class OpertatorInfo {
	/**
	 * 所属机构,机构暂时没有给定机构实体，不确定这样设计是否可以
	 */
	private String operatorOrga;
	
	/**
	 * 操作人员工号id
	 */
	private int operatorId;
	
	/**
	 * 操作人员姓名
	 */
	private String operatorName;
	
	/**
	 * 操作人员职位
	 */
	
	private String operatorPost;
	
	/**
	 * 操作人员公钥
	 */
	private String operatorPubKey;

	public OpertatorInfo() {}

	public OpertatorInfo(String operatorOrga, int operatorId, String operatorName, String operatorPost,
			String operatorPubKey) {
		super();
		this.operatorOrga = operatorOrga;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.operatorPost = operatorPost;
		this.operatorPubKey = operatorPubKey;
	}



	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorPost() {
		return operatorPost;
	}

	public void setOperatorPost(String operatorPost) {
		this.operatorPost = operatorPost;
	}

	public String getOperatorPubKey() {
		return operatorPubKey;
	}

	public void setOperatorPubKey(String operatorPubKey) {
		this.operatorPubKey = operatorPubKey;
	}

	
	public String getOperatorOrga() {
		return operatorOrga;
	}

	public void setOperatorOrga(String operatorOrga) {
		this.operatorOrga = operatorOrga;
	}

	@Override
	public String toString() {
		return "OpertatorInfo [operatorOrga=" + operatorOrga + ", operatorId=" + operatorId + ", operatorName="
				+ operatorName + ", operatorPost=" + operatorPost + ", operatorPubKey=" + operatorPubKey + "]";
	}
}
