package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static int port=3306;
	private static String ip="127.0.0.1";
	private static String database="tmall";
	private static String loginName="root";
	private static String password="admin";
	private static String encoding="UTF-8";
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public static Connection getConnection() throws SQLException{
		//%s,%d 格式化输出
		
		String url=String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip,port,database,encoding);
		return DriverManager.getConnection(url,loginName,password);
		
	}
	public static void main(String[] args) throws SQLException {
		System.out.println(getConnection());
	}
}
