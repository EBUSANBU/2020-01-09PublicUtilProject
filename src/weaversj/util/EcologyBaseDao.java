/**  
 * @Title      : EcologyBaseDao.java
 * @Package    : weaversj.util   
 * @version    : V1.0
 * @auther     : zyz
 * @date       : 2019年12月2日 下午9:01:57 
 * @Description: 
 */
package weaversj.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import weaver.general.BaseBean;

/**
 * @Title : EcologyBaseDao.java
 * @Package : weaversj.util
 * @version : V1.0
 * @auther : zyz
 * @date : 2019年12月2日 下午9:01:57
 * @Description:
 */
public class EcologyBaseDao {

	private static final BaseBean logs = new BaseBean();
	private static final String drivers = logs.getPropValue("weaver", "DriverClasses");
	private static final String url = logs.getPropValue("weaver", "ecology.url");
	private static final String username = logs.getPropValue("weaver", "ecology.user");
	private static final String password = logs.getPropValue("weaver", "ecology.password");

	private static Connection conn;
	
	public Connection getConn() {
		try {
			Class.forName(drivers);
			conn = DriverManager.getConnection(url, username,password);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	
	//关闭连接
	public void closeConnection(){
		try{
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
