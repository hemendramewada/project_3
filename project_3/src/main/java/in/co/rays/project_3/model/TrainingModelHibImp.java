package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.TrainingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class TrainingModelHibImp implements TrainingModelInt {

	public long add(TrainingDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		TrainingDTO duplicateTraining = findByTrainingCode(dto.getTrainingCode());

		// Check duplicate
		if (duplicateTraining != null && duplicateTraining.getTrainingCode() != null) {
			throw new DuplicateRecordException("Training Code already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Training Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(TrainingDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Training Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(TrainingDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		TrainingDTO duplicateTraining = findByTrainingCode(dto.getTrainingCode());

		if (duplicateTraining != null && duplicateTraining.getId() != dto.getId()) {
			throw new DuplicateRecordException("Training Code already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Training Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TrainingDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Training list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(TrainingDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(TrainingDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TrainingDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getTrainingCode() != null && dto.getTrainingCode().length() > 0) {
					criteria.add(Restrictions.like("trainingCode", dto.getTrainingCode() + "%"));
				}

				if (dto.getTrainingName() != null && dto.getTrainingName().length() > 0) {
					criteria.add(Restrictions.like("trainingName", dto.getTrainingName() + "%"));
				}

				if (dto.getTrainerName() != null && dto.getTrainerName().length() > 0) {
					criteria.add(Restrictions.like("trainerName", dto.getTrainerName() + "%"));
				}

				if (dto.getTrainingStatus() != null && dto.getTrainingStatus().length() > 0) {
					criteria.add(Restrictions.like("trainingStatus", dto.getTrainingStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Training search");
		} finally {
			session.close();
		}
		return list;
	}

	public TrainingDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		TrainingDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (TrainingDTO) session.get(TrainingDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Training by PK");
		} finally {
			session.close();
		}
		return dto;
	}

	public TrainingDTO findByTrainingCode(String trainingCode) throws ApplicationException {

		Session session = null;
		TrainingDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TrainingDTO.class);
			criteria.add(Restrictions.eq("trainingCode", trainingCode));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (TrainingDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Training by Code " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}
}