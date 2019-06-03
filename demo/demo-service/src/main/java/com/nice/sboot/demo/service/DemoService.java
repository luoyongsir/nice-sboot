package com.nice.sboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 测试
 * @author luoyong
 * @date 2019/6/2 17:22
 */
@Service
public class DemoService {

	@Autowired
	private DataSource dataSource;

	public void showConnection (){
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(connection.toString());
	}

}
