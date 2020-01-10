package weaversj.hpage.action;



import weaversj.hpage.bean.TypeBaseBean;
import weaversj.hpage.service.TypePageService;
import weaversj.hpage.service.impl.TypePageServiceImpl;

public class TypePageAction {
	
	public String getSelectData(TypeBaseBean basebean) {
		TypePageService typeService = new TypePageServiceImpl();
		return typeService.selectData(basebean);
	}

	public String getDeleteData(TypeBaseBean basebean) {
		TypePageService typeService = new TypePageServiceImpl();
		return typeService.delData(basebean);
	}
}
