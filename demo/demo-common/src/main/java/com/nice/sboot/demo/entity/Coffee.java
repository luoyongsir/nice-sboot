package com.nice.sboot.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table T_COFFEE
 * @author luoyong
 * @date 2019/06/22 17:40
 */
@Getter
@Setter
@ToString
public class Coffee implements Serializable {
	private Long id;

	/** 咖啡名称 */
	private String name;

	/** 咖啡价格 */
	private Money price;

	private Date createTime;

	private Date updateTime;

	private static final long serialVersionUID = -717111033660533872L;

}
