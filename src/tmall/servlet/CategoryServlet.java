package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class CategoryServlet extends BaseBackServlet {
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs=categoryDao.list(page.getStart(), page.getCount());
		int total=categoryDao.getTotal();
		page.setTotal(total);
		
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		
		return "admin/listCategory.jsp";
	}
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params=new HashMap<>();
		InputStream is=super.paraUplod(request, params);
		
		//把name传到数据库中
		String name=params.get("name");
		Category c=new Category();
		c.setName(name);
		categoryDao.add(c);
		
		File imageFolder=new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file=new File(imageFolder,c.getId()+".jpg");
		try{
			if(null!=is && 0!=is.available()){
				try(FileOutputStream fos=new FileOutputStream(file)){
					byte[] b=new byte[1024];
					int length=0;
					while((length=is.read(b))!=-1){
						fos.write(b, 0, length);	
					}
					fos.flush();
					//通过如下代码，把文件保存为jpg格式
					BufferedImage img=ImageUtil.change2jpg(file);
					ImageIO.write(img, "jpg", file);
					
				}catch(Exception e){
					e.printStackTrace();
				}	
			}			
		}catch(IOException e){
			e.printStackTrace();			
		}
		
		return "@admin_category_list";
	
	}
	
	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		categoryDao.delete(id);
		return "@admin_category_list";
	
	}
	
	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		
		Category c=categoryDao.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";
		
	}
	
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params=new HashMap<>();
		InputStream is=super.paraUplod(request, params);
		
		System.out.println(params);
		
		String name=params.get("name");
		int id=Integer.parseInt(params.get("id"));
		
		Category c=new Category();
		c.setId(id);
		c.setName(name);
		categoryDao.update(c);
		
		File imageFolder=new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file=new File(imageFolder,c.getId()+".jpg");
		file.getParentFile().mkdirs();
		
		try {
			if(null!=is && 0!=is.available()){
				try(FileOutputStream fos=new FileOutputStream(file)){
				byte[] b=new byte[1204*1024];
				int length=0;
				while((length=is.read(b))!=-1){
					fos.write(b, 0, length);
				}
				fos.flush();
				
				BufferedImage img=ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
				
				
				}catch(Exception e){
					e.printStackTrace();
				}		
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "@admin_category_list";
		
	}
	
	

}
