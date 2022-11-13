package dto;

public class CreateItemDTO {
	private String code;
	private String description;
	private String size;
	private String merk;
	private String article;
	private int sellPrice;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getMerk() {
		return merk;
	}
	public void setMerk(String merk) {
		this.merk = merk;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}  
}
