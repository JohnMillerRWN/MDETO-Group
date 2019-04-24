package BOE.events;

public class ProductChangeEvent implements Postable {

	private int product_id;
	private String product_name;
	
	public ProductChangeEvent(int product_id, String product_name) {
		this.setProduct_id(product_id);
		this.setProduct_name(product_name);
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
}
