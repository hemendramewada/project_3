package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.DecorationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Decoration model
 */

public class DecorationModelJDBCImpl implements DecorationModelInt {

	private static Logger log = Logger.getLogger(DecorationModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {

		log.debug("Decoration pk start");

		Connection con = null;
		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_DECORATION");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);
			}

		} catch (Exception e) {

			log.error(e);
			throw new DatabaseException("Database Exception " + e);

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Decoration pk end");

		return pk + 1;
	}

	public long add(DecorationDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");

		Connection conn = null;
		long pk = 0;

		DecorationDTO duplicateTheme = findByTheme(dto.getTheme());

		if (duplicateTheme != null) {
			throw new DuplicateRecordException("Theme already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO ST_DECORATION VALUES(?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getTheme());
			pstmt.setString(3, dto.getVendorName());
			pstmt.setString(4, dto.getCost());
			pstmt.setString(5, dto.getCreatedBy());
			pstmt.setString(6, dto.getModifiedBy());
			pstmt.setTimestamp(7, dto.getCreatedDatetime());
			pstmt.setTimestamp(8, dto.getModifiedDatetime());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in add Decoration");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void delete(DecorationDTO dto) throws ApplicationException {

		log.debug("Model delete Started");

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_DECORATION WHERE ID=?");

			pstmt.setLong(1, dto.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in delete Decoration");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model delete End");
	}

	public void update(DecorationDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model update Started");

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_DECORATION SET THEME=?,VENDOR_NAME=?,COST=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, dto.getTheme());
			pstmt.setString(2, dto.getVendorName());
			pstmt.setString(3, dto.getCost());
			pstmt.setString(4, dto.getCreatedBy());
			pstmt.setString(5, dto.getModifiedBy());
			pstmt.setTimestamp(6, dto.getCreatedDatetime());
			pstmt.setTimestamp(7, dto.getModifiedDatetime());
			pstmt.setLong(8, dto.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating Decoration");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model update End");
	}

	public List list() throws ApplicationException {

		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from ST_DECORATION");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				DecorationDTO dto = new DecorationDTO();

				dto.setId(rs.getLong(1));
				dto.setTheme(rs.getString(2));
				dto.setVendorName(rs.getString(3));
				dto.setCost(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting list of Decoration");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");

		return list;
	}

	public List search(DecorationDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	public List search(DecorationDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_DECORATION WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}

			if (dto.getTheme() != null && dto.getTheme().length() > 0) {
				sql.append(" AND THEME like '" + dto.getTheme() + "%'");
			}

			if (dto.getVendorName() != null && dto.getVendorName().length() > 0) {
				sql.append(" AND VENDOR_NAME like '" + dto.getVendorName() + "%'");
			}

			if (dto.getCost() != null && dto.getCost().length() > 0) {
				sql.append(" AND COST like '" + dto.getCost() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList list = new ArrayList();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new DecorationDTO();

				dto.setId(rs.getLong(1));
				dto.setTheme(rs.getString(2));
				dto.setVendorName(rs.getString(3));
				dto.setCost(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in search Decoration");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");

		return list;
	}

	public DecorationDTO findByPK(long pk) throws ApplicationException {

		log.debug("Model findByPK Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_DECORATION WHERE ID=?");

		DecorationDTO dto = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new DecorationDTO();

				dto.setId(rs.getLong(1));
				dto.setTheme(rs.getString(2));
				dto.setVendorName(rs.getString(3));
				dto.setCost(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting Decoration by PK");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model findByPK End");

		return dto;
	}

	public DecorationDTO findByTheme(String theme) throws ApplicationException {

		log.debug("Model findBy Theme Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_DECORATION WHERE THEME=?");

		DecorationDTO dto = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, theme);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new DecorationDTO();

				dto.setId(rs.getLong(1));
				dto.setTheme(rs.getString(2));
				dto.setVendorName(rs.getString(3));
				dto.setCost(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting Decoration by Theme");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model findBy Theme End");

		return dto;
	}
}