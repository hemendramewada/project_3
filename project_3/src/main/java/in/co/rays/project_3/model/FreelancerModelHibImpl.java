package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.FreelancerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class FreelancerModelHibImpl implements FreelancerModelInt {

	@Override
	public long add(FreelancerDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in Freelancer Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	@Override
	public void delete(FreelancerDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Freelancer Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void update(FreelancerDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {

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

			throw new ApplicationException("Exception in Freelancer Update " + e.getMessage());

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

			Criteria criteria = session.createCriteria(FreelancerDTO.class);

			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize);

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Freelancer List");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(FreelancerDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(FreelancerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(FreelancerDTO.class);

			if (dto != null) {

				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}

				if (dto.getSkill() != null && dto.getSkill().length() > 0) {
					criteria.add(Restrictions.like("skill", dto.getSkill() + "%"));
				}

				if (dto.getExperience() > 0) {
					criteria.add(Restrictions.eq("experience", dto.getExperience()));
				}

				if (dto.getHourlyRate() > 0) {
					criteria.add(Restrictions.eq("hourlyRate", dto.getHourlyRate()));
				}
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);

				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Freelancer Search");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public FreelancerDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		FreelancerDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (FreelancerDTO) session.get(FreelancerDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Find By PK");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public FreelancerDTO findByName(String name) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		FreelancerDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(FreelancerDTO.class);

			criteria.add(Restrictions.eq("name", name));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (FreelancerDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Find By Name");

		} finally {
			session.close();
		}

		return dto;
	}
}