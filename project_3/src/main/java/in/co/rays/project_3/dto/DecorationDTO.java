package in.co.rays.project_3.dto;

/**
 * DecorationDTO encapsulates decoration attributes
 * 
 * @author 
 */

public class DecorationDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private Long decorationId;
	private String theme;
	private String vendorName;
	private String cost;

	public Long getDecorationId() {
		return decorationId;
	}

	public void setDecorationId(Long decorationId) {
		this.decorationId = decorationId;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return theme;
	}
}