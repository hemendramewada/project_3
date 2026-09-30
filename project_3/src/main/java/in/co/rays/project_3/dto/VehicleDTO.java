package in.co.rays.project_3.dto;

public class VehicleDTO extends BaseDTO {

	private Long vechicleId;
	private String vehicleName;
	private String model;
	private String color;
	private Double price;

	public Long getVechicleId() {
		return vechicleId;
	}

	public void setVechicleId(Long vechicleId) {
		this.vechicleId = vechicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
