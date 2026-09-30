package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CustomerDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CustomerModelInt {

	public long add(CustomerDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(CustomerDTO dto) throws DatabaseException;

	public void update(CustomerDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(CustomerDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public CustomerDTO findByPK(long pk) throws DatabaseException;

	public CustomerDTO findByContactNumber(String contactNumber) throws DatabaseException;

}