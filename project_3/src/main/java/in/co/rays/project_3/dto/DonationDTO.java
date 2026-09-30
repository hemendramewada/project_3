package in.co.rays.project_3.dto;

import java.util.Date;

public class DonationDTO extends BaseDTO{
	
	
	private String donorName;
	private long amount;
	private Date donationDate;
	
	
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
	public String getDonorName() {
		return donorName;
	}
	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public Date getDonationDate() {
		return donationDate;
	}
	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}
	
	
	

}
