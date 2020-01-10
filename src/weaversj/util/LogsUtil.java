package weaversj.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

//输出日志类
public class LogsUtil{
	
	protected static boolean logWrites(String filePath,String str){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowDate = df.format(new Date());
        File file = new File(filePath);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        
        try {
            if (!file.exists()) {
            	file.mkdirs();
            }
            file = new File(filePath+"/"+nowDate.split(" ")[0]+".txt");
            if (!file.exists()) {
            	boolean hasFile = file.createNewFile();
                fos = new FileOutputStream(file);
            } else {
                fos = new FileOutputStream(file, true);
            }

            osw = new OutputStreamWriter(fos, "GBK");
            osw.write("---时间："+nowDate+"----日志输出:>>>>>>>"+str); //写入内容
            osw.write("\r\n");  //换行
        } catch (Exception e) {
        	throw new PublicReturnExceptionUtil(e);
        }finally {   //关闭流
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
            	throw new PublicReturnExceptionUtil(e);

            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            	throw new PublicReturnExceptionUtil(e);
            }
        }
        return true;
	}
}
