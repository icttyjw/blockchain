package edu.ictt.blockchain.Block.record.recordInfo;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class DegreeInfo {

    //学位
    private String degree;

    //开题情况
    private String gradDesignStartState;

    //中期情况
    private String gradDesignMidState;

    //最终论文答辩情况
    private String gradDesignFinState;

    //学位证书编号
    private long degreeId;

    //学校签名
    private String schoolSign;

    public DegreeInfo(){}

    public DegreeInfo(String degree, String gradDesignStartState, String gradDesignMidState,
                      String gradDesignFinState, long degreeId){
        this.degree = degree;
        this.degreeId = degreeId;
        this.gradDesignStartState = gradDesignStartState;
        this.gradDesignMidState = gradDesignMidState;
        this.gradDesignFinState = gradDesignFinState;
    }







}
