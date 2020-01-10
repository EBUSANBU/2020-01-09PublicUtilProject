package weaversj.hpage.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaversj.hpage.bean.TypeBaseBean;
import weaversj.hpage.service.TypePageService;

public class TypePageServiceImpl implements TypePageService{

	@Override
	public String selectData(TypeBaseBean basebean) {
		JSONObject returnJsonObj = new JSONObject();

		if("".equals(basebean.getFlag())) {
			returnJsonObj.put("code", "1");
			returnJsonObj.put("msg", "标识为空！");
			returnJsonObj.put("count", "0");
			returnJsonObj.put("data", "[]");
			return JSON.toJSONString(returnJsonObj);
		}
		
		String sqlWhere = "";
		String  orderBy = " order by id desc";
		if(!"".equals(basebean.getType())) {
			sqlWhere += " and 	iftable = '"+basebean.getType()+"'";
		}
		if(!"".equals(basebean.getTypeName())) {
			sqlWhere += " and 	name like '%"+basebean.getTypeName()+"%'";
		}
		int nowPages = Integer.parseInt(basebean.getPageSize())*Integer.parseInt(basebean.getPageCount());
		int beginPages = nowPages - Integer.parseInt(basebean.getPageCount());
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonData = null;

		// TODO Auto-generated method stub
		RecordSet rs = new RecordSet();
		int count = 0;
		try {
			rs.execute("select count(*) as count from uf_flces");
			rs.next();
			count = rs.getInt("count");
			String sql = "select * from (select row_number()over(order by tempcolumn)temprownumber,* from "
					+ " (select top  "+nowPages+" tempcolumn=10000,* from uf_flces where 1=1 "+sqlWhere+orderBy+") t )tt "
					+" where temprownumber > "+beginPages;
			rs.execute(sql);
			while(rs.next()) {
				jsonData = new JSONObject();
				jsonData.put("t_id", Util.null2String(rs.getString("id")));
				jsonData.put("t_name", Util.null2String(rs.getString("name")));
				if("0".equals(Util.null2String(rs.getString("iftable")))) {
					jsonData.put("if_table", "接口");
				}else {
					jsonData.put("if_table","数据表" );
				}
				jsonData.put("table_name", Util.null2String(rs.getString("tablename")));
				jsonData.put("t_url", Util.null2String(rs.getString("url")));
				jsonArr.add(jsonData);
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonObj.put("code", "2");
			returnJsonObj.put("msg", "查询实现类异常："+e.getMessage());
			returnJsonObj.put("count", "0");
			returnJsonObj.put("data", "[]");
			return JSON.toJSONString(returnJsonObj);
			// TODO: handle exception
		}
		returnJsonObj.put("code", "0");
		returnJsonObj.put("msg", "查询成功");
		returnJsonObj.put("count", count);
		returnJsonObj.put("data", jsonArr);		
		return JSON.toJSONString(returnJsonObj);
	}

	@Override
	public String delData(TypeBaseBean basebean) {
		JSONObject returnJsonObj = new JSONObject();
		if("".equals(basebean.getFlag())) {
			returnJsonObj.put("code", "1");
			returnJsonObj.put("mess", "标识为空");
			return JSON.toJSONString(returnJsonObj);
		}
		if("".equals(basebean.getIds())) {
			returnJsonObj.put("code", "1");
			returnJsonObj.put("mess", "id为空");
			return JSON.toJSONString(returnJsonObj);
		}
		try {
			RecordSet rs = new RecordSet();
			boolean flag =  rs.execute("delete uf_flces where id in("+basebean.getIds()+")");
			if(flag) {
				returnJsonObj.put("code", "0");
				returnJsonObj.put("mess", "删除成功");
			}else {
				returnJsonObj.put("code", "1");
				returnJsonObj.put("mess", rs.getMsg());
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnJsonObj.put("code", "2");
			returnJsonObj.put("mess", "删除异常："+e.getMessage());
			return JSON.toJSONString(returnJsonObj);
			// TODO: handle exception
		}
		return JSON.toJSONString(returnJsonObj);
	}
}