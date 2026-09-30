package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ListenerDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ListenerModelInt {

    public long add(ListenerDTO dto) throws DatabaseException, DuplicateRecordException;

    public void delete(ListenerDTO dto) throws DatabaseException;

    public void update(ListenerDTO dto) throws DatabaseException, DuplicateRecordException;

    public List list() throws DatabaseException;

    public List search(ListenerDTO dto, int pageNo, int pageSize) throws DatabaseException;

    public ListenerDTO findByPK(long pk) throws DatabaseException;

    public ListenerDTO findByListenerCode(String listenerCode) throws DatabaseException;

}