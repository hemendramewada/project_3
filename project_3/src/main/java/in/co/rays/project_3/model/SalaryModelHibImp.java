package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SalaryDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SalaryModelHibImp implements SalaryModelInt {

	/**
	 * Hibernate implementation of Salary model
	 * 
	 * @author Rajendra Singh
	 */

	private static Logger log = Logger.getLogger(SalaryModelHibImp.class.getName());

	@Override
	public long add(SalaryDTO dto) throws DatabaseException, DuplicateRecordException {

	   	log.info("SalaryModelHibImp add started");

		SalaryDTO existDto = findBySalaryCode(dto.getSalaryCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Salary Code already exists");
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
			throw new DatabaseException("Exception in Salary add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(SalaryDTO dto) throws DatabaseException {

		log.info("SalaryModelHibImp delete started");

		SalaryDTO sDto = findByPK(dto.getId());

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(sDto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DatabaseException("Exception in Salary delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(SalaryDTO dto) throws DatabaseException, DuplicateRecordException {

		log.info("SalaryModelHibImp update started");

		SalaryDTO existDto = findBySalaryCode(dto.getSalaryCode());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Salary Code already exists");
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
			throw new DatabaseException("Exception in Salary update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public SalaryDTO findByPK(long pk) throws DatabaseException {

		Session session = null;
		SalaryDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (SalaryDTO) session.get(SalaryDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in getting Salary by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public SalaryDTO findBySalaryCode(String salaryCode) throws DatabaseException {

		Session session = null;
		SalaryDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SalaryDTO.class);
			criteria.add(Restrictions.eq("salaryCode", salaryCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (SalaryDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in findBySalaryCode " + e.getMessage());
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
			Criteria criteria = session.createCriteria(SalaryDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Salary list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(SalaryDTO dto, int pageNo, int pageSize) throws DatabaseException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SalaryDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getSalaryCode() != null && dto.getSalaryCode().length() > 0) {
					criteria.add(Restrictions.like("salaryCode", dto.getSalaryCode() + "%"));
				}

				if (dto.getEmployeeName() != null && dto.getEmployeeName().length() > 0) {
					criteria.add(Restrictions.like("employeeName", dto.getEmployeeName() + "%"));
				}

				if (dto.getSalaryStatus() != null && dto.getSalaryStatus().length() > 0) {
					criteria.add(Restrictions.like("salaryStatus", dto.getSalaryStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Salary search");
		} finally {
			session.close();
		}

		return list;
	}
}