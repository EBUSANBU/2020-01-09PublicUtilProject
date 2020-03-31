package weaver.weaversso;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ln.LN;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.hrm.OnLineMonitor;
import weaver.hrm.User;

public class WeaverLoginFilter implements Filter{
	protected final Log log = LogFactory.getLog(getClass());
	
	private ServletContext application;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.application = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest ServletRequest, ServletResponse ServletResponse,FilterChain Chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)ServletRequest;
		HttpServletResponse response = (HttpServletResponse)ServletResponse;		
		if(StringUtils.isNotBlank(request.getHeader("x-requested-with"))){
			request.setCharacterEncoding("UTF-8");
		}
		RecordSet rs = new RecordSet();

		String ssoToken=request.getParameter("ssoToken");
		String ssologinid = "";
		if(ssoToken!=null){
		rs.execute("select * from  sso_login_oa where token='"+ssoToken+"' and isuse ='0' ");
		rs.writeLog("select * from  sso_login_oa where token='"+ssoToken+"' and isuse ='0'");
	
		if(rs.next()){
			ssologinid=rs.getString("loginid");
			rs.execute("update  sso_login_oa set isuse ='1' where token='"+ssoToken+"' and isuse =0");
			rs.writeLog("update  sso_login_oa set isuse ='1' where token='"+ssoToken+"' and isuse =0");
		}
		
	
		
		String currentPage = request.getServletPath().toLowerCase();
		String logintype = getLogintype(request);
		if(currentPage.toLowerCase().indexOf(("/system/inlicense.jsp"))==-1&&currentPage.toLowerCase().indexOf(("/system/licenseoperation.jsp"))==-1){
			
		
		//License限制

		Calendar today1 = Calendar.getInstance();
		String currentdate1 = Util.add0(today1.get(Calendar.YEAR), 4) + "-" + Util.add0(today1.get(Calendar.MONTH) + 1, 2) + "-" + Util.add0(today1.get(Calendar.DAY_OF_MONTH), 2);
		String message = "";
		           LN ckLicense = new LN();
		           message=ckLicense.CkLicense(currentdate1);
		           if (!message.equals("1")) {
		           	response.sendRedirect("/system/InLicense.jsp") ;
		           	return ;
		           } else {
		           	StaticObj.getInstance().putObject("isLicense", "true");
		           }
		}
		
		//==================
	
		if(!ssologinid.equals("")){
			
			rs.executeSql("select * from HrmResource where loginid='"+ssologinid+"' and status<4 and (accounttype !=1 or accounttype is null)");

			User user_new = null;
			if(rs.next()){//OA有相关人员
				request.getSession(true).setAttribute("weaver_login_type", "OALogin");

				User user = (User)request.getSession(true).getAttribute("weaver_user@bean") ;
				if(true||user==null){
					if(logintype.equals("1")){//用户登录
					user_new = new User();
					user_new.setUid(rs.getInt("id"));
					user_new.setLoginid(rs.getString("loginid"));
					user_new.setFirstname(rs.getString("firstname"));
					user_new.setLastname(rs.getString("lastname"));
					user_new.setAliasname(rs.getString("aliasname"));
					user_new.setTitle(rs.getString("title"));
					user_new.setTitlelocation(rs.getString("titlelocation"));
					user_new.setSex(rs.getString("sex"));
					user_new.setPwd(rs.getString("password"));
					String languageidweaver = rs.getString("systemlanguage");
					user_new.setLanguage(Util.getIntValue(languageidweaver, 7));
		
					user_new.setTelephone(rs.getString("telephone"));
					user_new.setMobile(rs.getString("mobile"));
					user_new.setMobilecall(rs.getString("mobilecall"));
					user_new.setEmail(rs.getString("email"));
					user_new.setCountryid(rs.getString("countryid"));
					user_new.setLocationid(rs.getString("locationid"));
					user_new.setResourcetype(rs.getString("resourcetype"));
					user_new.setStartdate(rs.getString("startdate"));
					user_new.setEnddate(rs.getString("enddate"));
					user_new.setContractdate(rs.getString("contractdate"));
					user_new.setJobtitle(rs.getString("jobtitle"));
					user_new.setJobgroup(rs.getString("jobgroup"));
					user_new.setJobactivity(rs.getString("jobactivity"));
					user_new.setJoblevel(rs.getString("joblevel"));
					user_new.setSeclevel(rs.getString("seclevel"));
					user_new.setUserDepartment(Util.getIntValue(rs.getString("departmentid"), 0));
					user_new.setUserSubCompany1(Util.getIntValue(rs.getString("subcompanyid1"), 0));
					user_new.setUserSubCompany2(Util.getIntValue(rs.getString("subcompanyid2"), 0));
					user_new.setUserSubCompany3(Util.getIntValue(rs.getString("subcompanyid3"), 0));
					user_new.setUserSubCompany4(Util.getIntValue(rs.getString("subcompanyid4"), 0));
					user_new.setManagerid(rs.getString("managerid"));
					user_new.setAssistantid(rs.getString("assistantid"));
					user_new.setPurchaselimit(rs.getString("purchaselimit"));
					user_new.setCurrencyid(rs.getString("currencyid"));
					user_new.setLastlogindate(rs.getString("currentdate"));
					user_new.setLogintype("1");
					user_new.setAccount(rs.getString("account"));
		
					user_new.setLoginip(request.getRemoteAddr());
					request.getSession(true).setMaxInactiveInterval(60 * 60 * 24);
					request.getSession(true).setAttribute("weaver_user@bean", user_new);
					request.getSession(true).setAttribute("browser_isie", getisIE(request));
					
					//多帐号登陆
					if (user_new.getUID() != 1) {  //is not sysadmin
						weaver.login.VerifyLogin VerifyLogin = new weaver.login.VerifyLogin();
						java.util.List accounts = VerifyLogin.getAccountsById(user_new.getUID());
						request.getSession(true).setAttribute("accounts", accounts);
					}
					
					request.getSession(true).setAttribute("moniter", new OnLineMonitor("" + user_new.getUID(),user_new.getLoginip()));
					Util.setCookie(response, "loginfileweaver", "/login/Login.jsp?logintype=1", 172800);
					Util.setCookie(response, "loginidweaver", ""+user_new.getUID(), 172800);
					Util.setCookie(response, "languageidweaver", languageidweaver, 172800);
					
					Map logmessages=(Map)application.getAttribute("logmessages");
	                if(logmessages==null){
		                logmessages=new HashMap();
		                logmessages.put(""+user_new.getUID(),"");
		                application.setAttribute("logmessages",logmessages);
	                }
	                
	                request.getSession(true).setAttribute("logmessage",getLogMessage(user_new.getUID()+""));
	                
	                //登录日志
	                weaver.systeminfo.SysMaintenanceLog log1 = new  weaver.systeminfo.SysMaintenanceLog();
	                log1.resetParameter();
	                log1.setRelatedId(rs.getInt("id"));
	                log1.setRelatedName((rs.getString("firstname") + " " + rs.getString("lastname")).trim());
	                log1.setOperateType("6");
	                log1.setOperateDesc("");
	                log1.setOperateItem("60");
	                log1.setOperateUserid(rs.getInt("id"));
	                log1.setClientAddress(request.getRemoteAddr());
	                try {
						log1.setSysLogInfo();
					} catch (Exception e) {
						e.printStackTrace();
					}
						//==============
	                
					}else if(logintype.equals("2")){//客户登录
						user_new = new User();
						user_new.setUid(rs.getInt("id"));
	                    user_new.setLoginid(ssologinid);
						user_new.setPwd(rs.getString("PortalPassword"));
	                    user_new.setFirstname(rs.getString("name"));
	                    String languageidweaver = rs.getString("language");
	                    user_new.setLanguage(Util.getIntValue(languageidweaver, 7));
	                    user_new.setUserDepartment(Util.getIntValue(rs.getString("department"), 0));
	                    user_new.setUserSubCompany1(Util.getIntValue(rs.getString("subcompanyid1"), 0));
	                    user_new.setManagerid(rs.getString("manager"));
	                    user_new.setCountryid(rs.getString("country"));
	                    user_new.setEmail(rs.getString("email"));
	                    user_new.setAgent(Util.getIntValue(rs.getString("agent"), 0));
	                    user_new.setType(Util.getIntValue(rs.getString("type"), 0));
	                    user_new.setParentid(Util.getIntValue(rs.getString("parentid"), 0));
	                    user_new.setProvince(Util.getIntValue(rs.getString("province"), 0));
	                    user_new.setCity(Util.getIntValue(rs.getString("city"), 0));
	                    user_new.setLogintype("2");
	                    user_new.setSeclevel(rs.getString("seclevel"));
	                    user_new.setLoginip(request.getRemoteAddr());
	                    request.getSession(true).setAttribute("weaver_user@bean", user_new);
	                    request.getSession(true).setAttribute("browser_isie", getisIE(request));

	                    Util.setCookie(response, "loginfileweaver", "/login/Login.jsp?logintype=1", 172800);
	                    Util.setCookie(response, "loginidweaver", ""+user_new.getUID(), 172800);
	                    Util.setCookie(response, "languageidweaver", languageidweaver, 172800);

	                    char separator = Util.getSeparator();
	                    Calendar today = Calendar.getInstance();
	                    String currentdate = Util.add0(today.get(Calendar.YEAR), 4) + "-" + Util.add0(today.get(Calendar.MONTH) + 1, 2) + "-" + Util.add0(today.get(Calendar.DAY_OF_MONTH), 2);
	                    String currenttime = Util.add0(today.get(Calendar.HOUR_OF_DAY), 2) + ":" + Util.add0(today.get(Calendar.MINUTE), 2) + ":" + Util.add0(today.get(Calendar.SECOND), 2);

	                    String para = "" + rs.getInt("id") + separator + currentdate + separator + currenttime + separator + request.getRemoteAddr();
	                    rs.executeProc("CRM_LoginLog_Insert", para);

	                    char flag = 2;
	                    String sql = "";
	                    RecordSet rs1 = new RecordSet();
	                    rs1.executeProc("SysRemindInfo_InserCrmcontact", "" + rs.getInt("id") + flag + "1" + flag + "0");
	                    sql = " select count(*) from CRM_ContactLog where isfinished = 0 and contactdate ='" + currentdate + "' and agentid =" + rs.getInt("id");
	                    rs1.executeSql(sql);
	                    if (rs1.next()) {
	                        if (Util.getIntValue(rs1.getString(1), 0) > 0)
	                            rs1.executeProc("SysRemindInfo_InserCrmcontact", "" + rs.getInt("id") + flag + "1" + flag + "1");
	                    }
					}
				}else{
					user_new = user;
				}
				
				//如果登录页面是login.jsp、verifylogin.jsp，则跳转到main.jsp
				
				if(currentPage.indexOf("/login.jsp")>-1 || currentPage.indexOf("/verifylogin.jsp")>-1||currentPage.indexOf("/refresh.jsp")>-1 ){
					String tourl = "";
					String gopage=Util.null2String(request.getParameter("gopage"));
					if(logintype.equals("1")){//用户登录
						//用户的登录后的页面
						weaver.systeminfo.template.UserTemplate ut=new weaver.systeminfo.template.UserTemplate();				
						ut.getTemplateByUID(user_new.getUID(),user_new.getUserSubCompany1());
						int templateId=ut.getTemplateId();
						int extendTempletid=ut.getExtendtempletid();
						int extendtempletvalueid=ut.getExtendtempletvalueid();
						String defaultHp = ut.getDefaultHp();
						request.getSession(true).setAttribute("defaultHp",defaultHp);
					
					
						
						if(extendTempletid!=0){
							rs.executeSql("select id,extendname,extendurl from extendHomepage  where id="+extendTempletid);
							if(rs.next()){
								int id=Util.getIntValue(rs.getString("id"));
								String extendurl=Util.null2String(rs.getString("extendurl"));	
								rs.executeSql("select * from extendHpWebCustom where templateid="+templateId);
								String defaultshow ="";
								if(rs.next()){
									defaultshow = Util.null2String(rs.getString("defaultshow"));
								}
								String param = "";
								if(!defaultshow.equals("")){
									param ="&"+defaultshow.substring(defaultshow.indexOf("?")+1);
								}
								
								if(gopage.length()>0){
									tourl = "/login/RemindLogin.jsp?RedirectFile="+extendurl+"/index.jsp?templateId="+templateId+param+"&gopage="+gopage;
								}else{			
									tourl = "/login/RemindLogin.jsp?RedirectFile="+extendurl+"/index.jsp?templateId="+templateId+param;
								}  
							}
						} else {
							tourl="/wui/main.jsp";
						}
					
					}else if(logintype.equals("2")){//客户登录
						if(!gopage.equals("")){
							tourl = "/portal/main.jsp?gopage="+gopage ;
						}else{
							tourl = "/portal/main.jsp" ;
						}
					}
					
					response.sendRedirect(tourl);
				}
			}else{//OA中查无此人，必须重新登录
			
			}
			
		}
		}
		Chain.doFilter(request, response);
	}

	public void destroy() {
		
	}
	
	private String getLogMessage(String uid){
		String message = "";
		RecordSet rs = new RecordSet();
		String sqltmp = "";
        if (rs.getDBType().equals("oracle")) {
            sqltmp = "select * from (select * from SysMaintenanceLog where relatedid = " + uid + " and operatetype='6' and operateitem='60' order by id desc ) where rownum=1 ";
        }else if(rs.getDBType().equals("db2")){
            sqltmp = "select * from SysMaintenanceLog where relatedid = "+uid +" and operatetype='6' and operateitem='60' order by id desc fetch first 1 rows only ";
        } else {
            sqltmp = "select top 1 * from SysMaintenanceLog where relatedid = " + uid + " and operatetype='6' and operateitem='60' order by id desc";
        }

        rs.executeSql(sqltmp);
        if (rs.next()){
            message = rs.getString("clientaddress") + " " + rs.getString("operatedate") + " " + rs.getString("operatetime");
        }
        
        return message;
	}
	
	private String getLogintype(HttpServletRequest request){
		String logintype = Util.null2String(request.getParameter("logintype"));
		String userLogintype = "";
		User user = (User)request.getSession(true).getAttribute("weaver_user@bean") ;
		if(user!=null){
			userLogintype = Util.null2String(user.getLogintype());
			logintype = userLogintype;
		}
		
		if(logintype.equals("")){
			logintype = "1";
		}
		
		return logintype;
	}
	
	// 判断浏览器是否为IE
	private String getisIE(HttpServletRequest request) {
		String isIE = "true";
		String agent = request.getHeader("User-Agent").toLowerCase();
		if(agent.indexOf("rv:11") == -1 && agent.indexOf("msie") == -1 ) {
			isIE = "false";
		}
		if(agent.indexOf("rv:11") > -1 || agent.indexOf("msie") > -1 ) {
			isIE = "true";
		}
		return isIE;
	}
}
