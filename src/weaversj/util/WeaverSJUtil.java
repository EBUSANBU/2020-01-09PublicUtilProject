package weaversj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;



//公共方法类
public class WeaverSJUtil extends LogsUtil{

	private static final String url = "weaversj.weaversj_util";
	private static final ResourceBundle property = ResourceBundle.getBundle(url);
	private static final String fileLocator_url = property.getString("fileLocator_url_window");
	protected static final String modeId = property.getString("log_modeid");
	protected static final String log_table = property.getString("log_table");

	public WeaverSJUtil(){
		
	}
	
	/**
	 * 获取当前时间 精确到毫秒
	 * @return
	 */
	public static String getNowDate(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}
	/**
	 * 获取当前时间 自定义个数
	 * @return
	 */
	public static String getNowDate(String format){
		return new SimpleDateFormat(format).format(new Date());
	}
	/**
	 * 获取json数据
	 * @param json
	 * @return
	 * @throws JSONException 
	 * @throws Exception
	 */
	
	public static JSONArray getJson(String json) throws JSONException{
        JSONTokener jsonTokener = new JSONTokener(json);  
        JSONArray jsonArray = new JSONArray();//获取整个json文件的内容，因为最外层是数组，所以用JSONArray来构造  
		if(!"".equals(json)&&null!=json){
			return new JSONArray(jsonTokener);
		}
		return jsonArray;
	}
	/**
	 * 重新打印日志
	 * @param filePath
	 * @param str
	 * @return
	 */
	public static boolean logWrites(String str){
		return logWrites(fileLocator_url,str);
	}
	
	
	/**
	 * 去除 字符串空格与换行
	 * @param str
	 * @return
	 */
	public static String getRepAllRN(String str){
		return 	str.replaceAll("\r|\n", "");
	}
	/**
	 * 去除 字符串
	 * @param str
	 * @return
	 */
	public static String getRepAllRN(String str,String res){
		return 	str.replaceAll(res, "");
	}
	
	/**
	 * 截取字符串
	 * @param fl
	 * @param str
	 * @return
	 */ 
	public static String getSplit(String fl,int index,String str){
		return str.split(fl)[index];
	}
	
	/**
	 * 几个月后的某天
	 * @param dqDate
	 * @param mounth
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	public static String getMouth(String dqDate,int mounth) throws ParseException{
		Calendar c = Calendar.getInstance();//获得一个日历的实例
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dates	=  sdf.parse(dqDate);//初始日期
		c.setTime(dates);//设置日历时间
		c.add(Calendar.MONTH,mounth);//在日历的月份上增加6个月
		return sdf.format(c.getTime());
	}
	/**
	 * 集合去重
	 * @param list
	 * @return
	 */
	public static List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	}  
	/**
	 * list 转换字符串
	 * @param list
	 * @return
	 */
	public static String listToString(List	list) {   
		String str = "";
	    for(Object obj:list){
	    	if("".equals(obj.toString()))
	    		continue;
	    	str += ","+obj.toString();
	    }
	    if(!"".equals(str))
	    	str = str.substring(1);
	    return str;
	}
	
	/**
	 * 判断是否为空 null
	 * @param arr
	 * @return
	 */
	public static boolean getNull(String arr){
		if("".equals(arr.trim())||null==arr)
			return false;
		return true;
	}
	/**
	 * 获取jsonkey 
	 * @Title      : getJsonKey 
	 * @Package    : weaversj.dd.attence.util     
	 * @param      : @return      
	 * @return     : String   
	 * @throws     :
	 * @author     : zyz
	 * @throws JSONException 
	 * @date       : 2019年12月4日 下午8:18:09 
	 * @Description:
	 */
	public static String getJsonKey(String key,String json) throws JSONException{
        return new org.json.JSONObject(json).getString(key);
	}
}
