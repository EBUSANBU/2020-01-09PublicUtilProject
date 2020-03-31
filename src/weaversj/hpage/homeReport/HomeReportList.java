package weaversj.hpage.homeReport;

import java.util.ArrayList;
import java.util.List;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;

public class HomeReportList {

	public String getCanDelType(String id) {
		String str = "true";
		RecordSet localRecordSet = new RecordSet();
		localRecordSet.executeSql("SELECT DISTINCT type FROM roommess where type='" + id + "'");
		if (localRecordSet.next()) {
			str = "false";
		}
		return str;
	}

	public String getLinkType(String paramString1, String paramString2) {
		String str = "";
		str = "<a href=javaScript:newDialog(1," + paramString2 + ")>" + paramString1 + "</a>";
		return str;
	}

	public List getCanDelTypeList(String id) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add("true");
		RecordSet localRecordSet = new RecordSet();
		localRecordSet.executeSql("SELECT DISTINCT type FROM roommess where type='" + id + "'");
		if (localRecordSet.next()) {
			localArrayList.add("false");
		} else {
			localArrayList.add("true");
		}
		return localArrayList;
	}

}
