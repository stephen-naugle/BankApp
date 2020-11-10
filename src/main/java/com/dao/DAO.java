package com.dao;

import java.io.Serializable;
import java.util.List;

import com.pojo.account;
import com.pojo.customer;

public interface DAO <T, I extends Serializable>{
	

	List<T> getAll();
	T findOne(I id);
	T save(T obj);
	T update(T obj);
	void delete(T obj);
	default boolean isUnique(T obj)
	{
		return true;
	}
//	String getAll(int x, String s);

}