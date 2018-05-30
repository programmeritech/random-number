package com.tushar.spring.boot.rest.randomNumberGenerator;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private final AtomicLong counter = new AtomicLong();


	@RequestMapping("/generate/randomnumber")
	public RandonNumber greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new RandonNumber(counter.incrementAndGet(), new Random().nextInt());
	}

	@RequestMapping("/student")
	public Student getStudent(@RequestParam(value = "id", defaultValue = "NaN") String id) throws SQLException, URISyntaxException {
		Statement stmt = dataSource().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT \"ID\", \"NAME\" FROM \"Student\"");
		Student student = new Student();
		while (rs.next()) {
			student.setId(rs.getLong("ID"));
			student.setName(rs.getString("NAME"));

		}
		return student;
	}
	// You can see the DATABASE_URL provided to an application by running:

	// heroku config
	@Bean
	public Connection dataSource() throws URISyntaxException, SQLException {

		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		 //URI dbUri = new URI("postgres://postgres:06109@localhost:5432/databasename");

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() +  ":" + dbUri.getPort() + dbUri.getPath();

		return DriverManager.getConnection(dbUrl, username, password);

	}
}
