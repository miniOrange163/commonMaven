package priv.linjb.common.parse.importDBF;

import java.sql.*;

public class DBConnection {
	/**
	 * 驱动类
	 */
	public static String drive = "oracle.jdbc.driver.OracleDriver";
	/**
	 * 连接驱动符
	 */
	public static String url = "jdbc:oracle:thin:@172.16.20.213:1521:orcl";
	/**
	 * 用户名
	 */
	public static String username = "ggfw";
	/**
	 * 密码
	 */
	public static String password = "ylz_ggfw";


	
	public static Connection getConnection(){
		
		try {
			Class.forName(drive);
			
			return	DriverManager.getConnection(url, username, password);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 关闭数据库
	 * @param rs 	返回结果记录集
	 * @param pst	执行sql操作
	 * @param con	建立连接
	 */
	public static void close(ResultSet rs,Statement pst,Connection con){
		
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(pst!=null){
					try {
						pst.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						if(con!=null){
							try {
								con.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		
	}
		public static void sort(int []a){
			
			int index=0;
			int temp;
			for(int i=0;i<a.length;i++){
				if(a[i]%2==1){
					temp = a[i];
					a[i] = a[index++];
					a[index] = temp;
				}
					
			}
		}
	
	public static void main(String[] args) {
		
		
		
		StringBuffer sql =new StringBuffer("select * from zxzj where ");
		
		String str ="aaa bbb     ccc";
		String[] array = str.split(" +");
		String temp;
		for(int i =0;i<array.length;i++){
			temp ="zxzjwh like %"+array[i]+"%";
			sql.append(temp);
			sql.append(" or ");
		}
		sql.delete(sql.length()-3, sql.length());
		System.out.println(sql.toString());
	}
}
