	

package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductReviewComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		return p2.getReviewCount()-p1.getReviewCount();
	}

}

//人气比较器把 评价数量多的放前面	
