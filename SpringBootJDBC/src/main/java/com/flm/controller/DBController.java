package com.flm.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flm.SpringBootJdbcApplication;
import com.flm.model.Employee;

@RestController
public class DBController {

    private final SpringBootJdbcApplication springBootJdbcApplication;

	@Autowired
	JdbcTemplate jdbcTemplate;


    DBController(SpringBootJdbcApplication springBootJdbcApplication) {
        this.springBootJdbcApplication = springBootJdbcApplication;
    }
	
	
	@GetMapping("/save")
	public String save() {
		
		jdbcTemplate.update("insert into employee values(?,?,?)" ,16,"email@gmail.com",400000);
		return  "saved";
		
	}
	
	@GetMapping("/get")
   public void Employee() {
	   
		Employee employee = jdbcTemplate.queryForObject("select * from employee where empid =1",
				(rs,n)-> new Employee (rs.getInt(1), rs.getString(2), rs.getDouble(3)));
		
		System.out.println(employee);
   }

}
