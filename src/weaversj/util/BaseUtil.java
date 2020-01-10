package weaversj.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.Util;

public class BaseUtil extends WeaverSJUtil{

	private static final RecordSet rs = new RecordSet();
	private static final ModeRightInfo ModeRightInfo = new ModeRightInfo();//重构表单建模权限，使数据正常显示
	private static Map<String,String> mapField = new HashMap<String,String>();
	
	private static String getKeySet = "";
	private static String getValSet = "";
	
	public BaseUtil(){
		ModeRightInfo.setNewRight(true);
		setMap();
	}

	/**
	 * 获取分部id 根据分部编号
	 * @param rs
	 * @param subCode
	 * @return
	 */
	public static String getSubIdToCode(String subCode) throws Exception{
		String subcompanyid1 = "0";
		rs.execute("select id from HrmSubCompany where subcompanycode = '"+subCode+"'");
		if(rs.next()) {
			subcompanyid1 = Util.null2String(rs.getString("id")); 
		}
		return subcompanyid1;
	}
	
	/**
	 * 获取部门id 根据部门编号
	 * @param rs
	 * @param subCode
	 * @return
	 */
	public static String getDeptIdToCode(String deptCode) throws Exception{
		String departId = "0";
		rs.execute("select id from HrmDepartment where departmentcode = '"+deptCode+"'");
		if(rs.next()) {
			departId = Util.null2String(rs.getString("id")); 
		}
		return departId;
	}
	/**
	 * 获取部门id 获取部门编号
	 * @param rs
	 * @param subCode
	 * @return
	 */
	public static String getDeptCodeToId(String departId) throws Exception{
		String deptCode = "0";
		rs.execute("select departmentcode from HrmDepartment where id = '"+departId+"'");
		if(rs.next()) {
			deptCode = Util.null2String(rs.getString("departmentcode")); 
		}
		return deptCode;
	}
	/**
	 * 获取人员id 根据人员编号
	 * @param rs
	 * @param subCode
	 * @return
	 */
	public static String getHrmIdToCode(String hrmCode){
		String hrmId = "0";
		rs.execute("select id from hrmresource where workcode = '"+hrmCode+"'");
		if(rs.next()) {
			hrmId = Util.null2String(rs.getString("id")); 
		}
		return hrmId;
	}
	
	/**
	 * 获取主表名称 跟据 workflowid
	 * @param comId
	 * @return
	 */
	public static String getMainTable(String id) throws Exception{
		String tablename = "";
		rs.execute("select tablename from workflow_base w1,workflow_bill w2 where w1.formid = w2.id and w1.id = '"+id+"'");
		if(rs.next())
			tablename = Util.null2String(rs.getString("tablename"));
		return tablename;
	}
	
	public BaseUtil addMap(String key,String val){
		mapField.put(key,val);
		return this;
	}
	public BaseUtil addMap(Map<String,String> map){
		mapField.putAll(map);
		return this;
	}
	
	/**
	 * 插入日志
	 * @param modeId 模块id int
	 * @param action 接口名称 varcahr
	 * @param setMessage 接收信息 text
	 * @param returnMessage 返回信息 text
	 */
	public static void setLogs() throws Exception{
		getSetToString(mapField.keySet());
		rs.execute("insert into "+log_table+"("+getKeySet+") "
				+ " values("+getValSet+")");
		rs.execute("select max(id) as maxid from  "+log_table);
		if(rs.next()) {
			//权限重构
			ModeRightInfo.editModeDataShare(1,Integer.parseInt(modeId),rs.getInt("maxid"));//1系统管理员 721模块id
		}
	}
	
	/**
	 * 插入日志公共map
	 */
	private void setMap(){
		String nowDate = getNowDate("yyyy-MM-dd HH:mm:ss");
		mapField.put("formmodeid", modeId);
		mapField.put("modedatacreatertype", "0");
		mapField.put("modedatacreatedate", nowDate.split(" ")[0]);
		mapField.put("modedatacreatetime", nowDate.split(" ")[1]);
		mapField.put("modedatacreater", "1");
	}
	
	/**
	 * 获取  sql 插入 字符，与值
	 * @param s
	 */
	public static void getSetToString(Set<String> s){
		String keys = "";
		String vals = "";
		String splits = ",";
		for(String key:s){
			keys += splits+key;
			vals += splits+getStrTod(mapField.get(key));
		}
		getKeySet = keys.substring(splits.length());
		getValSet = vals.substring(splits.length());
	}
	/**
	 * 拼接字符
	 * @param str
	 * @return
	 */
	private static String getStrTod(String str){
		return "'"+str+"'";
	}
}
