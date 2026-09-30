package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.HospitalDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Hospital model
 * 
 * @author Rajendra Singh
 *
 */
public interface HospitalModelInt {

	public long add(HospitalDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(HospitalDTO dto) throws DatabaseException;

	public void update(HospitalDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(HospitalDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public HospitalDTO findByPK(long pk) throws DatabaseException;

	public HospitalDTO findByHospitalId(String hospitalId) throws DatabaseException;

}