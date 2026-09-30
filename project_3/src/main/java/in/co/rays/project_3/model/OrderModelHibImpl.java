
package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Order model
 *
 * @author Rajendra Singh
 *
 */
public class OrderModelHibImpl implements OrderModelInt {

	/**
	 * Add Order
	 */
	@Override
	public long add(OrderDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in Order Add " + e.getMessage());

		} finally {

			session.close();
		}

		return dto.getOrderId();
	}

	/**
	 * Delete Order
	 */
	@Override
	public void delete(OrderDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Order Delete " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Update Order
	 */
	@Override
	public void update(OrderDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in Order Update " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Find Order by Primary Key
	 */
	@Override
	public OrderDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		OrderDTO dto = null;

		try {

			session = HibDataSource.getSession();

			dto = (OrderDTO) session.get(OrderDTO.class, pk);

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting Order by pk");

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto;
	}

	/**
	 * List Orders
	 */
	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * List Orders with pagination
	 */
	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(OrderDTO.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in Orders list");

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	/**
	 * Search Order
	 */
	@Override
	public List search(OrderDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	/**
	 * Search Order with pagination
	 */
	@Override
	public List search(OrderDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;

		ArrayList<OrderDTO> list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(OrderDTO.class);

			if (dto != null) {

				if (dto.getOrderId() != null && dto.getOrderId() > 0) {

					criteria.add(Restrictions.eq("orderId", dto.getOrderId()));
				}

				if (dto.getOrderDate() != null) {

					criteria.add(Restrictions.eq("orderDate", dto.getOrderDate()));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {

					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}

				if (dto.getCustomerId() != null && dto.getCustomerId() > 0) {

					criteria.add(Restrictions.eq("customerId", dto.getCustomerId()));
				}
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<OrderDTO>) criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Order search");

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return list;
	}
}
