package in.co.rays.project_3.dto;

import java.util.Date;

/**
 * Training JavaDto encapsulates Training attributes
 * 
 * @author 
 *
 */

public class TrainingDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private String trainingCode;
	private String trainingName;
	private String trainerName;
	private Date trainingDate;
	private String trainingStatus;

	public String getTrainingCode() {
		return trainingCode;
	}

	public void setTrainingCode(String trainingCode) {
		this.trainingCode = trainingCode;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public String getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(String trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return trainingName;
	}

}