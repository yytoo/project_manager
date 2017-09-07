package com.pm.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.pm.bean.CaseItem;
import com.pm.dao.CaseItemDAO;
import com.pm.dao.ToolDao;
import com.pm.util.BaseActionSupport;
import com.pm.util.Page;

public class CaseListAction  extends BaseActionSupport{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private int pageIndex;
	
	public String getCaseList(){
		request=ServletActionContext.getRequest();		
		int iPageSize=10;
		Page page = new Page();	
		int iPageIndex=1;
		int iTagId=0;
		int iPlatId=0;
		int iSchedule=0;
		int iTimeBeg=0;
		int iTimeEnd=0;
		String sUTime="";
		Date date;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		/*if(request.getParameter("pageIndex")!=null&&(!"".equals(request.getParameter("pageIndex")))){
			iPageIndex=Integer.parseInt(request.getParameter("pageIndex"));	
		}*/
		if(pageIndex!=0){
			iPageIndex=pageIndex;	
		}
		if(request.getParameter("tagId")!=null){
			iTagId=Integer.parseInt(request.getParameter("tagId"));	
			request.setAttribute("tagId",iTagId);
		}
		if(request.getParameter("platId")!=null){
			iPlatId=Integer.parseInt(request.getParameter("platId"));	
			request.setAttribute("platId",iPlatId);
		}
		if(request.getParameter("schedule")!=null){
			iSchedule=Integer.parseInt(request.getParameter("schedule"));	
			request.setAttribute("schedule",iSchedule);
		}

		String sTimeBeg=request.getParameter("timeBeg");		
		if(sTimeBeg!=null&&(!"".equals(sTimeBeg))){			
			try {
				date = sdf.parse(sTimeBeg);
				sUTime=date.getTime()/1000+"";
				iTimeBeg=Integer.parseInt(sUTime);	
				request.setAttribute("timeBeg",sTimeBeg);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}
		String sTimeEnd=request.getParameter("timeEnd");
		if(sTimeEnd!=null&&(!"".equals(sTimeEnd))){
			try {
				date = sdf.parse(sTimeEnd);
				sUTime=date.getTime()/1000+"";
				iTimeEnd=Integer.parseInt(sUTime);	
				request.setAttribute("timeEnd",sTimeEnd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		
		page.setiPageSize(iPageSize);
		
		String sSql="SELECT COUNT(*) rowCount FROM caselist WHERE 1=1";
		if(iTagId!=0) sSql=sSql+" and tag="+iTagId;
		if(iPlatId!=0) sSql=sSql+" and platform=" +iPlatId;
		if(iSchedule!=0) sSql=sSql+" and flag=" +iSchedule;
		if(iTimeBeg!=0) sSql=sSql+" and update_time>" +iTimeBeg;
		if(iTimeEnd!=0) sSql=sSql+" and update_time<" +iTimeEnd;
		sSql=sSql+";";
		//System.out.println(sSql);
		page.setiRowCount(sSql);
		page.setiPageCount();
		int iPageCount=page.getiPageCount();
		if(iPageIndex < 0 || iPageIndex > iPageCount){
			iPageIndex=1;
		}
		CaseItemDAO cid = new  CaseItemDAO();
		ArrayList<CaseItem> caseItems = cid.getCaseList(iPageIndex, iPageSize,iTagId, iPlatId,iSchedule,iTimeBeg,iTimeEnd);
		request.setAttribute("lists", caseItems);
		request.setAttribute("pageCount", iPageCount);	
		request.setAttribute("pageSize", iPageSize);
		request.setAttribute("pageIndex", iPageIndex);

		return "caseList";
	}
	
	public String getDetail(){
		request=ServletActionContext.getRequest();
		int iCaseId=0;
		String sCaseId=request.getParameter("id");
		int iPageIndex=1;
		if(pageIndex!=0){
			iPageIndex=pageIndex;
		}
		
		if(sCaseId== null ||"".equals(sCaseId)){
			
		}else{
			iCaseId=Integer.parseInt(sCaseId);
			CaseItemDAO itemdao = new CaseItemDAO();
			CaseItem caseItem=itemdao.getDetail(iCaseId);
			request.setAttribute("caseItem", caseItem);
		}
		
		ToolDao toolDao = new ToolDao();
		String picName=toolDao.selectPic(iCaseId, 1);
		if(picName== null ||"".equals(picName)){
			picName="helloworld.jpg";
		}
		//System.out.println(iPageIndex+"=======iPageIndex");
		request.setAttribute("pageIndex", iPageIndex);
		request.setAttribute("picName", picName);
		return "caseDetail";
	}
	
	public String updateCase(){
		request=ServletActionContext.getRequest();
		//int iPageIndex=Integer.parseInt(request.getParameter("pageIndex"));
		CaseItemDAO caseItemDao= new CaseItemDAO();
		CaseItem caseitem = new CaseItem();
		String sId=request.getParameter("id");
		
		String sPic=request.getParameter("pic");
		
		caseitem.setSummary(request.getParameter("summary"));
		caseitem.setTagId(Integer.parseInt(request.getParameter("tagId")));
		caseitem.setStatusId(Integer.parseInt(request.getParameter("statusId")));
		caseitem.setPrioId(Integer.parseInt(request.getParameter("prioId")));
		caseitem.setPlatId(Integer.parseInt(request.getParameter("platId")));
		caseitem.setPage(request.getParameter("page"));
		caseitem.setDetail(request.getParameter("detail"));
		caseitem.setComment(request.getParameter("comment"));
		caseitem.setEnvironment(request.getParameter("environment"));
		
		if (sId != null && !sId.equals("")) {
			caseitem.setId(Integer.parseInt(request.getParameter("id")));
			/*caseitem.setSummary(request.getParameter("summary"));
			caseitem.setTagId(Integer.parseInt(request.getParameter("tagId")));
			caseitem.setStatusId(Integer.parseInt(request.getParameter("statusId")));
			caseitem.setPrioId(Integer.parseInt(request.getParameter("prioId")));
			caseitem.setPlatId(Integer.parseInt(request.getParameter("platId")));
			caseitem.setPage(request.getParameter("page"));
			caseitem.setDetail(request.getParameter("detail"));
			caseitem.setComment(request.getParameter("comment"));
			caseitem.setEnvironment(request.getParameter("environment"));*/
			caseItemDao.updateCase(caseitem);
			
		}else{

			caseItemDao.addCase(caseitem,sPic);
			
		}
		//System.out.println(pageIndex+"==========pageIndex");
		//setPageIndex(iPageIndex);
		return "returnList";
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
