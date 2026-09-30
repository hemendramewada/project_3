package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CustomerDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CustomerModelHibImpl implements CustomerModelInt {

	@Override
	public long add(CustomerDTO dto) throws DatabaseException, DuplicateRecordException {

		CustomerDTO existDto = findByContactNumber(dto.getContactNumber());
		if (existDto != null) {
			throw new DuplicateRecordException("Contact Number already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DatabaseException("Exception in Customer add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(CustomerDTO dto) throws DatabaseException {

		CustomerDTO cDto = findByPK(dto.getId());

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(cDto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DatabaseException("Exception in Customer delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(CustomerDTO dto) throws DatabaseException, DuplicateRecordException {

		CustomerDTO existDto = findByContactNumber(dto.getContactNumber());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Contact Number already exists");
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
			throw new DatabaseException("Exception in Customer update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public CustomerDTO findByPK(long pk) throws DatabaseException {

		Session session = null;
		CustomerDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (CustomerDTO) session.get(CustomerDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in getting Customer by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public CustomerDTO findByContactNumber(String contactNumber) throws DatabaseException {

		Session session = null;
		CustomerDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CustomerDTO.class);
			criteria.add(Restrictions.eq("contactNumber", contactNumber));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CustomerDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in findByContactNumber " + e.getMessage());
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws DatabaseException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws DatabaseException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CustomerDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Customer list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(CustomerDTO dto, int pageNo, int pageSize) throws DatabaseException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CustomerDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getClientName() != null && dto.getClientName().length() > 0) {
					criteria.add(Restrictions.like("clientName", dto.getClientName() + "%"));
				}

				if (dto.getLocation() != null && dto.getLocation().length() > 0) {
					criteria.add(Restrictions.like("location", dto.getLocation() + "%"));
				}

				if (dto.getContactNumber() != null && dto.getContactNumber().length() > 0) {
					criteria.add(Restrictions.eq("contactNumber", dto.getContactNumber()));
				}

				if (dto.getImportance() != null && dto.getImportance().length() > 0) {
					criteria.add(Restrictions.eq("importance", dto.getImportance()));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Customer search");
		} finally {
			session.close();
		}

		return list;
	}
}