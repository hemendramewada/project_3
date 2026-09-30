package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.LanguageDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Language model
 * 
 * @author Rajendra Singh
 *
 */
public interface LanguageModelInt {

    public long add(LanguageDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public void delete(LanguageDTO dto) 
            throws DatabaseException;

    public void update(LanguageDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public List list() 
            throws DatabaseException;

    public List search(LanguageDTO dto, int pageNo, int pageSize) 
            throws DatabaseException;

    public LanguageDTO findByPK(long pk) 
            throws DatabaseException;

    public LanguageDTO findByLanguageCode(String languageCode) 
            throws DatabaseException;

}