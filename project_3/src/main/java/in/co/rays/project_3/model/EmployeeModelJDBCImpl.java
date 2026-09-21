package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC Implementation of Employee Model
 * 
 * @author Hemendra mewada
 */

public class EmployeeModelJDBCImpl implements EmployeeModelInt {

	private static Logger log = Logger.getLogger(EmployeeModelJDBCImpl.class);

	// ===================== NEXT PK =====================
	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_EMPLOYEE");
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

		return pk + 1;
	}

	// ===================== ADD =====================
	@Override
	public long add(EmployeeDTO dto)
			throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO ST_EMPLOYEE VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getEmployeeName());
			pstmt.setString(3, dto.getLastName());
			pstmt.setString(4, dto.getDepartment());
			pstmt.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(6, dto.getCreatedBy());
			pstmt.setString(7, dto.getModifiedBy());
			pstmt.setTimestamp(8, dto.getCreatedDatetime());
			pstmt.setTimestamp(9, dto.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in add Employee");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ===================== DELETE =====================
	@Override
	public void delete(EmployeeDTO dto) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("DELETE FROM ST_EMPLOYEE WHERE ID=?");

			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in delete Employee");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ===================== UPDATE =====================
	@Override
	public void update(EmployeeDTO dto)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_EMPLOYEE SET EMPLOYEE_NAME=?, LAST_NAME=?, DEPARTMENT=?, DOB=?, CREATED_BY=?, MODIFIED_BY=?, CREATED_DATETIME=?, MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, dto.getEmployeeName());
			pstmt.setString(2, dto.getLastName());
			pstmt.setString(3, dto.getDepartment());
			pstmt.setDate(4, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(5, dto.getCreatedBy());
			pstmt.setString(6, dto.getModifiedBy());
			pstmt.setTimestamp(7, dto.getCreatedDatetime());
			pstmt.setTimestamp(8, dto.getModifiedDatetime());
			pstmt.setLong(9, dto.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating Employee");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ===================== LIST =====================
	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize)
			throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_EMPLOYEE");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				EmployeeDTO dto = new EmployeeDTO();

				dto.setId(rs.getLong(1));
				dto.setEmployeeName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setDepartment(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Employee list");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ===================== SEARCH =====================
	@Override
	public List search(EmployeeDTO dto)
			throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(EmployeeDTO dto, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_EMPLOYEE WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}

			if (dto.getEmployeeName() != null
					&& dto.getEmployeeName().length() > 0) {
				sql.append(" AND EMPLOYEE_NAME LIKE '"
						+ dto.getEmployeeName() + "%'");
			}

			if (dto.getLastName() != null
					&& dto.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME LIKE '"
						+ dto.getLastName() + "%'");
			}

			if (dto.getDepartment() != null
					&& dto.getDepartment().length() > 0) {
				sql.append(" AND DEPARTMENT LIKE '"
						+ dto.getDepartment() + "%'");
			}

			if (dto.getDob() != null
					&& dto.getDob().getDate() > 0) {
				sql.append(" AND DOB='" + dto.getDob() + "'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				EmployeeDTO edto = new EmployeeDTO();

				edto.setId(rs.getLong(1));
				edto.setEmployeeName(rs.getString(2));
				edto.setLastName(rs.getString(3));
				edto.setDepartment(rs.getString(4));
				edto.setDob(rs.getDate(5));
				edto.setCreatedBy(rs.getString(6));
				edto.setModifiedBy(rs.getString(7));
				edto.setCreatedDatetime(rs.getTimestamp(8));
				edto.setModifiedDatetime(rs.getTimestamp(9));

				list.add(edto);
			}

			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Employee search");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ===================== FIND BY PK =====================
	@Override
	public EmployeeDTO findByPK(long pk)
			throws ApplicationException {

		EmployeeDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT * FROM ST_EMPLOYEE WHERE ID=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new EmployeeDTO();

				dto.setId(rs.getLong(1));
				dto.setEmployeeName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setDepartment(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Employee by PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return dto;
	}
}