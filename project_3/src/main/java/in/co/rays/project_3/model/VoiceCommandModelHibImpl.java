package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.VoiceCommandDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of VoiceCommand Model
 *
 * @author Rajendra Singh
 *
 */
public class VoiceCommandModelHibImpl implements VoiceCommandModelInt {

	/**
	 * Add Voice Command
	 */
	@Override
	public long add(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException {

		VoiceCommandDTO existDto = findByCommandCode(dto.getCommandCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Command Code already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			session.save(dto);

			tx.commit();

		} catch (org.hibernate.exception.JDBCConnectionException e) {

			e.printStackTrace();

			throw new DatabaseException("Database connection was lost. Please try again.");

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Voice Command Add : " + e.getMessage());

		} finally {

			session.close();
		}

		return dto.getCommandId();
	}

	/**
	 * Delete Voice Command
	 */
	@Override
	public void delete(VoiceCommandDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.delete(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in VoiceCommand Delete : " + e.getMessage());

		} finally {

			session.close();
		}
	}

	/**
	 * Update Voice Command
	 */
	@Override
	public void update(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		VoiceCommandDTO existDto = findByCommandCode(dto.getCommandCode());

		// Check Duplicate Command Code
		if (existDto != null && existDto.getCommandId() != dto.getCommandId()) {

			throw new DuplicateRecordException("Command Code already exists");
		}

		try {

			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.saveOrUpdate(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in VoiceCommand Update : " + e.getMessage());

		} finally {

			session.close();
		}
	}

	/**
	 * Find By Primary Key
	 */
	@Override
	public VoiceCommandDTO findByPK(long pk) throws ApplicationException {

		Session session = null;

		VoiceCommandDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (VoiceCommandDTO) session.get(VoiceCommandDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in finding Voice Command by PK");

		} finally {

			session.close();
		}

		return dto;
	}

	/**
	 * Find By Command Code
	 */
	@Override
	public VoiceCommandDTO findByCommandCode(String commandCode) throws ApplicationException {

		Session session = null;

		VoiceCommandDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(VoiceCommandDTO.class);

			criteria.add(Restrictions.eq("commandCode", commandCode));

			List list = criteria.list();

			if (list.size() == 1) {

				dto = (VoiceCommandDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in finding Command Code : " + e.getMessage());

		} finally {

			session.close();
		}

		return dto;
	}

	/**
	 * List
	 */
	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * List With Pagination
	 */
	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(VoiceCommandDTO.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);

				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in VoiceCommand List");

		} finally {

			session.close();
		}

		return list;
	}

	/**
	 * Search
	 */
	@Override
	public List search(VoiceCommandDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	/**
	 * Search With Pagination
	 */
	@Override
	public List search(VoiceCommandDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;

		ArrayList<VoiceCommandDTO> list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(VoiceCommandDTO.class);

			if (dto != null) {

				if (dto.getCommandId() > 0) {

					criteria.add(Restrictions.eq("commandId", dto.getCommandId()));
				}

				if (dto.getCommandCode() != null && dto.getCommandCode().length() > 0) {

					criteria.add(Restrictions.like("commandCode", dto.getCommandCode() + "%"));
				}

				if (dto.getUserName() != null && dto.getUserName().length() > 0) {

					criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
				}

				if (dto.getCommandText() != null && dto.getCommandText().length() > 0) {

					criteria.add(Restrictions.like("commandText", dto.getCommandText() + "%"));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {

					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}
			}

			// Pagination

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);

				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<VoiceCommandDTO>) criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in VoiceCommand Search");

		} finally {

			session.close();
		}

		return list;
	}
}