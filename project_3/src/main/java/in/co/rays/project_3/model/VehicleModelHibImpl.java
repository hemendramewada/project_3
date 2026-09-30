
package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.VehicleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Vehicle model
 *
 * @author Rajendra Singh
 */
public class VehicleModelHibImpl implements VehicleModelInt {

	@Override
	public long add(VehicleDTO dto) throws ApplicationException, DuplicateRecordException {

		VehicleDTO existDto = findByVehicleName(dto.getVehicleName());

		if (existDto != null) {
			throw new DuplicateRecordException("Vehicle Name already exists");
		}

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();

			session.save(dto);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Vehicle Add " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto.getVechicleId();
	}

	@Override
	public void delete(VehicleDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Vehicle Delete " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void update(VehicleDTO dto) throws ApplicationException, DuplicateRecordException {

		VehicleDTO existDto = findByVehicleName(dto.getVehicleName());

		if (existDto != null && !existDto.getVechicleId().equals(dto.getVechicleId())) {

			throw new DuplicateRecordException("Vehicle Name already exists");
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

			throw new ApplicationException("Exception in Vehicle Update " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public VehicleDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		VehicleDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (VehicleDTO) session.get(VehicleDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Vehicle by PK " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto;
	}

	public VehicleDTO findByVehicleName(String vehicleName) throws ApplicationException {

		Session session = null;
		VehicleDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(VehicleDTO.class);

			criteria.add(Restrictions.eq("vehicleName", vehicleName));

			List list = criteria.list();

			if (list != null && list.size() == 1) {

				dto = (VehicleDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Vehicle by Name " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
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

			Criteria criteria = session.createCriteria(VehicleDTO.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Vehicle List " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List search(VehicleDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(VehicleDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<VehicleDTO> list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(VehicleDTO.class);

			if (dto != null) {

				if (dto.getVechicleId() != null && dto.getVechicleId() > 0) {

					criteria.add(Restrictions.eq("vechicleId", dto.getVechicleId()));
				}

				if (dto.getVehicleName() != null && dto.getVehicleName().length() > 0) {

					criteria.add(Restrictions.like("vehicleName", dto.getVehicleName() + "%"));
				}

				if (dto.getModel() != null && dto.getModel().length() > 0) {

					criteria.add(Restrictions.like("model", dto.getModel() + "%"));
				}

				if (dto.getColor() != null && dto.getColor().length() > 0) {

					criteria.add(Restrictions.like("color", dto.getColor() + "%"));
				}

				if (dto.getPrice() != null && dto.getPrice() > 0) {

					criteria.add(Restrictions.eq("price", dto.getPrice()));
				}
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<VehicleDTO>) criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Vehicle Search " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public long nextPK() throws DatabaseException {
		// TODO Auto-generated method stub
		return 0;
	}
}
