package com.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import com.app.menus;
import com.dao.customerDAO;
import com.pojo.customer;
import com.util.PlainTextConnectionUtil;

import org.junit.Test;

public class TestFind {
	
	private customerDAO cd;

	@Before
	public void findOne() {
		cd = new customerDAO();
	}
	
	@Test
	public void findByUsernameTest() {
		List<customer> customers = (List<customer>) cd.findOne("kmalone");
		assertFalse(customers.size() == 0);
	}
}
