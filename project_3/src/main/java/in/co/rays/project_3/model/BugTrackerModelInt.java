package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BugTrackerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of BugTracker Model
 * 
 * @author Rajendra Singh
 *
 */
public interface BugTrackerModelInt {

	public long add(BugTrackerDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(BugTrackerDTO dto) throws ApplicationException;

	public void update(BugTrackerDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(BugTrackerDTO dto) throws ApplicationException;

	public List search(BugTrackerDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public BugTrackerDTO findByPK(long pk) throws ApplicationException;

	public BugTrackerDTO findByBugId(int bugId) throws ApplicationException, DuplicateRecordException;

	public BugTrackerDTO findByTitle(String title) throws ApplicationException, DuplicateRecordException;

	public BugTrackerDTO findByAssignedTo(String assignedTo) throws ApplicationException, DuplicateRecordException;

}