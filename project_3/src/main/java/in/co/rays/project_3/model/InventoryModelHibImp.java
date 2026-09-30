package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.InventoryDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class InventoryModelHibImp implements InventoryModelInt {

	/**
	 * Hibernate implementation of Inventory model
	 * 
	 * @author Rajendra Singh
	 */

	private static Logger log = Logger.getLogger(InventoryModelHibImp.class.getName());

	@Override
	public long add(InventoryDTO dto) throws DatabaseException, DuplicateRecordException {
		log.info("InventoryModelHibImp add started");

		InventoryDTO existDto = findByProduct(dto.getProduct());
		if (existDto != null) {
			throw new DuplicateRecordException("Product already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			log.info("Inventory added successfully");

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DatabaseException("Exception in Inventory add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(InventoryDTO dto) throws DatabaseException {
		log.info("InventoryModelHibImp delete started");

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
			throw new DatabaseException("Exception in Inventory delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(InventoryDTO dto) throws DatabaseException, DuplicateRecordException {
		log.info("InventoryModelHibImp update started");

		InventoryDTO existDto = findByProduct(dto.getProduct());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Product already exists");
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
			throw new DatabaseException("Exception in Inventory update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public InventoryDTO findByPK(long pk) throws DatabaseException {

		Session session = null;
		InventoryDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (InventoryDTO) session.get(InventoryDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in getting Inventory by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public InventoryDTO findByProduct(String product) throws DatabaseException {

		Session session = null;
		InventoryDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(InventoryDTO.class);
			criteria.add(Restrictions.eq("product", product));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (InventoryDTO) list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Exception in findByProduct " + e.getMessage());
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
			Criteria criteria = session.createCriteria(InventoryDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Inventory list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(InventoryDTO dto, int pageNo, int pageSize) throws DatabaseException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(InventoryDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getSupplierName() != null && dto.getSupplierName().length() > 0) {
					criteria.add(Restrictions.like("supplierName", dto.getSupplierName() + "%"));
				}

				if (dto.getProduct() != null && dto.getProduct().length() > 0) {
					criteria.add(Restrictions.like("product", dto.getProduct() + "%"));
				}

				if (dto.getQuantity() != null && dto.getQuantity() > 0) {
					criteria.add(Restrictions.eq("quantity", dto.getQuantity()));
				}

				if (dto.getDob() != null && dto.getDob().getDate() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}

			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Inventory search");
		} finally {
			session.close();
		}

		return list;
	}
}
