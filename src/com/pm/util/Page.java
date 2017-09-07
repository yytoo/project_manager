package com.pm.util;

import com.pm.dao.ToolDao;

public class Page {
	
	private int iPageIndex=0;  //当前页数
	private int iRowCount=0;  //记录总数
	private int iPageSize=0;  //一页有多少行
	private int iPageCount;   //总页数

	
	public int getPageCount(int aPageSize){
		
		
		
		return iPageCount;
	}
	


	public int getiPageIndex() {
		return iPageIndex;
	}

	public void setiPageIndex(int iPageIndex) {
		this.iPageIndex = iPageIndex;
	}

	public int getiRowCount() {
		return iRowCount;
	}

	public void setiRowCount(String aSql) {
		ToolDao toolDao=new ToolDao();
		this.iRowCount = toolDao.GetPageCount(aSql);
	}

	public int getiPageSize() {
		return iPageSize;
	}

	public void setiPageSize(int iPageSize) {
		this.iPageSize = iPageSize;
	}

	public int getiPageCount() {
		return iPageCount;
	}

	public void setiPageCount() {
		iRowCount=getiRowCount();
		if(iRowCount%iPageSize==0){
			this.iPageCount=iRowCount/iPageSize;
		}else{
			this.iPageCount=iRowCount/iPageSize+1;
		}
	}
	
	
}
