package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;



import java.util.Optional;
import java.time.LocalDateTime;

@SpringBootApplication
public class TestApplication {


    public static void main(String[] args) {
	String jdbcURL = "";

	try (Connection conn = DriverManager.getConnection(jdbcURL, "postgres", "password")) {
	    if(!conn.isValid(0)) {
		System.out.println("Unable to connect to database");
		System.exit(0);
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

	PreparedStatement preparedStatement = conn.prepareStatement("select * from run");
	ResultSet rs = preparedStatement.executeQuery();

	while(rs.next()) {
	    String title = rs.getString("title");
	    int miles = rs.getInt("miles");
	    System.out.println(title + ": " + miles);
	}
	
	SpringApplication.run(TestApplication.class, args);
    }

}


