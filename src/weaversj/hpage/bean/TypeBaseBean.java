package weaversj.hpage.bean;

public class TypeBaseBean {

	public String flag;
	public String typeName;
	public String type;
	public String pageSize;
	public String pageCount;
	public String ids;
	
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public TypeBaseBean() {
		this.flag = "";
		this.type = "";
		this.typeName = "";
		this.pageCount = "";
		this.pageSize = "";
		this.ids = "";
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
