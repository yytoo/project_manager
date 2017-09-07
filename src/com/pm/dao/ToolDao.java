package com.pm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.pm.util.DBManager;

public class ToolDao {
	public int GetPageCount(String aSql){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int iRowCount=-1;
		try {
			conn=DBManager.getConn();
			String sql =aSql;
			stmt= conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			rs.next();
			iRowCount=rs.getInt("rowCount");
			return iRowCount;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return iRowCount;
		}finally{
			try {
				if(rs != null){
					rs.close();
					rs=null;
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(stmt != null){
					stmt.close();
					stmt=null;
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBManager.closeConn(conn);
		}
		
	}
	
	public void insertPic(String aName, int aFormId, int aType){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn=DBManager.getConn();
			String sql ="INSERT INTO picture(NAME,form_id,TYPE) VALUE(?,?,?);";
			String sqlDel="DELETE FROM picture WHERE form_id=? AND TYPE=?;";
			stmt= conn.prepareStatement(sqlDel);			
			stmt.setInt(1,aFormId);
			stmt.setInt(2, aType);
			stmt.executeUpdate();
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, aName);
			stmt.setInt(2,aFormId);
			stmt.setInt(3, aType);
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(stmt != null){
					stmt.close();
					stmt=null;
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBManager.closeConn(conn);
		}
		
	}
	
	public String selectPic( int aFormId, int aType){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs=null;
		String fileName="";
		try {
			conn=DBManager.getConn();
			//String sql ="SELECT NAME FROM picture WHERE id=(SELECT MAX(id)FROM  picture WHERE form_id=? AND TYPE=?);";
			String sql ="SELECT NAME FROM picture WHERE form_id=? AND TYPE=?;";	
			stmt= conn.prepareStatement(sql);			
			stmt.setInt(1,aFormId);
			stmt.setInt(2, aType);
			rs=stmt.executeQuery();
			while(rs.next()){
			fileName=rs.getString("name");
			}
			
			return fileName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(rs != null){
					rs.close();
					rs=null;
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(stmt != null){
					stmt.close();
					stmt=null;
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBManager.closeConn(conn);
			
		}
		
	}                                                                      
	
}
