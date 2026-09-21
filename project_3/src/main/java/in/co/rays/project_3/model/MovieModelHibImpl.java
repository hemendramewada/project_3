package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.MovieDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Movie model
 * @author Hemendra mewada
 *
 */
public class MovieModelHibImpl implements MovieModelInt {

	@Override
	public long add(MovieDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		long pk = 0;

		MovieDTO existDto = findByName(dto.getMovieName());
		if (existDto != null) {
			throw new DuplicateRecordException("Movie Name already exists");
		}

		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Movie Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	@Override
	public void delete(MovieDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Movie Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(MovieDTO dto) throws ApplicationException, DuplicateRecordException {

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
			throw new ApplicationException("Exception in Movie Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public MovieDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		MovieDTO dto = null;

		try {
			dto = (MovieDTO) session.get(MovieDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Movie by PK");
		} finally {
			session.close();
		}
		return dto;
	}

	@Override
	public MovieDTO findByName(String movieName) throws ApplicationException {

		Session session = HibDataSource.getSession();
		MovieDTO dto = null;

		try {
			Criteria criteria = session.createCriteria(MovieDTO.class);
			criteria.add(Restrictions.eq("movieName", movieName));
			List list = criteria.list();

			if (list.size() == 1) {
				dto = (MovieDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Movie by Name " + e.getMessage());
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
			Criteria criteria = session.createCriteria(MovieDTO.class);

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Movie list");
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(MovieDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(MovieDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MovieDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getMovieName() != null && dto.getMovieName().length() > 0) {
					criteria.add(Restrictions.like("movieName", dto.getMovieName() + "%"));
				}

				if (dto.getDirector() != null && dto.getDirector().length() > 0) {
					criteria.add(Restrictions.like("director", dto.getDirector() + "%"));
				}

				if (dto.getGenre() != null && dto.getGenre().length() > 0) {
					criteria.add(Restrictions.like("genre", dto.getGenre() + "%"));
				}

				if (dto.getLanguage() != null && dto.getLanguage().length() > 0) {
					criteria.add(Restrictions.like("language", dto.getLanguage() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Movie search");
		} finally {
			session.close();
		}

		return list;
	}
}