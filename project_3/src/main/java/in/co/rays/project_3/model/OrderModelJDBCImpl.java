
package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implementation of Order model
 *
 * @author Rajendra Singh
 */
public class OrderModelJDBCImpl implements OrderModelInt {

	private static Logger log = Logger.getLogger(OrderModelJDBCImpl.class);

	/**
	 * Generate next primary key
	 */
	public long nextPK() throws DatabaseException {

		log.debug("Order nextPK start");

		Connection con = null;
		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select max(order_id) from st_order");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);
			}

		} catch (Exception e) {

			log.error(e);
			throw new DatabaseException("Database Exception : " + e);

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Order nextPK end");

		return pk + 1;
	}

	/**
	 * Add Order
	 */
	@Override
	public long add(OrderDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Order add start");

		Connection con = null;
		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement("insert into st_order values(?,?,?,?)");

			ps.setLong(1, pk);
			ps.setDate(2, new java.sql.Date(dto.getOrderDate().getTime()));
			ps.setString(3, dto.getStatus());
			ps.setLong(4, dto.getCustomerId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error(e);

			try {
				con.rollback();
			} catch (Exception ex) {
				log.error(ex);
			}

			throw new ApplicationException("Exception : Exception in add Order");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Order add end");

		return pk;
	}

	/**
	 * Delete Order
	 */
	@Override
	public void delete(OrderDTO dto) throws ApplicationException {

		log.debug("Order delete start");

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from st_order where order_id=?");

			ps.setLong(1, dto.getOrderId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			try {
				con.rollback();
			} catch (Exception ex) {
				log.error(ex);
			}

			throw new ApplicationException("Exception : Exception in delete Order");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Order delete end");
	}

	/**
	 * Update Order
	 */
	@Override
	public void update(OrderDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Order update start");

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(
					"update st_order set ORDER_DATE=?, STATUS=?, CUSTOMER_ID=? " + "where ORDER_ID=?");

			ps.setDate(1, new java.sql.Date(dto.getOrderDate().getTime()));

			ps.setString(2, dto.getStatus());

			ps.setLong(3, dto.getCustomerId());

			ps.setLong(4, dto.getOrderId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception in updating Order");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Order update end");
	}

	/**
	 * Find Order by Primary Key
	 */
	@Override
	public OrderDTO findByPK(long pk) throws ApplicationException {

		log.debug("Order findByPK start");

		Connection con = null;
		PreparedStatement ps = null;
		OrderDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement("select * from st_order where order_id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new OrderDTO();

				dto.setOrderId(rs.getLong(1));
				dto.setOrderDate(rs.getDate(2));
				dto.setStatus(rs.getString(3));
				dto.setCustomerId(rs.getLong(4));
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting Order by pk");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Order findByPK end");

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

		log.debug("Order list start");

		Connection con = null;
		PreparedStatement ps = null;

		ArrayList array = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_order where 1=1");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				OrderDTO dto = new OrderDTO();

				dto.setOrderId(rs.getLong(1));
				dto.setOrderDate(rs.getDate(2));
				dto.setStatus(rs.getString(3));
				dto.setCustomerId(rs.getLong(4));

				array.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting list of Orders");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Order list end");

		return array;
	}

	/**
	 * Search Orders
	 */
	@Override
	public List search(OrderDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Order search start");

		Connection con = null;
		PreparedStatement ps = null;

		ArrayList array = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_order where 1=1");

		if (dto != null) {

			if (dto.getOrderId() != null && dto.getOrderId() > 0) {

				sql.append(" AND ORDER_ID = " + dto.getOrderId());
			}

			if (dto.getOrderDate() != null) {

				sql.append(" AND ORDER_DATE = '" + new java.sql.Date(dto.getOrderDate().getTime()) + "'");
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {

				sql.append(" AND STATUS like '" + dto.getStatus() + "%'");
			}

			if (dto.getCustomerId() != null && dto.getCustomerId() > 0) {

				sql.append(" AND CUSTOMER_ID = " + dto.getCustomerId());
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				OrderDTO orderDTO = new OrderDTO();

				orderDTO.setOrderId(rs.getLong(1));
				orderDTO.setOrderDate(rs.getDate(2));
				orderDTO.setStatus(rs.getString(3));
				orderDTO.setCustomerId(rs.getLong(4));

				array.add(orderDTO);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in search Order");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Order search end");

		return array;
	}

	/**
	 * Search Orders without pagination
	 */
	@Override
	public List search(OrderDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}
}
