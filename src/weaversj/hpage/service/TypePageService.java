package weaversj.hpage.service;

import weaversj.hpage.bean.TypeBaseBean;

public interface TypePageService {

	public String selectData(TypeBaseBean basebean);
	public String delData(TypeBaseBean basebean);
	public String addData(TypeBaseBean basebean);
	public String getViewData(TypeBaseBean basebean);
	public String editData(TypeBaseBean basebean);
}
