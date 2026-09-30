package in.co.rays.project_3.dto;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * SessionDTO JavaDto encapsulates session attributes
 * @author Rajendra Singh
 *
 */

public class SessionDTO extends  BaseDTO{

	

	String sessionToken;

	@Override
	public String toString() {
		return "SessionDTO [sessionToken=" + getId() + ", userName=" + userName + ", loginTime=" + loginTime
				+ ", logoutTime=" + logoutTime + ", sessionStatus=" + sessionStatus + "]";
	}

	String userName;

	Date loginTime;

	Date logoutTime;

	String sessionStatus;

	
	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
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
