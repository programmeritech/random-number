package com.tushar.spring.boot.rest.randomNumberGenerator;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private BasicDataSource basicDataSource;

	@RequestMapping("/generate/randomnumber")
	public RandonNumber greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new RandonNumber(counter.incrementAndGet(), new Random().nextInt());
	}

	@RequestMapping("/student")
	public Student getStudent(@RequestParam(value = "id", defaultValue = "NaN") String id) throws SQLException {
		System.out.println(basicDataSource.getDefaultAutoCommit());
		Statement stmt = basicDataSource.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT \"ID\", \"NAME\" FROM \"Student\"");
		Student student = new Student();
		while (rs.next()) {
			student.setId(rs.getLong("ID"));
			student.setName(rs.getString("NAME"));

		}
		return student;
	}

	@Bean
	public BasicDataSource dataSource() throws URISyntaxException {

		 URI dbUri = new URI(System.getenv("DATABASE_URL"));
		//URI dbUri = new URI("postgres://postgres:06109@localhost:5432/databasename");

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath() + ":" + dbUri.getPort()
				+ dbUri.getPath();

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		return basicDataSource;
	}
}
