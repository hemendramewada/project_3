package in.co.rays.project_3.dto;

public class SmartLightDTO extends BaseDTO {

	private long lightId;
	private String lightCode;
	private String roomName;
	private Integer brightnessLevel;
	private String status;

	public long getLightId() {
		return lightId;
	}

	public void setLightId(long lightId) {
		this.lightId = lightId;
	}

	public String getLightCode() {
		return lightCode;
	}

	public void setLightCode(String lightCode) {
		this.lightCode = lightCode;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getBrightnessLevel() {
		return brightnessLevel;
	}

	public void setBrightnessLevel(Integer brightnessLevel) {
		this.brightnessLevel = brightnessLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
