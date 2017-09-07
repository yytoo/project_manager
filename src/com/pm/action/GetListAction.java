package com.pm.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import com.pm.bean.Item;
import com.pm.dao.ItemDAO;
import com.pm.dao.ToolDao;
import com.pm.util.BaseActionSupport;
import com.pm.util.Page;

public class GetListAction extends BaseActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageIndex;
	public String getList1(){
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
		String sSql="SELECT COUNT(*) rowCount FROM func WHERE 1=1 ";
		if(iTagId!=0) sSql=sSql+" and type_id="+iTagId;
		if(iPlatId!=0) sSql=sSql+" and platform_id=" +iPlatId;
		if(iSchedule!=0) sSql=sSql+" and schedule=" +iSchedule;
		if(iTimeBeg!=0) sSql=sSql+" and date>" +iTimeBeg;
		if(iTimeEnd!=0) sSql=sSql+" and date<" +iTimeEnd;
		sSql=sSql+";";
		page.setiRowCount(sSql);
		page.setiPageCount();
		int iPageCount=page.getiPageCount();
		if(iPageIndex < 0 || iPageIndex > iPageCount){
			iPageIndex=1;
		}
		ItemDAO itemDAO = new ItemDAO();
		ArrayList<Item> items = itemDAO.GetFuncList(iPageIndex, iPageSize,iTagId, iPlatId,iSchedule,iTimeBeg,iTimeEnd);
		request.setAttribute("lists", items);
		request.setAttribute("pageCount", iPageCount);	
		request.setAttribute("pageSize", iPageSize);
		request.setAttribute("pageIndex", iPageIndex);
		return "getlist1";
	}
	
	public String getDetail(){
		request=ServletActionContext.getRequest();
		int iFuncId=0;
		
		String sFuncId=request.getParameter("id");
	
		if(sFuncId== null ||"".equals(iFuncId)){
			
		}else{
			iFuncId=Integer.parseInt(sFuncId);
			ItemDAO itemdao = new ItemDAO();
			Item item=itemdao.getDetail(iFuncId);
			request.setAttribute("item", item);
		}
		
		ToolDao toolDao = new ToolDao();
		String picName=toolDao.selectPic(iFuncId, 2);
		if(picName== null ||"".equals(picName)){
			picName="helloworld.jpg";
		}
		request.setAttribute("picName", picName);
		return "getdetail";
	}
	
	public String updateProject(){
		request=ServletActionContext.getRequest();
		ItemDAO itemDao= new ItemDAO();
		Item item = new Item();
		String sId=request.getParameter("id");
		String sPic=request.getParameter("pic");
		
		item.setFunction_name(request.getParameter("funcName"));
		item.setTypeId(Integer.parseInt(request.getParameter("typeId")));
		item.setPlatId(Integer.parseInt(request.getParameter("platId")));
		item.setPage(request.getParameter("page"));
		item.setDemand_schedule(request.getParameter("demSch"));
		item.setDesign_schedule(request.getParameter("desSch"));
		item.setAchieve_schedule(request.getParameter("achSch"));
		item.setTest_schedule(request.getParameter("testSch"));
		item.setFunction_describe(request.getParameter("funcDes"));
		item.setNote(request.getParameter("note"));		
		item.setAbout(request.getParameter("about"));		
		item.setPrioId(Integer.parseInt(request.getParameter("prioId")));
		item.setSchedule(Integer.parseInt(request.getParameter("schedule")));
		//System.out.println(item.getSchedule()+"=================schedule1");
		if (sId != null && !sId.equals("")) {
			item.setFunction_id(Integer.parseInt(sId));		
			itemDao.updateProject(item);
		}else{			
			itemDao.addProj(item,sPic);
			
		}
		return "getlist1";
		
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
