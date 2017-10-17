package tmall.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tmall.dao.CategoryDao;
import tmall.dao.OrderDao;
import tmall.dao.OrderItemDao;
import tmall.dao.ProductDao;
import tmall.dao.ProductImageDao;
import tmall.dao.PropertyDao;
import tmall.dao.PropertyValueDao;
import tmall.dao.ReviewDao;
import tmall.dao.UserDao;
import tmall.util.Page;

public abstract class BaseBackServlet extends HttpServlet {
	
	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page) ;
     
    protected CategoryDao categoryDao=new CategoryDao();
    protected OrderDao orderDao=new OrderDao();
    protected OrderItemDao orderItemDao=new OrderItemDao();
    protected ProductDao productDao=new ProductDao();
    protected ProductImageDao productImageDao=new ProductImageDao();
    protected PropertyDao propertyDao=new PropertyDao();
    protected PropertyValueDao propertyValueDao=new PropertyValueDao();
    protected ReviewDao reviewDao=new ReviewDao();
    protected UserDao userDao=new UserDao();
    
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
		
		try {
				//获取分页信息
				
				int start=0;
				int count=5;
				try{
				start=Integer.parseInt(request.getParameter("page.start"));
				}catch(Exception e){
					
				}
				try{
				count=Integer.parseInt(request.getParameter("page.count"));
				}catch(Exception e){
				
			}
			
			Page page=new Page(start,count);
			/*借助反射，调用相应的方法*/
			String method=(String) request.getAttribute("method");
			Method m=this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,javax.servlet.http.HttpServletResponse.class,Page.class);
			String redirect=m.invoke(this, request,respone,page).toString();
			
			/*	如果redirect是以@开头的字符串，那么就进行客户端跳转
				如果redirect是以%开头的字符串，那么就直接输出字符串
				如果都不是，则进行服务端跳转*/
			if(redirect.startsWith("@"))
				respone.sendRedirect(redirect.substring(1));
			else if(redirect.startsWith("%"))
					respone.getWriter().print(redirect.substring(1));
			else 
				request.getRequestDispatcher(redirect).forward(request, respone);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}
	
	public InputStream paraUplod(HttpServletRequest request,Map<String,String> params){
		InputStream is=null;
		try{
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			//设置图片大小的限制为10M
			factory.setSizeThreshold(1024*1024);
			
			List items=upload.parseRequest(request);
			Iterator iter=items.iterator();
			while(iter.hasNext()){
				FileItem item=(FileItem) iter.next();
				//是否是普通的表单类型
				if(!item.isFormField()){
					is=item.getInputStream();//获取文件的输入流	
				}else{
					String paramName=item.getFieldName();
					String paramValue=item.getString();
					paramValue=new String(paramValue.getBytes("ISO-8859-1"),"UTF-8");
					params.put(paramName, paramValue);
					
				}
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return is;
		
	}
	
	
}
