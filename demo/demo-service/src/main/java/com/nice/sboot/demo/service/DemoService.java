package com.nice.sboot.demo.service;

import com.github.pagehelper.PageInfo;
import com.mongodb.client.result.UpdateResult;
import com.nice.sboot.base.utils.concurrent.ThreadUtil;
import com.nice.sboot.demo.comm.MoneyReadConverter;
import com.nice.sboot.demo.entity.Coffee;
import com.nice.sboot.demo.mapper.CoffeeMapper;
import com.nice.sboot.demo.pojo.bo.CoffeeBO;
import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.result.Result;
import com.nice.sboot.result.ResultUtil;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试
 * @author luoyong
 * @date 2019/6/2 17:22
 */
@Service
public class DemoService {

	private static final Logger LOG = LoggerFactory.getLogger(DemoService.class.getName());

	@Autowired
	private CoffeeMapper coffeeMapper;
	@Autowired
	private MongoTemplate mongoTemplate;

	public Result findAllWithParam(PageParamBO param) {
		List<Coffee> list = coffeeMapper.findAllWithParam(param.getPageNum(), param.getPageSize());
		list.forEach(c -> LOG.info("Page(1) Coffee {}", c));

		list = coffeeMapper.findAllWithParam(param.getPageNum(), param.getPageSize());
		PageInfo page = new PageInfo(list);
		LOG.info("PageInfo: {}", page);
		return ResultUtil.ok(page);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
	}

	public void testMongo() {
		CoffeeBO espresso = CoffeeBO.newBuilder().name("espresso").price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.createTime(new Date()).updateTime(new Date()).build();
		CoffeeBO saved = mongoTemplate.save(espresso);
		LOG.info("CoffeeBO {}", saved);

		List<CoffeeBO> list = mongoTemplate.find(Query.query(Criteria.where("name").is("espresso")), CoffeeBO.class);
		LOG.info("Find {} CoffeeBO", list.size());
		list.forEach(c -> LOG.info("CoffeeBO {}", c));

		// 为了看更新时间
		ThreadUtil.sleep(1000L);

		UpdateResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("espresso")),
				new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30)).currentDate("updateTime"),
				CoffeeBO.class);
		LOG.info("Update Result: {}", result.getModifiedCount());
		CoffeeBO updateOne = mongoTemplate.findById(saved.getId(), CoffeeBO.class);
		LOG.info("Update Result: {}", updateOne);

		mongoTemplate.remove(updateOne);
	}

}
