package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ProfileDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ProfileModelInt {
	
	/**
	 * Interface of Profile model
	 * @author Rajendra Singh
	 *
	 */
	public long add(ProfileDTO dto)throws DatabaseException,DuplicateRecordException;
	public void delete(ProfileDTO dto)throws DatabaseException;
	public void update(ProfileDTO dto)throws DatabaseException,DuplicateRecordException;
	public List list()throws DatabaseException;
	public List search(ProfileDTO dto,int pageNo,int pageSize)throws DatabaseException;
	public ProfileDTO findByPK(long pk)throws DatabaseException;
	public ProfileDTO findByName(String name)throws DatabaseException;

}