package weaversj.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.interfaces.datasource.DataSource;

public class BaseDao {

	private static Connection conn;
	private static Statement stmt;
	//获取数据库连接
	public Statement getConn(String connname) {
		DataSource ds = (DataSource) StaticObj.getServiceByFullname(("datasource."+connname),DataSource.class); 
		conn = ds.getConnection();
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	//关闭连接
	public void closeConnection(){
		try{
			stmt.close();
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt != null) stmt.close();
			}catch(SQLException e){
			}
			try{
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
