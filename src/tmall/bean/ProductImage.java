package tmall.bean;

/*1. 基础属性的getter、setter
2. 与Product的多对一关系*/
public class ProductImage {
	private int id;
	private String type;
	private Product product;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	

}
