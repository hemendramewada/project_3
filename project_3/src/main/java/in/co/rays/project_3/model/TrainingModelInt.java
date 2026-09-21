package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.TrainingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface TrainingModelInt {

	public long add(TrainingDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(TrainingDTO dto) throws ApplicationException;

	public void update(TrainingDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(TrainingDTO dto) throws ApplicationException;

	public List search(TrainingDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public TrainingDTO findByPK(long pk) throws ApplicationException;

	public TrainingDTO findByTrainingCode(String trainingCode) throws ApplicationException;

}