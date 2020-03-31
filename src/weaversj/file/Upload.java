package weaversj.file;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weaver.general.BaseBean;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class FileDemo
 */
public class Upload {
    
      /**
         * 
         */
        private static final long serialVersionUID = 564190060577130813L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BaseBean().writeLog("1111111111111111111111");

		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = "c://weaver/Ecology_8/Weaver2017_base/ecology/weaversj";
		// String savePath = "C:/tomcat7.0.67/webapps/ueditor/taskAnnex"; //250
		// String path="/home/attachFiles/userpic"; //线上
		// 上传时生成的临时文件保存目录
		String tempPath = "c://weaver/Ecology_8/Weaver2017_base/ecology/weaversj";

		
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			// 创建临时目录
			tmpFile.mkdir();
		}
		File tmpFile1 = new File(savePath);
		if (!tmpFile1.exists()) {
			// 文件保存路径
			tmpFile1.mkdir();
		}
		new BaseBean().writeLog("2222");

		// 消息提示
		String message = "";
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
			factory.setSizeThreshold(1024 * 100);// 设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
			// 设置上传时生成的临时文件的保存目录
			factory.setRepository(tmpFile);
			
			new BaseBean().writeLog("3333");

			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 3、判断提交上来的数据是否是上传表单的数据
//			if (!ServletFileUpload.isMultipartContent(request)) {
//				// 按照传统方式获取数据
//				return;
//			}
			new BaseBean().writeLog("4444");

			// 设置上传单个文件的大小的最大值，字节
			//upload.setFileSizeMax(200 * 1024 * 1024);
			// 设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和
			upload.setSizeMax(1024 * 1024 * 200);
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			Iterator<FileItem> i = list.iterator();
			FileItem item;
			new BaseBean().writeLog("555555");

			while (i.hasNext()) {
				item = (FileItem) i.next();
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					new BaseBean().writeLog(name + ":" + value);
					// System.out.println(name + "=" + value);
				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					String filename = item.getName();
					new BaseBean().writeLog("附件:" + filename);

					// System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 得到上传文件的扩展名
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					// System.out.println("上传的文件的扩展名是："+fileExtName);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 得到文件保存的名称
					String saveFilename = makeFileName(filename);
					// 得到文件的保存目录
					String realSavePath = savePath;// makePath(saveFilename, savePath);
					// System.out.println(realSavePath);

					int size = (int) item.getSize();
					// 创建一个文件输出流
					/*
					 * FileOutputStream out = new FileOutputStream(realSavePath + "\\" +
					 * saveFilename); //创建一个缓冲区 byte buffer[] = new byte[1024]; //判断输入流中的数据是否已经读完的标识
					 * int len = 0; //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					 * while((len=in.read(buffer))>0){
					 * //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
					 * out.write(buffer, 0, len); } //关闭输入流 in.close(); //关闭输出流 out.close();
					 */
					// 删除处理文件上传时生成的临时文件

					// 存储文件
					File file = new File(realSavePath);
					String uploadPath = file.getPath();
					File savedFile = new File(realSavePath, saveFilename);
					item.write(savedFile);
					item.delete();
					new BaseBean().writeLog("saveFilename" + saveFilename);
					new BaseBean().writeLog("filename" + filename);
					new BaseBean().writeLog("size" + size);
					new BaseBean().writeLog("fileExtName" + fileExtName);

					message = "文件上传成功！";
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("message", "单个文件超出最大值！！！");
			return;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
			return;
		} catch (Exception e) {
			message = "文件上传失败！";
			e.printStackTrace();
		}

	}
        
      
        /**
        * @Method: makeFileName
        * @Description: 生成上传文件的文件名
        */ 
        private String makeFileName(String filename){
            //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
            
            Date currentDate2 = new Date();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");
            SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMDDHHMMSS");
            String str2 =df2.format(currentDate2);
            String str3 =df3.format(currentDate2);
            String[] a  = filename.split("\\.");
            String fileType = a[a.length-1];
            String saveFileName =filename.replace("."+fileType, "")+ str3+"."+fileType;//保存的文件名
            return saveFileName;
            //return UUID.randomUUID().toString() + "_" + filename;
        }
        
        /**
         * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
        * @Method: makePath
        * @Description: 
        * @Anthor:
        *
        * @param filename 文件名，要根据文件名生成存储目录
        * @param savePath 文件存储路径
        * @return 新的存储目录
        */ 
        private String makePath(String filename,String savePath){
            //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
            int hashcode = filename.hashCode();
            int dir1 = hashcode&0xf;  //0--15
            int dir2 = (hashcode&0xf0)>>4;  //0-15
            //构造新的保存目录
            String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
            //File既可以代表文件也可以代表目录
            File file = new File(dir);
            //如果目录不存在
            if(!file.exists()){
                //创建目录
                file.mkdirs();
            }
            return dir;
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            doGet(request, response);
        }
        
}