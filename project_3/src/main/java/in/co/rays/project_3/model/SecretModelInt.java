package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SecretDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;


public interface SecretModelInt {

	public long add(SecretDTO dto) throws DatabaseException, DuplicateRecordException;

	public void delete(SecretDTO dto) throws DatabaseException;

	public void update(SecretDTO dto) throws DatabaseException, DuplicateRecordException;

	public List list() throws DatabaseException;

	public List search(SecretDTO dto, int pageNo, int pageSize) throws DatabaseException;

	public SecretDTO findByPK(long pk) throws DatabaseException;

	public SecretDTO findBySecretCode(String secretCode) throws DatabaseException;

}