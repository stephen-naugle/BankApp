package com.dao;

import org.apache.log4j.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.pojo.account;
import com.util.PlainTextConnectionUtil;



public class accountDao implements DAO<account, Integer> {
	
	final static Logger logger = Logger.getLogger(accountDao.class);

	@Override
	public List<account> getAll() {

		List<account> acc = new ArrayList<account>();
		
		String sql = "{call public.get_all_accounts(?)}";
//		String sql = "{call public.get_all_accounts(?)}";
//		String sql = "select balance from accounts where user_id = ?";

		try (Connection conn = PlainTextConnectionUtil.getInstance().getConnection())

		{
			
//			String query = "select * from accounts";              //revision
//			Statement statement = conn.createStatement();        // revision
//			ResultSet rs = statement.executeQuery(query);		//  revision
			CallableStatement cs = conn.prepareCall(sql);

/**	testing
			cs.setInt(2, x);
			cs.setInt(3, x);
			cs.setString(4, s);
*/			
			
			cs.execute();
			ResultSet rs = (ResultSet)cs.getObject(1);    	//    revision        
			logger.error("Attempting get_all_accounts from SQL.");
			
			while(rs.next())
			{
				account temp = new account();
				temp.setAccount_Name(rs.getString("account_name"));
				temp.setAccount_type(rs.getString("account_type"));
				temp.setBalance(rs.getDouble("balance"));
				temp.setAccount_Id(rs.getInt("account_id"));
				temp.setCustomer_id(rs.getInt("user_id"));
				acc.add(temp);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error while using the getAll() method. Message: " + e.getMessage());
		}
		
//		return result;
		return acc;		
	}
	
	/////commenting above for testing

	@Override
	public account findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public account save(account obj) 
	{		
		try(Connection conn = PlainTextConnectionUtil.getInstance().getConnection())
		{
			conn.setAutoCommit(false);
			
			String sql = "insert into accounts(account_name, account_type, balance, user_id) values(?, ?, ?, ?)";
			
			String[] keys = {"account_id"};
			
			PreparedStatement ps = conn.prepareStatement(sql, keys);
			ps.setString(1, obj.getAccount_Name());
			ps.setString(2, obj.getAccount_type());
			ps.setDouble(3, obj.getBalance());
			ps.setInt(4, obj.getCustomer_id());
			
			int numsRowsAffected = ps.executeUpdate();
			
			if(numsRowsAffected > 0)
			{
				ResultSet pk = ps.getGeneratedKeys();
				
				while(pk.next()) 
				{
					obj.setAccount_Id(pk.getInt(1));
				}
			}
			
			conn.commit();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}

	@Override
	public account update(account obj) 
	{
		try (Connection conn = PlainTextConnectionUtil.getInstance().getConnection())
		{
			conn.setAutoCommit(false);
			String sql = "update accounts set balance = ? where account_id = ?";
			
			String[] keys = {"account_id"};
			
			PreparedStatement ps = conn.prepareStatement(sql, keys);
			ps.setDouble(1, obj.getBalance());
			ps.setInt(2, obj.getAccount_Id());
			ps.executeUpdate();               // Ok - storing the balances correctly
//			ps.executeQuery();				 // only queries - not storing balances
			conn.commit();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(account obj)
	{
		try (Connection conn = PlainTextConnectionUtil.getInstance().getConnection())
		{
			conn.setAutoCommit(false);
			String sql = "delete from accounts where account_id = ?";
			
			String[] keys = {"account_id"};
			
			PreparedStatement ps = conn.prepareStatement(sql, keys);
			ps.setDouble(1, obj.getAccount_Id());
			ps.executeQuery();
			conn.commit();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
	}
	
	public List<account> getAl() 
	{
		List<account> genres = new ArrayList<account>();
		try (Connection conn = PlainTextConnectionUtil.getInstance().getConnection())

		{
			String query = "select * from accounts order by account_name asc";
			
		
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				account temp = new account();
				temp.setAccount_Id(rs.getInt(1));
				temp.setAccount_Name(rs.getString(2));
				genres.add(temp);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return genres;

	}

}
