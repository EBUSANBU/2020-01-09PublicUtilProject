package weaversj.hpage.action;



import weaversj.hpage.bean.TypeBaseBean;
import weaversj.hpage.service.TypePageService;
import weaversj.hpage.service.impl.TypePageServiceImpl;

public class TypePageAction {
	
	/**
	 * 查询接口
	 * @param basebean
	 * @return
	 */
	public String getSelectData(TypeBaseBean basebean) {
		TypePageService typeService = new TypePageServiceImpl();
		return typeService.selectData(basebean);
	}

	/**
	 * 删除接口
	 * @param basebean
	 * @return
	 */
	public String getDeleteData(TypeBaseBean basebean) {
		TypePageService typeService = new TypePageServiceImpl();
		return typeService.delData(basebean);
	}
	/**
	 * 新增接口
	 */
	public String addData(TypeBaseBean basebean) {
		TypePageService typeService = new TypePageServiceImpl();
		return typeService.addData(basebean);
	}
	/**
	 * 查看数据
	 */
	public String getViewData(TypeBaseBean basebean) {
		TypePageService typeService = new TypePageServiceImpl();
		return typeService.getViewData(basebean);
	}
	/**
	 * 更新
	 */
	public String editData(TypeBaseBean basebean) {
		TypePageService typeService = new TypePageServiceImpl();
		return typeService.editData(basebean);
	}
}
