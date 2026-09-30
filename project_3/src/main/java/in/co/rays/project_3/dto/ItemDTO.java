package in.co.rays.project_3.dto;

import java.util.Date;

public class ItemDTO extends BaseDTO {

	private String itemName;
	private String itemAmmount;
	private Date purchaseDate;
	private String itemCategory;

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemAmmount() {
		return itemAmmount;
	}

	public void setItemAmmount(String itemAmmount) {
		this.itemAmmount = itemAmmount;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}