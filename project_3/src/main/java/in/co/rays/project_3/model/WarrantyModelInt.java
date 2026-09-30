package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.WarrantyDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Warranty model
 * 
 * @author Rajendra Singh
 *
 */
public interface WarrantyModelInt {

	public long add(WarrantyDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(WarrantyDTO dto) throws DatabaseException;

	public void update(WarrantyDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(WarrantyDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public WarrantyDTO findByPK(long pk) throws DatabaseException;

	public WarrantyDTO findByProductName(String productName) throws DatabaseException;

}