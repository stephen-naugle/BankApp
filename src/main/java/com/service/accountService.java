package com.service;

import java.util.List;

import com.dao.DAO;
import com.dao.accountDao;
import com.dao.customerDAO;
import com.pojo.account;

public class accountService {
	
	static DAO<account, Integer> aDao = new accountDao();
	private customerDAO cd;
	
	public List<account> getAll()
	{
		aDao.toString();             // added for testing
		return aDao.getAll();
	}
	
	public account save(account obj)
	{
		return aDao.save(obj);
	}
	
	public account update(account obj)
	{
		return aDao.update(obj);
	}
	
	public void delete(account obj)
	{
		this.cd = cd;
		aDao.delete(obj);
	}

}
