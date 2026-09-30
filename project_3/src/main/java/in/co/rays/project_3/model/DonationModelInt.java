package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DonationDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Donation model
 * 
 * @author Rajendra Singh
 *
 */
public interface DonationModelInt {

	public long add(DonationDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(DonationDTO dto) throws DatabaseException;

	public void update(DonationDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(DonationDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public DonationDTO findByPK(long pk) throws DatabaseException;

	public DonationDTO findByDonorName(String donorName) throws DatabaseException;

}