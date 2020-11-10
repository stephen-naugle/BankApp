package com.service;

import java.util.List;

import com.dao.DAO;
import com.dao.customerDAO;
import com.pojo.customer;

public class customerService {
	
	static DAO<customer, Integer> aDao = new customerDAO();
	static customerDAO cus = new customerDAO();
	
	public customer save(customer obj)
	{
		return aDao.save(obj);
	}
	
	public List<customer> getAll() 
	{
		aDao.toString();          // added for testing
		return aDao.getAll();
		
	}
	
	public customer findOne(String obj)
	{
		return cus.findOne(obj);
		
	}

}
