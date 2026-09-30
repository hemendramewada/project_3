package in.co.rays.project_3.dto;

public class VoiceCommandDTO extends BaseDTO {

	private long commandId;
	private String commandCode;
	private String userName;
	private String commandText;
	private String status;

	public long getCommandId() {
		return commandId;
	}

	public void setCommandId(long commandId) {
		this.commandId = commandId;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommandText() {
		return commandText;
	}

	public void setCommandText(String commandText) {
		this.commandText = commandText;
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
