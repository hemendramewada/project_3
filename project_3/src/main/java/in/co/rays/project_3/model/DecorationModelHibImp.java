package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DecorationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Decoration model
 */

public class DecorationModelHibImp implements DecorationModelInt {

	public long add(DecorationDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		long pk = 0;

		try {

			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Decoration Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	public void delete(DecorationDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Decoration Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public void update(DecorationDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Decoration Update " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public DecorationDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		DecorationDTO dto = null;

		try {

			dto = (DecorationDTO) session.get(DecorationDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting Decoration by PK");

		} finally {
			session.close();
		}

		return dto;
	}

	public DecorationDTO findByTheme(String theme) throws ApplicationException {

		Session session = HibDataSource.getSession();
		DecorationDTO dto = null;

		try {

			Criteria criteria = session.createCriteria(DecorationDTO.class);
			criteria.add(Restrictions.eq("theme", theme));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (DecorationDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Decoration by Theme " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	public List list() throws ApplicationException {

		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DecorationDTO.class);

			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in Decoration list");

		} finally {
			session.close();
		}

		return list;
	}

	public List search(DecorationDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	public List search(DecorationDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DecorationDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getTheme() != null && dto.getTheme().length() > 0) {
					criteria.add(Restrictions.like("theme", dto.getTheme() + "%"));
				}

				if (dto.getVendorName() != null && dto.getVendorName().length() > 0) {
					criteria.add(Restrictions.like("vendorName", dto.getVendorName() + "%"));
				}

				if (dto.getCost() != null && dto.getCost().length() > 0) {
					criteria.add(Restrictions.like("cost", dto.getCost() + "%"));
				}
			}

			if (pageSize > 0) {

				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Decoration search");

		} finally {
			session.close();
		}

		return list;
	}
}