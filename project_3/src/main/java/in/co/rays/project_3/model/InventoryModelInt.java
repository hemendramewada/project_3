package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.InventoryDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Inventory model
 * 
 * @author Rajendra Singh
 *
 */
public interface InventoryModelInt {

	public long add(InventoryDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(InventoryDTO dto) throws DatabaseException;

	public void update(InventoryDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(InventoryDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public InventoryDTO findByPK(long pk) throws DatabaseException;

	public InventoryDTO findByProduct(String product) throws DatabaseException;

}
