package weaversj.util;

public class PublicReturnExceptionUtil extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	public PublicReturnExceptionUtil(String msg){
		super("获取文本文件失败.\n" + msg);
	}

	public PublicReturnExceptionUtil(Throwable e) {
		super(e);
	}

	public PublicReturnExceptionUtil(String msg , Throwable e) {
		super(msg, e);
	}
		
}
