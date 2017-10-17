package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;



public class BackServletFilter implements Filter {

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		/*	HttpServletRequest和ServletRequest都是接口 
			HttpServletRequest继承自ServletRequest */
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse respone=(HttpServletResponse) resp;
		
		String contextPath=request.getContextPath();//得到：/tmall
		String uri=request.getRequestURI();//得到:/tmall
		
		uri=StringUtils.remove(uri,contextPath);//得到：/admin_category_lsit
		
		if(uri.startsWith("/admin_")){
			//取出两个_之间的：category在拼接Servlet得到categoryServlet
			String servletPath=StringUtils.substringBetween(uri, "_", "_")+"Servlet";
			
			//取出_后面的：list.把list加到requset的mothed方法上
			String method=StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			
			//服务端跳转到categoryServlet
			req.getRequestDispatcher("/"+servletPath).forward(request, respone);
			return;
		
			
		}
		chain.doFilter(request, respone);//不是以admin_开头进进行，如css,js
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}
	

}
