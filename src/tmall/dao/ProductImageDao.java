package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProductImageDao {
	
	public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";
	
	public int getTotal(){
		int total=0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			
			String sql="select count(*) from productImage";
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()){
				total=rs.getInt(1);	
			}		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(ProductImage bean){
		String sql="insert into productImage values(null,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setInt(1, bean.getProduct().getId());//外键，product的Id
			ps.setString(2, bean.getType());
			
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
	
	public void update(ProductImage bean){
		String sql="update Product set pid=? and type=? where id=?";
		
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1,bean.getProduct().getId());
			ps.setString(2, bean.getType());
			ps.setInt(3, bean.getId());
			
			ps.execute();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void delete(int id){
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			String sql="delete from ProductImage where id="+id;
			s.execute(sql);//原始用的execute	
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public ProductImage get(int id){
		ProductImage bean=new ProductImage();
		
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			String sql="select * from productImage where id="+id;
			ResultSet rs= s.executeQuery(sql);
			if(rs.next()){
				int pid=rs.getInt("pid");
				Product product=new ProductDao().get(pid);
				bean.setProduct(product);
				String type=rs.getString("type");
				bean.setId(id);
				bean.setType(type);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return bean;	
	}
	
	public List<ProductImage> list(Product p, String type) {
        return list(p, type,0, Short.MAX_VALUE);
    }
  
    public List<ProductImage> list(Product p, String type, int start, int count) {
        List<ProductImage> beans = new ArrayList<ProductImage>();
  
        String sql = "select * from ProductImage where pid =? and type =? order by id desc limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setInt(1, p.getId());
            ps.setString(2, type);
  
            ps.setInt(3, start);
            ps.setInt(4, count);
             
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
 
                ProductImage bean = new ProductImage();
                int id = rs.getInt(1);
 
                bean.setProduct(p);
                bean.setType(type);
                bean.setId(id);
                   
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }
  
}

