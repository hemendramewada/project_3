package in.co.rays.project_3.dto;

import java.util.Date;

/**
 * EmployeeDTO encapsulates employee attributes.
 * 
 * @author Hemendra mewada
 */

public class EmployeeDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private String employeeName;
	private String lastName;
	private String department;
	private Date dob;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return employeeName + " " + lastName;
	}
}