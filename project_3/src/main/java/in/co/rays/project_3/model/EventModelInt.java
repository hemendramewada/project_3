package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Event model
 * 
 * @author Rajendra Singh
 *
 */
public interface EventModelInt {

	public long add(EventDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(EventDTO dto) throws DatabaseException;

	public void update(EventDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(EventDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public EventDTO findByPK(long pk) throws DatabaseException;

	public EventDTO findByEventCode(String eventCode) throws DatabaseException;

}