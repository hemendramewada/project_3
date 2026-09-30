package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SmartParkingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of SmartParking model
 * 
 * @author Rajendra Singh
 *
 */
public interface SmartParkingModelInt {

	public long add(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(SmartParkingDTO dto) throws ApplicationException;

	public void update(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException;

	public SmartParkingDTO findByPK(long pk) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(SmartParkingDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(SmartParkingDTO dto) throws ApplicationException;

	public void parkVehical(SmartParkingDTO dto) throws ApplicationException;

	public void releaseSlot(SmartParkingDTO dto) throws ApplicationException;

	public SmartParkingDTO findByVehicalNumber(String vehicalNumber) throws ApplicationException;

}