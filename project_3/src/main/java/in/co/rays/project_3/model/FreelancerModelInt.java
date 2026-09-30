package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.FreelancerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Freelancer Model
 * 
 * @author Rajendra Singh
 *
 */
public interface FreelancerModelInt {

	public long add(FreelancerDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(FreelancerDTO dto) throws ApplicationException;

	public void update(FreelancerDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(FreelancerDTO dto) throws ApplicationException;

	public List search(FreelancerDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public FreelancerDTO findByPK(long pk) throws ApplicationException;

	public FreelancerDTO findByName(String name) throws ApplicationException, DuplicateRecordException;

}