package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SalaryDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Salary model
 * 
 * @author Rajendra Singh
 *
 */
public interface SalaryModelInt {

	public long add(SalaryDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(SalaryDTO dto) throws DatabaseException;

	public void update(SalaryDTO dto) throws DatabaseException, DuplicateRecordException;

	  public List list() throws DatabaseException;

	public List search(SalaryDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public SalaryDTO findByPK(long pk) throws DatabaseException;

	public SalaryDTO findBySalaryCode(String salaryCode) throws DatabaseException;

}