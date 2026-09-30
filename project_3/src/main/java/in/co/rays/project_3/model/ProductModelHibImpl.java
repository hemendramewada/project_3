
package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Product model
 *
 * @author Rajendra Singh
 *
 */
public class ProductModelHibImpl implements ProductModelInt {

	@Override
	public long add(ProductDTO dto) throws ApplicationException, DuplicateRecordException {

		// Check duplicate Product Name
		ProductDTO existDto = findByProductName(dto.getProductName());

		if (existDto != null) {
			throw new DuplicateRecordException("Product Name already exist");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			session.save(dto);

			tx.commit();

		} catch (org.hibernate.exception.JDBCConnectionException e) {

			e.printStackTrace();

			throw new DatabaseException("Database connection was lost. Please try again.");

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Product Add " + e.getMessage());

		} finally {

			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(ProductDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.delete(dto);

			tx.commit();

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Product Delete " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void update(ProductDTO dto) throws ApplicationException, DuplicateRecordException {

		ProductDTO existDto = findByProductName(dto.getProductName());

		// Check if updated Product Name already exists
		if (existDto != null && existDto.getId() != dto.getId()) {

			throw new DuplicateRecordException("Product Name is already exist");
		}

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

			throw new ApplicationException("Exception in Product update " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public ProductDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		ProductDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (ProductDTO) session.get(ProductDTO.class, pk);

		} catch (HibernateException e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in getting Product by pk");

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto;
	}

	/**
	 * Find Product by Product Name
	 */
	public ProductDTO findByProductName(String productName) throws ApplicationException {

		Session session = null;
		ProductDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ProductDTO.class);

			criteria.add(Restrictions.eq("productName", productName));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (ProductDTO) list.get(0);
			}

		} catch (HibernateException e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in getting Product by Product Name " + e.getMessage());

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

			Criteria criteria = session.createCriteria(ProductDTO.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in Product list");

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List search(ProductDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(ProductDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<ProductDTO> list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ProductDTO.class);

			if (dto != null) {

				// Product Name
				if (dto.getProductName() != null && dto.getProductName().length() > 0) {

					criteria.add(Restrictions.like("productName", dto.getProductName() + "%"));
				}

				// Product Amount
				if (dto.getProductAmmount() != null && dto.getProductAmmount().length() > 0) {

					criteria.add(Restrictions.like("productAmmount", dto.getProductAmmount() + "%"));
				}

				// Purchase Date
				if (dto.getPurchaseDate() != null && dto.getPurchaseDate().getTime() > 0) {

					criteria.add(Restrictions.eq("purchaseDate", dto.getPurchaseDate()));
				}

				// Product Category
				if (dto.getProductCategory() != null && dto.getProductCategory().length() > 0) {

					criteria.add(Restrictions.like("productCategory", dto.getProductCategory() + "%"));
				}
			}

			// Pagination
			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<ProductDTO>) criteria.list();

		} catch (HibernateException e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in Product search");

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public ProductDTO fingByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}
