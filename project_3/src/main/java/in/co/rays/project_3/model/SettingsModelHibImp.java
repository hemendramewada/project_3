package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SettingsDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SettingsModelHibImp implements SettingsModelInt {

	@Override
	public long add(SettingsDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {

			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Settings Add " + e.getMessage());

		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(SettingsDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Settings Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void update(SettingsDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();

		} catch (HibernateException e) {

			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Settings Update " + e.getMessage());

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
			Criteria criteria = session.createCriteria(SettingsDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Settings list");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(SettingsDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(SettingsDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SettingsDTO.class);

			if (dto.getId() != null && dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getSettingName() != null && dto.getSettingName().length() > 0) {
				criteria.add(Restrictions.like("settingName", dto.getSettingName() + "%"));
			}

			if (dto.getSettingType() != null && dto.getSettingType().length() > 0) {
				criteria.add(Restrictions.like("settingType", dto.getSettingType() + "%"));
			}

			if (dto.getSettingStatus() != null && dto.getSettingStatus().length() > 0) {
				criteria.add(Restrictions.like("settingStatus", dto.getSettingStatus() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			e.printStackTrace();
			throw new ApplicationException("Exception in Settings search");

		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public SettingsDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		SettingsDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (SettingsDTO) session.get(SettingsDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Settings by PK");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public SettingsDTO findByName(String name) throws ApplicationException {

		Session session = null;
		SettingsDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SettingsDTO.class);
			criteria.add(Restrictions.eq("settingName", name));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (SettingsDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Settings by Name " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}
}
