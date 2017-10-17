package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;



public class PropertyDao {
	
	/**一个分类下的总数*/
	public int getTotal(int cid){
		int total=0;
		try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement();){
			String sql="select count(*) from property where cid="+cid;
			
			ResultSet rs=s.executeQuery(sql);
			
			while(rs.next()){
				total=rs.getInt(1);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	
		return total;
	
	}
	
	public void add(Property bean){
		String sql="insert into property values(null,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			
			ps.execute();
			
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next()){
				int id=rs.getInt(1);
				bean.setId(id);
				
			}	
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void update(Property bean){
		String sql="update property set cid=?,name=? where id=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
			
			ps.execute();
		
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void delete(int id){
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="delete from  property where id="+id;
			s.execute(sql);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public Property get(int id){
		Property bean=new Property();
		
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			
			String sql="select * from property where id="+id;
			
			ResultSet rs=s.executeQuery(sql);
			if(rs.next()){
				bean.setId(id);
				String name=rs.getString("name");
				bean.setName(name);
				int cid=rs.getInt("cid");
				Category category=new CategoryDao().get(cid);
				bean.setCategory(category);
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return bean;
	
	}
	
	public Property get(String name,int cid){
		Property bean=null;
		
		String sql="select * from property where name=? and cid=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setString(1, name);
			ps.setInt(2, cid);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean=new Property();
				int id=rs.getInt(1);
				bean.setName(name);
				bean.setId(id);
				Category category=new CategoryDao().get(id);
				bean.setCategory(category);
				
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		return bean;
	}
	
	/**分类下的分页*/
	public List<Property> list(int cid){
		return list(cid,0,Short.MAX_VALUE);
		
	}
	public List<Property> list(int cid,int start,int count){
		List<Property> beans=new ArrayList<Property>();
		
		String sql="select * from property where cid=? order by id desc limit ?,?";
		
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Property bean=new Property();
				int id = rs.getInt(1);
                String name = rs.getString("name");
                bean.setName(name);
                Category category = new CategoryDao().get(cid);
                bean.setCategory(category);
                bean.setId(id);
                
                beans.add(bean);
				
			}
			
		}catch(Exception e){

			e.printStackTrace();
		}
		return beans;
	}
	
	
}