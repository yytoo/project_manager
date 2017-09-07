package com.pm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import com.pm.bean.CaseItem;
import com.pm.util.DBManager;


public class CaseItemDAO {
	
	public ArrayList<CaseItem> getCaseList(int aPageIndex, int aPageSize,int aTagId,int aPlatId,int aSchedule,int aTimeBeg,int aTimeEnd){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<CaseItem> caseList = new ArrayList<CaseItem>();
		int iRowIndex=(aPageIndex-1)*aPageSize;
		String funcDate="";
		String formatDate="";
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			conn=DBManager.getConn();
			String sql = "SELECT cl.`id`, cl.`summary`, cl.`page`, cl.`update_time`, p.`name` priority, cs.status caseStatus," +
					"pf.`name` platform,ct.`tag` tag FROM caselist cl INNER JOIN priority p ON cl.`priority`=p.`id` " +
					"INNER JOIN case_status cs ON cs.`id`=cl.`status` INNER JOIN platform pf ON cl.`platform`=pf.`id` " +
					"INNER JOIN case_tag ct ON cl.`tag`=ct.`id` where 1=1";
			
			if(aTagId!=0) sql=sql+" and cl.tag="+aTagId;
			if(aPlatId!=0) sql=sql+" and cl.platform=" +aPlatId;
			if(aSchedule!=0) sql=sql+" and cl.status=" +aSchedule;
			if(aTimeBeg!=0) sql=sql+" and cl.update_time>" +aTimeBeg;
			if(aTimeEnd!=0) sql=sql+" and cl.update_time<" +aTimeEnd;
			sql=sql+" ORDER BY cl.id desc limit ?,? ;";
			stmt= conn.prepareStatement(sql);
			stmt.setInt(1, iRowIndex);
			stmt.setInt(2, aPageSize);
			rs = stmt.executeQuery();
			while(rs.next()){
				CaseItem caseItem = new CaseItem();
				caseItem.setId(rs.getInt("id"));
				caseItem.setSummary(rs.getString("summary"));
				caseItem.setPage(rs.getString("page"));
				caseItem.setPriority(rs.getString("priority"));
				caseItem.setStatus(rs.getString("caseStatus"));
				caseItem.setPlatform(rs.getString("platform"));
				caseItem.setTag(rs.getString("tag"));

				funcDate=rs.getInt("update_time")+"";
				formatDate= sdf.format(Long.parseLong(funcDate)*1000);
				caseItem.setUpdateTime(formatDate);	
				
				caseList.add(caseItem);
			}
			return caseList;
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
	
	public CaseItem getDetail(int aId){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String funcDate="";
		String formatDate="";
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CaseItem caseItem = new CaseItem();
		try {
			conn=DBManager.getConn();
			String sql ="SELECT cl.`id`, cl.`summary`, cl.`page`, cl.`update_time`, p.`id` prioId, cs.id statusId," +
					"pf.`id` platId,ct.`id` tagId,cl.`detail`,cl.`comment`,cl.`environment`,cl.`user_id` userId FROM caselist cl " +
					" INNER JOIN priority p ON cl.`priority`=p.`id` INNER JOIN case_status cs ON cs.`id`=cl.`status`" +
					" INNER JOIN platform pf ON cl.`platform`=pf.`id` INNER JOIN case_tag ct ON cl.`tag`=ct.`id` WHERE cl.id=?;";		
			stmt= conn.prepareStatement(sql);
			stmt.setInt(1, aId);
			rs = stmt.executeQuery();
			rs.next();
			caseItem.setId(rs.getInt("id"));
			caseItem.setSummary(rs.getString("summary"));
			caseItem.setPage(rs.getString("page"));
			caseItem.setPrioId(rs.getInt("prioId"));
			caseItem.setStatusId(rs.getInt("statusId"));
			caseItem.setPlatId(rs.getInt("platId"));
			caseItem.setTagId(rs.getInt("tagId"));
			caseItem.setDetail(rs.getString("detail"));
			caseItem.setComment(rs.getString("comment"));
			caseItem.setEnvironment(rs.getString("environment"));
			caseItem.setUserId(rs.getInt("userId"));
			funcDate=rs.getInt("update_time")+"";
			formatDate= sdf.format(Long.parseLong(funcDate)*1000);
			caseItem.setUpdateTime(formatDate);	
			//System.out.println(caseItem.getSummary()+"================summary");
			return caseItem;
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
	
	public void updateCase(CaseItem caseitem){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn=DBManager.getConn();
			String sql ="UPDATE caselist SET summary=?, detail=?, tag=?, STATUS=?, platform=?, " +
					" page=?, COMMENT=?, environment=? ,priority=? WHERE id=?;";		
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, caseitem.getSummary());
			stmt.setString(2, caseitem.getDetail());
			stmt.setInt(3, caseitem.getTagId());
			stmt.setInt(4, caseitem.getStatusId());
			stmt.setInt(5, caseitem.getPlatId());
			stmt.setString(6, caseitem.getPage());
			stmt.setString(7, caseitem.getComment());
			stmt.setString(8, caseitem.getEnvironment());
			stmt.setInt(9, caseitem.getPrioId());	
			stmt.setInt(10, caseitem.getId());	
			//System.out.println(caseitem.getComment()+"==============Comment");
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
	
	public void addCase(CaseItem caseitem,String aPic){
		Connection conn = null;
		PreparedStatement stmt = null;
		Date date = new Date();
    	long lTime = date.getTime()/1000;
    	String sDateTime=lTime+"";
    	//sDateTime=sDateTime.substring(0, 10);
    	int iCurrentTime=Integer.parseInt(sDateTime);
    	ToolDao toolDao = new ToolDao();
    	ResultSet rs=null;
    	int iFormId=0;
		try {
			conn=DBManager.getConn();
			String sql ="INSERT INTO caselist(summary, detail, tag, STATUS, platform,  page, COMMENT, environment," +
					"priority,update_time,user_id) VALUE(?,?,?,?,?,?,?,?,?,?,?);";	
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, caseitem.getSummary());
			stmt.setString(2, caseitem.getDetail());
			stmt.setInt(3, caseitem.getTagId());
			stmt.setInt(4, caseitem.getStatusId());
			stmt.setInt(5, caseitem.getPlatId());
			stmt.setString(6, caseitem.getPage());
			stmt.setString(7, caseitem.getComment());
			stmt.setString(8, caseitem.getEnvironment());
			stmt.setInt(9, caseitem.getPrioId());	
			stmt.setInt(10, iCurrentTime);
			stmt.setInt(11, 1);			
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys(); 
			rs.next();
			iFormId=rs.getInt(1);
			toolDao.insertPic(aPic, iFormId, 1);
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
	
	public ArrayList<CaseItem> screeningCase(int aPageIndex, int aPageSize,int aStatusID,int aPlatformId,int aTimeBeg, int aTimeEnd, int aFlag){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<CaseItem> caseList = new ArrayList<CaseItem>();
		int iRowIndex=(aPageIndex-1)*aPageSize;
		String funcDate="";
		String formatDate="";
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			conn=DBManager.getConn();		
			String sql = "SELECT cl.`id`, cl.`summary`, cl.`page`, cl.`update_time`, p.`name` priority, cs.status caseStatus," +
					"pf.`name` platform,ct.`tag` tag FROM caselist cl INNER JOIN priority p ON cl.`priority`=p.`id` " +
					"INNER JOIN case_status cs ON cs.`id`=cl.`status` INNER JOIN platform pf ON cl.`platform`=pf.`id` " +
					"INNER JOIN case_tag ct ON cl.`tag`=ct.`id` ";
			if(aStatusID!=0){
				sql=sql+" where cl.status="+aStatusID;
			}
			if(aPlatformId!=0){
				sql=sql+" and cl.platform="+aPlatformId;
			}
			if(aFlag!=0){
				sql=sql+" and cl.flag="+aFlag;
			}
			sql=sql+ "and (cl.update_time between "+aTimeBeg+" and "+aTimeEnd+" ) ";
			String limitSql=" ' ORDER BY cl.id limit ?,? ;";
			sql=sql+limitSql;		
			stmt= conn.prepareStatement(sql);
			stmt.setInt(1, iRowIndex);
			stmt.setInt(2, aPageSize);
			rs = stmt.executeQuery();
			while(rs.next()){
				CaseItem caseItem = new CaseItem();
				caseItem.setId(rs.getInt("id"));
				caseItem.setSummary(rs.getString("summary"));
				caseItem.setPage(rs.getString("page"));
				caseItem.setPriority(rs.getString("priority"));
				caseItem.setStatus(rs.getString("caseStatus"));
				caseItem.setPlatform(rs.getString("platform"));
				caseItem.setTag(rs.getString("tag"));

				funcDate=rs.getInt("update_time")+"";
				formatDate= sdf.format(Long.parseLong(funcDate)*1000);
				caseItem.setUpdateTime(formatDate);	
				
				caseList.add(caseItem);
			}
			return caseList;
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
