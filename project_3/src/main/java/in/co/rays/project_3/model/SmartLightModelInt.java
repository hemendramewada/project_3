package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SmartLightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of SmartLight Model
 * 
 * @author Rajendra Singh
 *
 */
public interface SmartLightModelInt {

	public long add(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(SmartLightDTO dto) throws ApplicationException;

	public void update(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException;

	public SmartLightDTO findByPK(long pk) throws ApplicationException;

	public SmartLightDTO findByLightCode(String lightCode) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(SmartLightDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(SmartLightDTO dto) throws ApplicationException;

}