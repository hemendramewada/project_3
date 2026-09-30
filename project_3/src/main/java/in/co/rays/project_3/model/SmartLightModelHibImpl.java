
package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SmartLightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate Implementation of SmartLight Model
 *
 * @author Rajendra Singh
 *
 */
public class SmartLightModelHibImpl implements SmartLightModelInt {

	@Override
	public long add(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException {

		SmartLightDTO existDto = findByLightCode(dto.getLightCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Light Code already exists");
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

			throw new ApplicationException("Exception in SmartLight Add : " + e.getMessage());

		} finally {

			session.close();
		}

		return dto.getLightId();
	}

	@Override
	public void delete(SmartLightDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in SmartLight Delete : " + e.getMessage());

		} finally {

			session.close();
		}
	}

	@Override
	public void update(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		SmartLightDTO existDto = findByLightCode(dto.getLightCode());

		if (existDto != null && existDto.getLightId() != dto.getLightId()) {

			throw new DuplicateRecordException("Light Code already exists");
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

			throw new ApplicationException("Exception in SmartLight Update : " + e.getMessage());

		} finally {

			session.close();
		}
	}

	@Override
	public SmartLightDTO findByPK(long pk) throws ApplicationException {

		Session session = null;

		SmartLightDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (SmartLightDTO) session.get(SmartLightDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting SmartLight by PK");

		} finally {

			session.close();
		}

		return dto;
	}

	@Override
	public SmartLightDTO findByLightCode(String lightCode) throws ApplicationException {

		Session session = null;

		SmartLightDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(SmartLightDTO.class);

			criteria.add(Restrictions.eq("lightCode", lightCode));

			List list = criteria.list();

			if (list.size() == 1) {

				dto = (SmartLightDTO) list.get(0);
			}

		} catch (HibernateException e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in getting SmartLight by Light Code");

		} finally {

			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(SmartLightDTO.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);

				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in SmartLight List");

		} finally {

			session.close();
		}

		return list;
	}

	@Override
	public List search(SmartLightDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(SmartLightDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(SmartLightDTO.class);

			if (dto != null) {

				if (dto.getLightId() > 0) {

					criteria.add(Restrictions.eq("lightId", dto.getLightId()));
				}

				if (dto.getLightCode() != null && dto.getLightCode().length() > 0) {

					criteria.add(Restrictions.like("lightCode", dto.getLightCode() + "%"));
				}

				if (dto.getRoomName() != null && dto.getRoomName().length() > 0) {

					criteria.add(Restrictions.like("roomName", dto.getRoomName() + "%"));
				}

				if (dto.getBrightnessLevel() != null) {

					criteria.add(Restrictions.eq("brightnessLevel", dto.getBrightnessLevel()));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {

					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);

				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in SmartLight Search");

		} finally {

			session.close();
		}

		return list;
	}
}