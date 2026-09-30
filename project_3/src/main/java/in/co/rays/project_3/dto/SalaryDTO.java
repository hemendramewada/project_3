package in.co.rays.project_3.dto;

public class SalaryDTO extends BaseDTO {

	String salaryCode;
	String employeeName;
	Long salaryAmount;
	String salaryStatus;

	public String getSalaryCode() {
		return salaryCode;
	  }

	public void setSalaryCode(String salaryCode) {
		this.salaryCode = salaryCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getSalaryAmount() {
		return salaryAmount;
	}

	public void setSalaryAmount(Long salaryAmount) {
		this.salaryAmount = salaryAmount;
	}

	public String getSalaryStatus() {
		return salaryStatus;
	}

	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
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