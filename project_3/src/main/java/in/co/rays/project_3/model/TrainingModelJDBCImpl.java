package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.TrainingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;


public class TrainingModelJDBCImpl implements TrainingModelInt {

	private static Logger log = Logger.getLogger(TrainingModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");
		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_TRAINING");
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

		log.debug("Model nextPK End");
		return pk + 1;
	}

	public long add(TrainingDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");
		Connection conn = null;
		long pk = 0;

		TrainingDTO duplicate = findByTrainingCode(dto.getTrainingCode());
		if (duplicate != null) {
			throw new DuplicateRecordException("Training Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO ST_TRAINING VALUES(?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getTrainingCode());
			pstmt.setString(3, dto.getTrainingName());
			pstmt.setString(4, dto.getTrainerName());
			pstmt.setDate(5, new java.sql.Date(dto.getTrainingDate().getTime()));
			pstmt.setString(6, dto.getTrainingStatus());
			pstmt.setString(7, dto.getCreatedBy());
			pstmt.setString(8, dto.getModifiedBy());
			pstmt.setTimestamp(9, dto.getCreatedDatetime());
			pstmt.setTimestamp(10, dto.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model add End");
		return pk;
	}

	public void delete(TrainingDTO dto) throws ApplicationException {

		log.debug("Model delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TRAINING WHERE ID=?");
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
			throw new ApplicationException("Exception in delete Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model delete End");
	}

	public void update(TrainingDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model update Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_TRAINING SET TRAINING_CODE=?,TRAINING_NAME=?,TRAINER_NAME=?,TRAINING_DATE=?,TRAINING_STATUS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, dto.getTrainingCode());
			pstmt.setString(2, dto.getTrainingName());
			pstmt.setString(3, dto.getTrainerName());
			pstmt.setDate(5, new java.sql.Date(dto.getTrainingDate().getTime()));
			pstmt.setString(5, dto.getTrainingStatus());
			pstmt.setString(6, dto.getCreatedBy());
			pstmt.setString(7, dto.getModifiedBy());
			pstmt.setTimestamp(8, dto.getCreatedDatetime());
			pstmt.setTimestamp(9, dto.getModifiedDatetime());
			pstmt.setLong(10, dto.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Training");
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
		StringBuffer sql = new StringBuffer("select * from ST_TRAINING");

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
				TrainingDTO dto = new TrainingDTO();
				dto.setId(rs.getLong(1));
				dto.setTrainingCode(rs.getString(2));
				dto.setTrainingName(rs.getString(3));
				dto.setTrainerName(rs.getString(4));
				dto.setTrainingDate(rs.getDate(5));
				dto.setTrainingStatus(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}
			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting list of Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

	public List search(TrainingDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(TrainingDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TRAINING WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getTrainingCode() != null && dto.getTrainingCode().length() > 0) {
				sql.append(" AND TRAINING_CODE like '" + dto.getTrainingCode() + "%'");
			}
			if (dto.getTrainingName() != null && dto.getTrainingName().length() > 0) {
				sql.append(" AND TRAINING_NAME like '" + dto.getTrainingName() + "%'");
			}
			if (dto.getTrainerName() != null && dto.getTrainerName().length() > 0) {
				sql.append(" AND TRAINER_NAME like '" + dto.getTrainerName() + "%'");
			}
			if (dto.getTrainingStatus() != null && dto.getTrainingStatus().length() > 0) {
				sql.append(" AND TRAINING_STATUS like '" + dto.getTrainingStatus() + "%'");
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
				dto = new TrainingDTO();
				dto.setId(rs.getLong(1));
				dto.setTrainingCode(rs.getString(2));
				dto.setTrainingName(rs.getString(3));
				dto.setTrainerName(rs.getString(4));
				dto.setTrainingDate(rs.getDate(5));
				dto.setTrainingStatus(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}
			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public TrainingDTO findByPK(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TRAINING WHERE ID=?");
		TrainingDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new TrainingDTO();
				dto.setId(rs.getLong(1));
				dto.setTrainingCode(rs.getString(2));
				dto.setTrainingName(rs.getString(3));
				dto.setTrainerName(rs.getString(4));
				dto.setTrainingDate(rs.getDate(5));
				dto.setTrainingStatus(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Training by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	public TrainingDTO findByTrainingCode(String code) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TRAINING WHERE TRAINING_CODE=?");
		TrainingDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new TrainingDTO();
				dto.setId(rs.getLong(1));
				dto.setTrainingCode(rs.getString(2));
				dto.setTrainingName(rs.getString(3));
				dto.setTrainerName(rs.getString(4));
				dto.setTrainingDate(rs.getDate(5));
				dto.setTrainingStatus(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Training by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}
}