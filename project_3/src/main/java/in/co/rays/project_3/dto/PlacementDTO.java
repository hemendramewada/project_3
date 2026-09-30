package in.co.rays.project_3.dto;

public class PlacementDTO extends BaseDTO {

	String placementCode;
	String studentName;
	String companyName;
	String placementStatus;

	public String getPlacementCode() {
		return placementCode;
	}

	public void setPlacementCode(String placementCode) {
		this.placementCode = placementCode;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPlacementStatus() {
		return placementStatus;
	}

	public void setPlacementStatus(String placementStatus) {
		this.placementStatus = placementStatus;
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
