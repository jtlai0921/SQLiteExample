package com.izero.sqllite;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;


public class MyTest {
	public Connection getConnection() throws SQLException
	{
		SQLiteConfig config = new SQLiteConfig();
		// config.setReadOnly(true);   
		config.setSharedCache(true);
		config.enableRecursiveTriggers(true);
	
			
		SQLiteDataSource ds = new SQLiteDataSource(config); 
		ds.setUrl("jdbc:sqlite:sample.db");
		return ds.getConnection();
		//ds.setServerName("sample.db");

		
	}
	//create Table
	public void createTable(Connection con )throws SQLException{
		String sql = "DROP TABLE IF EXISTS test ;create table test (id integer, name string); ";
		Statement stat = null;
		stat = con.createStatement();
		stat.executeUpdate(sql);
		
	}
	//drop table
	public void dropTable(Connection con)throws SQLException{
		String sql = "drop table test ";
		Statement stat = null;
		stat = con.createStatement();
		stat.executeUpdate(sql);
	}
	
	//insert data
	public void insert(Connection con,int id,String name)throws SQLException{
		String sql = "insert into test (id,name) values(?,?)";
		PreparedStatement pst = null;
		pst = con.prepareStatement(sql);
		int idx = 1 ; 
		pst.setInt(idx++, id);
		pst.setString(idx++, name);
		pst.executeUpdate();
		
	}
	//update data
	public void update(Connection con,int id,String name)throws SQLException{
		String sql = "update test set name = ? where id = ?";
		PreparedStatement pst = null;
		pst = con.prepareStatement(sql);
		int idx = 1 ; 
		pst.setString(idx++, name);
		pst.setInt(idx++, id);
		pst.executeUpdate();
	}
	//delete data
	public void delete(Connection con,int id)throws SQLException{
		String sql = "delete from test where id = ?";
		PreparedStatement pst = null;
		pst = con.prepareStatement(sql);
		int idx = 1 ; 
		pst.setInt(idx++, id);
		pst.executeUpdate();
	}
	
	public void selectAll(Connection con)throws SQLException{
		String sql = "select * from test";
		Statement stat = null;
		ResultSet rs = null;
		stat = con.createStatement();
		rs = stat.executeQuery(sql);
		while(rs.next())
		{
			System.out.println(rs.getInt("id")+"\t"+rs.getString("name"));
		}
	}
	public static void main(String args[]) throws SQLException{
		MyTest test = new MyTest();
		Connection con = test.getConnection();
		//create table
		test.createTable(con);
		//inser two rows
		test.insert(con, 1, "Eric");
		test.insert(con, 2, "Liu");
		//Query All 
		System.out.println("Query All Data Response :");
		test.selectAll(con);
		
		//Update first row data
		System.out.println("Update first row name:");
		test.update(con, 1, "Peter");
		//Query Response
		test.selectAll(con);
		
		//Delete first row data
		System.out.println("Delete first row :");
		test.delete(con, 1);
		//Query Response
		test.selectAll(con);
		
		//drop table
		test.dropTable(con);
		
		con.close();
		
	}
}
