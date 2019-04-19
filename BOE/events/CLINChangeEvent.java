package BOE.events;

public class CLINChangeEvent implements Postable {

	private int clin_id, clin_num;
	private String clin_desc;
	
	public CLINChangeEvent(int clin_id, int clin_num, String clin_desc) {
		this.clin_id = clin_id;
		this.clin_num = clin_num;
		this.clin_desc = clin_desc;
	}
	
	public int getClin_id() {
		return clin_id;
	}

	public void setClin_id(int clin_id) {
		this.clin_id = clin_id;
	}

	public int getClin_num() {
		return clin_num;
	}

	public void setClin_num(int clin_num) {
		this.clin_num = clin_num;
	}

	public String getClin_desc() {
		return clin_desc;
	}

	public void setClin_desc(String clin_desc) {
		this.clin_desc = clin_desc;
	}
}
