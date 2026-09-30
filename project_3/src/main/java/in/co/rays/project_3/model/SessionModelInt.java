package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SessionDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Session model
 * 
 * @author Rajendra Singh
 *
 */
public interface SessionModelInt {

    public long add(SessionDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public void delete(SessionDTO dto) 
            throws DatabaseException;

    public void update(SessionDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public List list() 
            throws DatabaseException;

    public List search(SessionDTO dto, int pageNo, int pageSize) 
            throws DatabaseException;

    public SessionDTO findByPK(long pk) 
            throws DatabaseException;

    public SessionDTO findByToken(String token) 
            throws DatabaseException;

}