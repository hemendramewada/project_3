package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BugTrackerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate Implementation of BugTracker Model
 * 
 * @author Rajendra Singh
 *
 */
public class BugTrackerModelHibImpl implements BugTrackerModelInt {

	@Override
	public long add(BugTrackerDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();

			session.save(dto);

			pk = dto.getId();

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in BugTracker Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	@Override
	public void delete(BugTrackerDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in BugTracker Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void update(BugTrackerDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in BugTracker Update " + e.getMessage());

		} finally {
			session.close();
		}
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

			Criteria criteria = session.createCriteria(BugTrackerDTO.class);

			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize);

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in BugTracker List");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(BugTrackerDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(BugTrackerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(BugTrackerDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getBugId() > 0) {
					criteria.add(Restrictions.eq("bugId", dto.getBugId()));
				}

				if (dto.getTitle() != null && dto.getTitle().length() > 0) {

					criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
				}

				if (dto.getSeverity() != null && dto.getSeverity().length() > 0) {

					criteria.add(Restrictions.like("severity", dto.getSeverity() + "%"));
				}

				if (dto.getAssignedTo() != null && dto.getAssignedTo().length() > 0) {

					criteria.add(Restrictions.like("assignedTo", dto.getAssignedTo() + "%"));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {

					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);

				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in BugTracker Search");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public BugTrackerDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		BugTrackerDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (BugTrackerDTO) session.get(BugTrackerDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Find By PK");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public BugTrackerDTO findByBugId(int bugId) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		BugTrackerDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(BugTrackerDTO.class);

			criteria.add(Restrictions.eq("bugId", bugId));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (BugTrackerDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in findByBugId");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public BugTrackerDTO findByTitle(String title) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		BugTrackerDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(BugTrackerDTO.class);

			criteria.add(Restrictions.eq("title", title));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (BugTrackerDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in findByTitle");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public BugTrackerDTO findByAssignedTo(String assignedTo) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		BugTrackerDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(BugTrackerDTO.class);

			criteria.add(Restrictions.eq("assignedTo", assignedTo));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (BugTrackerDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in findByAssignedTo");

		} finally {
			session.close();
		}

		return dto;
	}
}