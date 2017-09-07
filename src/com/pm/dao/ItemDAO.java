package com.pm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.pm.bean.Item;
import com.pm.util.DBHelper;
import com.pm.util.DBManager;

public class ItemDAO {
	public ArrayList<Item> GetFuncList(int aPageIndex, int aPageSize,int aTagId,int aPlatId,int aSchedule,int aTimeBeg,int aTimeEnd){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;		
		ArrayList<Item> items = new ArrayList<Item>();
		int iRowIndex=(aPageIndex-1)*aPageSize;
		String funcDate="";
		String formatDate="";
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			conn=DBManager.getConn();
			if(conn==null){
				System.out.println("conn is down=======================");
			}else{
				System.out.println(conn+"===================");
			}
			String sql ="SELECT f.`id` funcId, f.`func` funcName, pf.`name` platName, ft.`type` funcType, f.`page` page, " +
					"f.`demand_sch` dmSch, f.`design_sch` dsSch, f.`achieve_sch` achSch, f.`test_sch` testSch,f.schedule, p.`name` priority, " +
					"f.`date` funcDate FROM func f INNER JOIN platform pf ON f.`platform_id`=pf.`id` " +
					"INNER JOIN priority p ON f.`priority_id`=p.`id` INNER JOIN func_type ft ON ft.`id`=f.`type_id` ";
			if(aTagId!=0) sql=sql+" and f.type_id="+aTagId;
			if(aPlatId!=0) sql=sql+" and f.platform_id=" +aPlatId;
			if(aSchedule!=0) sql=sql+" and f.schedule=" +aSchedule;
			if(aTimeBeg!=0) sql=sql+" and f.date>" +aTimeBeg;
			if(aTimeEnd!=0) sql=sql+" and f.date<" +aTimeEnd;
			sql=sql+" ORDER BY f.id desc limit ?,? ;";
			stmt= conn.prepareStatement(sql);
			stmt.setInt(1, iRowIndex);
			stmt.setInt(2, aPageSize);
			rs = stmt.executeQuery();
			while(rs.next()){
				Item item=new Item();
				item.setFunction_id(rs.getInt("funcId"));
				item.setFunction_name(rs.getString("funcName"));
				item.setPlatform(rs.getString("platName"));
				item.setType(rs.getString("funcType"));
				item.setPage(rs.getString("page"));
/*				item.setDemand_schedule(rs.getString("dmSch"));
				item.setDesign_schedule(rs.getString("dsSch"));
				item.setAchieve_schedule(rs.getString("achSch"));
				item.setTest_schedule(rs.getString("testSch"));*/
				item.setPrority(rs.getString("priority"));
				item.setSchedule(rs.getInt("schedule"));
				funcDate=rs.getInt("funcDate")+"";
				formatDate= sdf.format(Long.parseLong(funcDate)*1000);
				item.setDate(formatDate);
				items.add(item);
			}
			return items;
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
	
	public Item getDetail(int aId){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String funcDate="";
		String formatDate="";
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Item item=new Item();
		try {
			conn=DBManager.getConn();
			String sql ="SELECT f.`id` funcId, f.`func` funcName, pf.`id` platId, ft.`id` typeId, f.`page` page,f.schedule, " +
					"f.`func_des` funcDes,f.`note`,f.`about`,f.`demand_sch` dmSch, f.`design_sch` dsSch, f.`achieve_sch` achSch," +
					" f.`test_sch` testSch, p.`id` prioId, f.`date` funcDate FROM func f  " +
					"INNER JOIN platform pf ON f.`platform_id`=pf.`id` INNER JOIN priority p ON f.`priority_id`=p.`id` " +
					"INNER JOIN func_type ft ON ft.`id`=f.`type_id` where f.id=? ;";		
			stmt= conn.prepareStatement(sql);
			stmt.setInt(1, aId);
			rs = stmt.executeQuery();
			rs.next();
			item.setFunction_id(rs.getInt("funcId"));
			item.setFunction_name(rs.getString("funcName"));
			item.setPlatId(rs.getInt("platId"));
			item.setTypeId(rs.getInt("typeId"));
			item.setPage(rs.getString("page"));
			item.setFunction_describe(rs.getString("funcDes"));
			item.setNote(rs.getString("note"));
			item.setAbout(rs.getString("about"));
			item.setDemand_schedule(rs.getString("dmSch"));
			item.setDesign_schedule(rs.getString("dsSch"));
			item.setAchieve_schedule(rs.getString("achSch"));
			item.setTest_schedule(rs.getString("testSch"));
			item.setPrioId(rs.getInt("prioId"));
			item.setSchedule(rs.getInt("schedule"));
			funcDate=rs.getInt("funcDate")+"";
			formatDate= sdf.format(Long.parseLong(funcDate)*1000);
			item.setDate(formatDate);			
			return item;
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
	
	public void updateProject(Item item){
		Connection conn = null;
		PreparedStatement stmt = null;

		Date date = new Date();
    	long lTime = date.getTime()/1000;
    	String sDateTime=lTime+"";
    	//sDateTime=sDateTime.substring(0, 10);
    	int iCurrentTime=Integer.parseInt(sDateTime); 
		try {
			conn=DBManager.getConn();
			String sql ="UPDATE func SET func=?, type_id=?, func_des=?, DATE=?, demand_sch=?, design_sch=?, achieve_sch=?, " +
					"test_sch=?, note=?, priority_id=?, about=?, platform_id=?, page=?,schedule=? WHERE id=?;";		
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, item.getFunction_name());
			stmt.setInt(2, item.getTypeId());
			stmt.setString(3, item.getFunction_describe());
			stmt.setInt(4, iCurrentTime );
			stmt.setString(5, item.getDemand_schedule());
			stmt.setString(6, item.getDesign_schedule());
			stmt.setString(7, item.getAchieve_schedule());
			stmt.setString(8, item.getTest_schedule());
			stmt.setString(9, item.getNote());
			stmt.setInt(10, item.getPrioId());
			stmt.setString(11, item.getAbout());
			stmt.setInt(12, item.getPlatId());
			stmt.setString(13, item.getPage());
			stmt.setInt(14, item.getSchedule());
			stmt.setInt(15, item.getFunction_id());			
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
	
	public void addProj(Item item,String aPic){
		Connection conn = null;
		PreparedStatement stmt = null;

		Date date = new Date();
    	long lTime = date.getTime()/1000;
    	String sDateTime=lTime+"";
    	//sDateTime=sDateTime.substring(0, 10);
    	int iCurrentTime=Integer.parseInt(sDateTime); 
    	ResultSet rs=null;
    	ToolDao toolDao =new ToolDao();
    	int iFormId=0;
		try {
			conn=DBManager.getConn();
			String sql ="INSERT INTO func( func, type_id, func_des, DATE, demand_sch, design_sch, achieve_sch, test_sch, " +
					"note, priority_id, about, platform_id, page,schedule) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";		
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, item.getFunction_name());
			stmt.setInt(2, item.getTypeId());
			stmt.setString(3, item.getFunction_describe());
			stmt.setInt(4, iCurrentTime );
			stmt.setString(5, item.getDemand_schedule());
			stmt.setString(6, item.getDesign_schedule());
			stmt.setString(7, item.getAchieve_schedule());
			stmt.setString(8, item.getTest_schedule());
			stmt.setString(9, item.getNote());
			stmt.setInt(10, item.getPrioId());
			stmt.setString(11, item.getAbout());
			stmt.setInt(12, item.getPlatId());
			stmt.setString(13, item.getPage());
			//System.out.println(item.getSchedule()+"=================schedule2");
			stmt.setInt(14,item.getSchedule());		
			stmt.executeUpdate();		
			rs = stmt.getGeneratedKeys(); 
			rs.next();
			iFormId=rs.getInt(1);
			toolDao.insertPic(aPic, iFormId, 2);
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

	
}
