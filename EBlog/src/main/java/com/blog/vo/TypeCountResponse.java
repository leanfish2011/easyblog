package com.blog.vo;

import java.math.BigInteger;

/**
 * @author：Tim
 * @date：2018年1月29日 下午9:45:17
 * @description：统计每种类别下文章数量
 */
public class TypeCountResponse {

	private String typeId;
	private String typeName;
	private BigInteger typeCount;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigInteger getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(BigInteger typeCount) {
		this.typeCount = typeCount;
	}

}
