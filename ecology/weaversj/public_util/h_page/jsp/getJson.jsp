<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="weaver.general.BaseBean" %>
<%@ page import="weaver.conn.RecordSet" %>
<%@ page import="java.util.Map" %>
<%
String flags = Util.null2String(request.getParameter("flags"));

weaversj.hpage.action.TypePageAction typeAction = new weaversj.hpage.action.TypePageAction();
weaversj.hpage.bean.TypeBaseBean typeBase = new weaversj.hpage.bean.TypeBaseBean();
String getJson = "";
if("select".equals(flags)){
	String pages = Util.null2String(request.getParameter("page"));
	String limit = Util.null2String(request.getParameter("limit"));
	String types = Util.null2String(request.getParameter("types"));
	String typeName = Util.null2String(request.getParameter("typeName"));

	typeBase.setFlag(flags);
	typeBase.setPageSize(pages);
	typeBase.setPageCount(limit);
	typeBase.setTypeName(typeName);
	typeBase.setType(types);
	getJson = typeAction.getSelectData(typeBase);

}else if("del".equals(flags)){
	String ids = Util.null2String(request.getParameter("ids"));
	typeBase.setFlag(flags);
	typeBase.setIds(ids);
	getJson = typeAction.getDeleteData(typeBase);
}
out.print(getJson.toString().trim());
%>