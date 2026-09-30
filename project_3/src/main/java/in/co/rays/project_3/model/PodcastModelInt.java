package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Podcast Model
 * 
 * @author Rajendra Singh
 *
 */
public interface PodcastModelInt {

	public long add(PodcastDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(PodcastDTO dto) throws ApplicationException;

	public void update(PodcastDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(PodcastDTO dto) throws ApplicationException;

	public List search(PodcastDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public PodcastDTO findByPK(long pk) throws ApplicationException;

	public PodcastDTO findByTitle(String title) throws ApplicationException, DuplicateRecordException;

	public PodcastDTO findByHostName(String hostName) throws ApplicationException, DuplicateRecordException;

	public PodcastDTO findByCategory(String category) throws ApplicationException, DuplicateRecordException;

}