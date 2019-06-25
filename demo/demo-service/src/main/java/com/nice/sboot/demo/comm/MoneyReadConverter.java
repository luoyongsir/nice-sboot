package com.nice.sboot.demo.comm;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * Money 对象转换
 * @author luoyong
 * @date 2019/6/13 11:22
 */
public class MoneyReadConverter implements Converter<Document, Money> {

	@Override
	public Money convert(Document source) {
		Document money = (Document) source.get("money");
		double amount = Double.parseDouble(money.getString("amount"));
		String currency = ((Document) money.get("currency")).getString("code");
		return Money.of(CurrencyUnit.of(currency), amount);
	}
}
