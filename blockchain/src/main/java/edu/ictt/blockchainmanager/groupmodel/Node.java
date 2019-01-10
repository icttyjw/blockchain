package edu.ictt.blockchainmanager.groupmodel;

import java.util.Date;


public class Node{
	/**
	 * 各级节点类的基类
	 */
	
	/**
	 * 节点ID
	 */
	private Long id;

	/**
	 * 节点创建时间
	 */
    private Date createTime;

    /**
     * 节点更新时间
     */
    private Date updateTime;

    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
