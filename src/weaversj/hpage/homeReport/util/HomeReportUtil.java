package weaversj.hpage.homeReport.util;

import java.util.ResourceBundle;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaversj.util.WeaverSJUtil;

public class HomeReportUtil extends WeaverSJUtil{
	private static final String url = "weaversj.homereport_base";
	private static final ResourceBundle property = ResourceBundle.getBundle(url);

	public HomeReportUtil() {
		
	}
	
	/**
	 * 判断是否有权限
	 * @param userid
	 * @return
	 */
	public static boolean getRole(String userid) {
		RecordSet rs = new RecordSet();
		String role ="";
		rs.execute("select * from roombaseutil");
		if(rs.next()) {
			role = Util.null2String(rs.getString("role")); 
		}else {
			return true;
		}
		rs.execute("select * from HrmRoleMembers where roleid = (select id  from HrmRoles where rolesmark = '"+role+"') and resourceid = '"+userid+"'");
		if(rs.next()) {
			return true;
		}
		return false;
	} 
	
}
