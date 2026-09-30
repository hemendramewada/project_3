package in.co.rays.project_3.dto;

public class LanguageDTO extends BaseDTO {

	String languageCode;

	String languageName;

	String direction;

	String languageStatus;
	
	

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLanguageStatus() {
		return languageStatus;
	}

	public void setLanguageStatus(String languageStatus) {
		this.languageStatus = languageStatus;
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
