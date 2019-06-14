package com.nice.sboot.demo.pojo.bo;

import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luoyong
 * @date 2019/6/14 10:49
 */
@Document
public class CoffeeBO implements Serializable {
	private static final long serialVersionUID = -6137565461333417220L;

	@Id
	private String id;

	/** 咖啡名称 */
	private String name;

	/** 咖啡价格 */
	private Money price;

	private Date createTime;

	private Date updateTime;

	public CoffeeBO() {
	}

	private CoffeeBO(Builder builder) {
		setId(builder.id);
		setName(builder.name);
		setPrice(builder.price);
		setCreateTime(builder.createTime);
		setUpdateTime(builder.updateTime);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", name=").append(name);
		sb.append(", price=").append(price);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append("]");
		return sb.toString();
	}

	public static final class Builder {
		private String id;
		private String name;
		private Money price;
		private Date createTime;
		private Date updateTime;

		private Builder() {
		}

		public Builder id(String val) {
			id = val;
			return this;
		}

		public Builder name(String val) {
			name = val;
			return this;
		}

		public Builder price(Money val) {
			price = val;
			return this;
		}

		public Builder createTime(Date val) {
			createTime = val;
			return this;
		}

		public Builder updateTime(Date val) {
			updateTime = val;
			return this;
		}

		public CoffeeBO build() {
			return new CoffeeBO(this);
		}
	}
}
