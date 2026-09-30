package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PortfolioDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Portfolio model
 * 
 * @author Rajendra Singh
 *
 */
public interface PortfolioModelInt {

	public long add(PortfolioDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(PortfolioDTO dto) throws DatabaseException;

	public void update(PortfolioDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(PortfolioDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public PortfolioDTO findByPK(long pk) throws DatabaseException;

	public PortfolioDTO findByPortfolioName(String portfolioName) throws DatabaseException;

}