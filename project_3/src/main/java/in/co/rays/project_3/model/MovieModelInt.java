package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.MovieDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Movie model
 * @author Hemendra mewada
 *
 */
public interface MovieModelInt {

	public long add(MovieDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(MovieDTO dto) throws ApplicationException;

	public void update(MovieDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(MovieDTO dto) throws ApplicationException;

	public List search(MovieDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public MovieDTO findByPK(long pk) throws ApplicationException;

	public MovieDTO findByName(String movieName) throws ApplicationException;

}