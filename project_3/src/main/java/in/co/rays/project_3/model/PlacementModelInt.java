package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PlacementDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Placement model
 * 
 * @author Rajendra Singh
 *
 */
public interface PlacementModelInt {

    public long add(PlacementDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public void delete(PlacementDTO dto) 
            throws DatabaseException;

    public void update(PlacementDTO dto) 
            throws DatabaseException, DuplicateRecordException;

    public List list() 
            throws DatabaseException;

    public List search(PlacementDTO dto, int pageNo, int pageSize) 
            throws DatabaseException;

    public PlacementDTO findByPK(long pk) 
            throws DatabaseException;

    public PlacementDTO findByPlacementCode(String placementCode) 
            throws DatabaseException;

}