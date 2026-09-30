package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ResultDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Result model
 * 
 * @author Rajendra Singh
 *
 */
public interface ResultModelInt {

	public long add(ResultDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(ResultDTO dto) throws DatabaseException;

	public void update(ResultDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(ResultDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public ResultDTO findByPK(long pk) throws DatabaseException;

	public ResultDTO findByResultCode(String resultCode) throws DatabaseException;

}