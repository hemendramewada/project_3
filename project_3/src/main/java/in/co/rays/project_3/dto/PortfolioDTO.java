package in.co.rays.project_3.dto;

import java.util.Date;

public class PortfolioDTO extends BaseDTO {
	
	private String portfolioName;
	
	private int totalValue;
	
	private Date createdDate;
	
	public String getPortfolioName() {
		return portfolioName;
	}
	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}
	public int getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "portfolioName";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return portfolioName;
	}

}
