package in.co.rays.project_3.dto;

public class SecretDTO extends BaseDTO {

	private String secretCode;
	private String keyName;
	private String secretValue;
	private String status;

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getSecretValue() {
		return secretValue;
	}

	public void setSecretValue(String secretValue) {
		this.secretValue = secretValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return secretValue;
	}
}