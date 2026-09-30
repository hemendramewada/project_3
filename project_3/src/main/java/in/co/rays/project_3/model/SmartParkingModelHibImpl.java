package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SmartParkingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of SmartParking model
 * 
 * @author Rajendra Singh
 *
 */
public class SmartParkingModelHibImpl implements SmartParkingModelInt {

	public long add(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException {

		SmartParkingDTO existDto = findByVehicalNumber(dto.getVehicalNumber());

		if (existDto != null) {
			throw new DuplicateRecordException("Vehical Number already exist");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			session.save(dto);

			tx.commit();

		} catch (org.hibernate.exception.JDBCConnectionException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new DatabaseException("Database connection was lost. Please try again.");

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in SmartParking Add " + e.getMessage());

		} finally {

			session.close();
		}

		return dto.getSlotId();
	}

	@Override
	public void delete(SmartParkingDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in SmartParking Delete " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void update(SmartParkingDTO dto) throws ApplicationException, DuplicateRecordException {

		SmartParkingDTO existDto = findByVehicalNumber(dto.getVehicalNumber());

		if (existDto != null && existDto.getSlotId() != dto.getSlotId()) {

			throw new DuplicateRecordException("Vehical Number already exist");
		}

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.saveOrUpdate(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in SmartParking Update " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public SmartParkingDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		SmartParkingDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (SmartParkingDTO) session.get(SmartParkingDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting SmartParking by pk " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto;
	}

	@Override
	public SmartParkingDTO findByVehicalNumber(String vehicalNumber) throws ApplicationException {

		Session session = null;
		SmartParkingDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(SmartParkingDTO.class);

			criteria.add(Restrictions.eq("vehicalNumber", vehicalNumber));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (SmartParkingDTO) list.get(0);
			}

		} catch (HibernateException e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in getting SmartParking by Vehical Number " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
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

			Criteria criteria = session.createCriteria(SmartParkingDTO.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in SmartParking list " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List search(SmartParkingDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(SmartParkingDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<SmartParkingDTO> list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(SmartParkingDTO.class);

			if (dto != null) {

				if (dto.getSlotId() > 0) {

					criteria.add(Restrictions.eq("slotId", dto.getSlotId()));
				}

				if (dto.getVehicalNumber() != null && dto.getVehicalNumber().length() > 0) {

					criteria.add(Restrictions.like("vehicalNumber", dto.getVehicalNumber() + "%"));
				}

				if (dto.getVehicalType() != null && dto.getVehicalType().length() > 0) {

					criteria.add(Restrictions.like("vehicalType", dto.getVehicalType() + "%"));
				}
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<SmartParkingDTO>) criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in SmartParking search " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public void parkVehical(SmartParkingDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			dto.setOccupied(true);

			session.update(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in SmartParking Park Vehical " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void releaseSlot(SmartParkingDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			dto.setOccupied(false);

			session.update(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in SmartParking Release Slot " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

}