package tmall.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class BaseForeServlet extends HttpServlet {
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
   public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			int start= 0;
			int count = 10;
			try {
				start = Integer.parseInt(request.getParameter("page.start"));
			} catch (Exception e) {
				
			}
			
			try {
				count = Integer.parseInt(request.getParameter("page.count"));
			} catch (Exception e) {
			}
			
			Page page = new Page(start,count);
			
			String method = (String) request.getAttribute("method");

			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
					javax.servlet.http.HttpServletResponse.class,Page.class);
			
			String redirect = m.invoke(this,request, response,page).toString();
			
			if(redirect.startsWith("@"))
				response.sendRedirect(redirect.substring(1));
			else if(redirect.startsWith("%"))
				response.getWriter().print(redirect.substring(1));
			else
				request.getRequestDispatcher(redirect).forward(request, response);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
