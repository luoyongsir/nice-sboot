package com.nice.sboot.demo.entity;

import org.joda.money.Money;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table T_COFFEE
 * @author luoyong
 * @date 2019/06/22 17:40
 */
public class Coffee implements Serializable {
    private Long id;

    /** 咖啡名称 */
    private String name;

    /** 咖啡价格 */
    private Money price;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = -717111033660533872L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
