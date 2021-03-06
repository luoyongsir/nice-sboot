package com.nice.sboot.demo.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

/**
 * 分页请求参数
 * @author luoyong
 * @date 2019/6/27 10:20
 */
@Schema(name = "PageParamBO", description = "分页请求参数")
public class PageParamBO implements Serializable {

	private static final long serialVersionUID = 5934926768926264314L;

	/**
	 * 页码数 从1开始
	 */
	@Schema(name = "pageNum", description = "页码数，从1开始，默认值：1", required = true, type = "int")
	private Integer pageNum;

	/**
	 * 每页数量
	 */
	@Schema(name = "pageSize", description = "每页数量，默认值：10", defaultValue = "10", required = true, type = "int")
	private Integer pageSize;

	public PageParamBO() {
		super();
	}

	public PageParamBO(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum == null ? 1 : pageNum;
		this.pageSize = pageSize == null ? 10 : pageSize;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PageParamBO{");
		sb.append("pageNum=").append(pageNum);
		sb.append(", pageSize=").append(pageSize);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PageParamBO bo = (PageParamBO) o;
		return Objects.equals(pageNum, bo.pageNum) && Objects.equals(pageSize, bo.pageSize);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pageNum, pageSize);
	}

	public static final class Builder {
		private Integer pageNum;
		private Integer pageSize;

		private Builder() {
		}

		public Builder pageNum(Integer pageNum) {
			this.pageNum = pageNum;
			return this;
		}

		public Builder pageSize(Integer pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public PageParamBO build() {
			return new PageParamBO(this.pageNum, this.pageSize);
		}
	}
}
