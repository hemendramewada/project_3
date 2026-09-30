package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ResultDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ResultModelHibImp implements ResultModelInt {

	/**
	 * Hibernate implementation of Result model
	 * 
	 * @author Rajendra Singh
	 */

	private static Logger log = Logger.getLogger(ResultModelHibImp.class.getName());

	@Override
	public long add(ResultDTO dto) throws DatabaseException, DuplicateRecordException {

		log.info("ResultModelHibImp add started");

		ResultDTO existDto = findByResultCode(dto.getResultCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Result Code already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			log.info("Result added successfully");

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DatabaseException("Exception in Result add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(ResultDTO dto) throws DatabaseException {

		log.info("ResultModelHibImp delete started");

		ResultDTO resultDto = findByPK(dto.getId());

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(resultDto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DatabaseException("Exception in Result delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(ResultDTO dto) throws DatabaseException, DuplicateRecordException {

		log.info("ResultModelHibImp update started");

		ResultDTO existDto = findByResultCode(dto.getResultCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Result Code already exists");
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
			throw new DatabaseException("Exception in Result update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public ResultDTO findByPK(long pk) throws DatabaseException {

		Session session = null;
		ResultDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (ResultDTO) session.get(ResultDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in getting Result by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public ResultDTO findByResultCode(String resultCode) throws DatabaseException {

		Session session = null;
		ResultDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResultDTO.class);
			criteria.add(Restrictions.eq("resultCode", resultCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ResultDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in findByResultCode " + e.getMessage());
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
			Criteria criteria = session.createCriteria(ResultDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Result list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(ResultDTO dto, int pageNo, int pageSize) throws DatabaseException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResultDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getResultCode() != null && dto.getResultCode().length() > 0) {
					criteria.add(Restrictions.like("resultCode", dto.getResultCode() + "%"));
				}

				if (dto.getStudentName() != null && dto.getStudentName().length() > 0) {
					criteria.add(Restrictions.like("studentName", dto.getStudentName() + "%"));
				}

				if (dto.getMarks() > 0) {
					criteria.add(Restrictions.eq("marks", dto.getMarks()));
				}

				if (dto.getGrade() != null && dto.getGrade().length() > 0) {
					criteria.add(Restrictions.eq("grade", dto.getGrade()));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Result search");
		} finally {
			session.close();
		}

		return list;
	}
}