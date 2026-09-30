package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Podcast Model
 * 
 * @author Rajendra Singh
 *
 */
public class PodcastModelHibImpl implements PodcastModelInt {

	@Override
	public long add(PodcastDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in Podcast Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	@Override
	public void delete(PodcastDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Podcast Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void update(PodcastDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {

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

			throw new ApplicationException("Exception in Podcast Update " + e.getMessage());

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

			Criteria criteria = session.createCriteria(PodcastDTO.class);

			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize);

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Podcast List");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(PodcastDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(PodcastDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PodcastDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getTitle() != null && dto.getTitle().length() > 0) {

					criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
				}

				if (dto.getHostName() != null && dto.getHostName().length() > 0) {

					criteria.add(Restrictions.like("hostName", dto.getHostName() + "%"));
				}

				if (dto.getDuration() != null && dto.getDuration().length() > 0) {

					criteria.add(Restrictions.like("duration", dto.getDuration() + "%"));
				}

				if (dto.getCategory() != null && dto.getCategory().length() > 0) {

					criteria.add(Restrictions.like("category", dto.getCategory() + "%"));
				}
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Podcast Search");

		} finally {

			session.close();
		}

		return list;
	}

	@Override
	public PodcastDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		PodcastDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (PodcastDTO) session.get(PodcastDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Podcast by PK");

		} finally {

			session.close();
		}

		return dto;
	}

	@Override
	public PodcastDTO findByTitle(String title) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		PodcastDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PodcastDTO.class);

			criteria.add(Restrictions.eq("title", title));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (PodcastDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in findByTitle");

		} finally {

			session.close();
		}

		return dto;
	}

	@Override
	public PodcastDTO findByHostName(String hostName) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		PodcastDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PodcastDTO.class);

			criteria.add(Restrictions.eq("hostName", hostName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (PodcastDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in findByHostName");

		} finally {

			session.close();
		}

		return dto;
	}

	@Override
	public PodcastDTO findByCategory(String category) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		PodcastDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PodcastDTO.class);

			criteria.add(Restrictions.eq("category", category));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (PodcastDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in findByCategory");

		} finally {

			session.close();
		}

		return dto;
	}
}