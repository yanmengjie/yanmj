package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.dao.PropertyValueDao;
import tmall.util.Page;

public class ProductServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid=Integer.parseInt(request.getParameter("cid"));
		Category c=categoryDao.get(cid);
		
		String name=request.getParameter("name");
		String subTitle=request.getParameter("subTitle");
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
	    float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
	    int stock = Integer.parseInt(request.getParameter("stock"));
	    
	    Product p=new Product();
	    p.setCategory(c);
	    p.setName(name);
	    p.setSubTitle(subTitle);
	    p.setOrignalPrice(orignalPrice);
	    p.setPromotePrice(promotePrice);
	    p.setStock(stock);
	    productDao.add(p);
	    return "@admin_product_list?cid="+cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		Product p=productDao.get(id);
		productDao.delete(id);
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		Product p=productDao.get(id);
		request.setAttribute("p", p);
		
		return "admin/editProduct.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid=Integer.parseInt(request.getParameter("cid"));
		Category c=categoryDao.get(cid);
		
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String subTitle=request.getParameter("subTitle");
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
	    float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
	    int stock = Integer.parseInt(request.getParameter("stock"));
	    
	    Product p=new Product();
	    p.setCategory(c);
	    p.setName(name);
	    p.setSubTitle(subTitle);
	    p.setOrignalPrice(orignalPrice);
	    p.setPromotePrice(promotePrice);
	    p.setStock(stock);
	    p.setId(id);
	    
	    productDao.update(p); 
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		
		int cid=Integer.parseInt(request.getParameter("cid"));
		Category c=categoryDao.get(cid);
		
		List<Product> ps=productDao.list(cid, page.getStart(), page.getCount());
		int total=productDao.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		
		
		
		request.setAttribute("ps", ps);
		request.setAttribute("c", c);
		request.setAttribute("page", page);
		
		return "admin/listProduct.jsp";
	}
	
	//属性值得设置
	public String editPropertyValue(HttpServletRequest request,HttpServletResponse response,Page page){
		int id=Integer.parseInt(request.getParameter("id"));//产品的ID
		Product p=productDao.get(id);//得到该id下的产品对象
		request.setAttribute("p", p);
		propertyValueDao.init(p);//初始化属性值		
		List<PropertyValue> pvs=propertyValueDao.list(p.getId());//得到这一产品下的属性值
		
		request.setAttribute("pvs", pvs);
		return "admin/editProductValue.jsp";
	}
	
	public String updatePropertyValue(HttpServletRequest request,HttpServletResponse response,Page page){
		int pvid=Integer.parseInt(request.getParameter("pvid"));
		System.out.println(pvid);
		
		
		
		String value=request.getParameter("value");
		System.out.println(value);
		
		PropertyValue pv=propertyValueDao.get(pvid);
		pv.setValue(value);
		propertyValueDao.update(pv);
		
		return "%success";
	}
}
