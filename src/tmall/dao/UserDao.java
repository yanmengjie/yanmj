package tmall.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDao {
	public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
  
            String sql = "select count(*) from User";
  
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return total;
    }
	
	public void add(User bean) {
		  
        String sql = "insert into user values(null ,? ,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
  
            ps.execute();
  
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
    }
	
	public void delete(int id){
		
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			
			String sql="delete user where id="+id;
			s.execute(sql);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(User bean){
		
		String sql="update user set name=? ,password=? where id=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			
			ps.execute();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public User get(int id){
		User bean=null;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select * from user where id="+id;
			
			ResultSet rs=s.executeQuery(sql);
			
			if(rs.next()){
				bean =new User();
				String name=rs.getString("name");
				bean.setName(name);
				String password=rs.getString("password");
				bean.setPassword(password);
				bean.setId(id);
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return bean;
	
	}
	
	public List<User> list(){
		return list(0,Short.MAX_VALUE);
	}
	
	public List<User> list(int start,int count){
		
		List<User> beans=new ArrayList<User>();
		
		String sql="select * from user order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()){
				User bean = new User();
                int id = rs.getInt(1);
 
                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                 
                bean.setId(id);
                beans.add(bean);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return beans;	
	}
	
	/**
	 * 判断用户是否存在*/
	public boolean isExist(String name){
		User user=get(name);
		return user!=null;
	}
	
	/**根据用户名获取对象*/
	public User get(String name) {
		User bean=null;
		String sql="select * from user where name=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, name);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				bean=new User();
				int id=rs.getInt("id");
				bean.setId(id);
				bean.setName(name);
				String password=rs.getString("password");
				bean.setPassword(password);
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	
	/**根据账号和密码获取对象，这才是合理的判断账号密码是否正确的方式，*/
	public User get(String name,String password){
		User bean=null;
		String sql="select * from user where name=? and password=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			
			ps.setString(1, name);
			ps.setString(2, password);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				bean=new User();
				int id=rs.getInt("id");
				bean.setId(id);
				bean.setName(name);
				bean.setPassword(password);
			
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
}
