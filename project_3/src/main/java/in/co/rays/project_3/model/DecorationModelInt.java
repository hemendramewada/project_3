package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DecorationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Decoration model
 * 
 * @author 
 *
 */
public interface DecorationModelInt {

	public long add(DecorationDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(DecorationDTO dto) throws ApplicationException;

	public void update(DecorationDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(DecorationDTO dto) throws ApplicationException;

	public List search(DecorationDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public DecorationDTO findByPK(long pk) throws ApplicationException;

	public DecorationDTO findByTheme(String theme) throws ApplicationException;

}