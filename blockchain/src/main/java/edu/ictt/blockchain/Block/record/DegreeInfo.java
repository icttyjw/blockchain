package edu.ictt.blockchain.Block.record;

public class DegreeInfo {
    
	/**
     * 学生信息
     */
    private StudentInfo studentInfo;
    
    /**
     * 开题情况
     */
    private String gradDesignStartState;

    /**
     * 中期情况
     */
    private String gradDesignMidState;

    /**
     * 最终论文答辩情况
     */
    private String gradDesignFinState;

    /**
     * 学位证书编号
     */
    private long degreeId;

    public DegreeInfo() {}
    
	public DegreeInfo(String gradDesignStartState, String gradDesignMidState, String gradDesignFinState,
			long degreeId) {
		super();
		this.gradDesignStartState = gradDesignStartState;
		this.gradDesignMidState = gradDesignMidState;
		this.gradDesignFinState = gradDesignFinState;
		this.degreeId = degreeId;
	}

	public String getGradDesignStartState() {
		return gradDesignStartState;
	}

	public void setGradDesignStartState(String gradDesignStartState) {
		this.gradDesignStartState = gradDesignStartState;
	}

	public String getGradDesignMidState() {
		return gradDesignMidState;
	}

	public void setGradDesignMidState(String gradDesignMidState) {
		this.gradDesignMidState = gradDesignMidState;
	}

	public String getGradDesignFinState() {
		return gradDesignFinState;
	}

	public void setGradDesignFinState(String gradDesignFinState) {
		this.gradDesignFinState = gradDesignFinState;
	}

	public long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(long degreeId) {
		this.degreeId = degreeId;
	}

	@Override
	public String toString() {
		return "DegreeInfo [gradDesignStartState=" + gradDesignStartState + ", gradDesignMidState=" + gradDesignMidState
				+ ", gradDesignFinState=" + gradDesignFinState + ", degreeId=" + degreeId + "]";
	}

}
