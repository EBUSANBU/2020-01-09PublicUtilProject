package weaversj.hpage.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RetrunMessBean {

	public String code;
	public String msg;
	public int count;
	public List<Map<String,Object>> data;
	
	public RetrunMessBean() {
		this.code = "";
		this.msg = "";
		this.count = 0;
		this.data = new ArrayList<Map<String,Object>>();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
	
}
