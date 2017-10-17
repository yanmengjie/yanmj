package tmall.bean;

/*1. 基本属性的getter、setter
2. 与Product的多对于关系
3. 与User的多对一关系
4. 与Order的多对一关系*/

public class OrderItem {
	private int id;
	private int number;
	private User user;
	private Order order;
	private Product product;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	

}
