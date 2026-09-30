package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.SmartLightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of SmartLight Model
 * 
 * @author Rajendra Singh
 *
 */
public class SmartLightModelJDBCImpl implements SmartLightModelInt {

	private static Logger log = Logger.getLogger(SmartLightModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {

		log.debug("SmartLight nextPK start");

		Connection con = null;

		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select max(LIGHT_ID) from ST_SMARTLIGHT");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				pk = rs.getLong(1);
			}

		} catch (Exception e) {

			log.error(e);

			throw new DatabaseException("Database Exception : " + e.getMessage());

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("SmartLight nextPK end");

		return pk + 1;
	}

	@Override
	public long add(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("SmartLight add start");

		Connection con = null;

		long pk = 0;

		SmartLightDTO existDto = findByLightCode(dto.getLightCode());

		if (existDto != null) {

			throw new DuplicateRecordException("Light Code already exists");
		}

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement("insert into ST_SMARTLIGHT "
					+ "(LIGHT_ID, LIGHT_CODE, ROOM_NAME, BRIGHTNESS_LEVEL, STATUS) " + "values(?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getLightCode());
			ps.setString(3, dto.getRoomName());
			ps.setInt(4, dto.getBrightnessLevel());
			ps.setString(5, dto.getStatus());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {

				con.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Add Rollback Exception : " + ex.getMessage());
			}

			throw new ApplicationException("Exception in adding SmartLight");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("SmartLight add end");

		return pk;
	}

	@Override
	public void delete(SmartLightDTO dto) throws ApplicationException {

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from ST_SMARTLIGHT where LIGHT_ID=?");

			ps.setLong(1, dto.getLightId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {

				con.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Delete Rollback Exception : " + ex.getMessage());
			}

			throw new ApplicationException("Exception in deleting SmartLight");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("SmartLight delete end");
	}

	@Override
	public void update(SmartLightDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		PreparedStatement ps = null;

		SmartLightDTO dtoExist = findByLightCode(dto.getLightCode());

		if (dtoExist != null && !(dtoExist.getLightId() == dto.getLightId())) {

			throw new DuplicateRecordException("Light Code already exists");
		}

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement("update ST_SMARTLIGHT set "
					+ "LIGHT_CODE=?, ROOM_NAME=?, BRIGHTNESS_LEVEL=?, STATUS=? " + "where LIGHT_ID=?");

			ps.setString(1, dto.getLightCode());
			ps.setString(2, dto.getRoomName());
			ps.setInt(3, dto.getBrightnessLevel());
			ps.setString(4, dto.getStatus());
			ps.setLong(5, dto.getLightId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {

				con.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Update Rollback Exception : " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating SmartLight");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("SmartLight update end");
	}

	@Override
	public SmartLightDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;
		SmartLightDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement("SELECT * FROM ST_SMARTLIGHT WHERE LIGHT_ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new SmartLightDTO();

				dto.setLightId(rs.getLong("LIGHT_ID"));
				dto.setLightCode(rs.getString("LIGHT_CODE"));
				dto.setRoomName(rs.getString("ROOM_NAME"));
				dto.setBrightnessLevel(rs.getInt("BRIGHTNESS_LEVEL"));
				dto.setStatus(rs.getString("STATUS"));

			}

			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

			log.error(e);

			throw new ApplicationException("Exception in finding SmartLight by PK");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	@Override
	public SmartLightDTO findByLightCode(String lightCode) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;
		SmartLightDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement("SELECT * FROM ST_SMARTLIGHT WHERE LIGHT_CODE=?");

			ps.setString(1, lightCode);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new SmartLightDTO();

				dto.setLightId(rs.getLong("LIGHT_ID"));
				dto.setLightCode(rs.getString("LIGHT_CODE"));
				dto.setRoomName(rs.getString("ROOM_NAME"));
				dto.setBrightnessLevel(rs.getInt("BRIGHTNESS_LEVEL"));
				dto.setStatus(rs.getString("STATUS"));

			}

			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

			log.error(e);

			throw new ApplicationException("Exception in finding SmartLight by Light Code");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;

		ArrayList list = new ArrayList();

		SmartLightDTO dto = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SMARTLIGHT WHERE 1=1");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new SmartLightDTO();

				dto.setLightId(rs.getLong("LIGHT_ID"));
				dto.setLightCode(rs.getString("LIGHT_CODE"));
				dto.setRoomName(rs.getString("ROOM_NAME"));
				dto.setBrightnessLevel(rs.getInt("BRIGHTNESS_LEVEL"));
				dto.setStatus(rs.getString("STATUS"));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error(e);

			throw new ApplicationException("Exception in getting SmartLight List");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public List search(SmartLightDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(SmartLightDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SMARTLIGHT WHERE 1=1");

		if (dto != null) {

			if (dto.getLightId() > 0) {

				sql.append(" AND LIGHT_ID = " + dto.getLightId());
			}

			if (dto.getLightCode() != null && dto.getLightCode().length() > 0) {

				sql.append(" AND LIGHT_CODE LIKE '" + dto.getLightCode() + "%'");
			}

			if (dto.getRoomName() != null && dto.getRoomName().length() > 0) {

				sql.append(" AND ROOM_NAME LIKE '" + dto.getRoomName() + "%'");
			}

			if (dto.getBrightnessLevel() != null) {

				sql.append(" AND BRIGHTNESS_LEVEL = " + dto.getBrightnessLevel());
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {

				sql.append(" AND STATUS LIKE '" + dto.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new SmartLightDTO();

				dto.setLightId(rs.getLong("LIGHT_ID"));
				dto.setLightCode(rs.getString("LIGHT_CODE"));
				dto.setRoomName(rs.getString("ROOM_NAME"));
				dto.setBrightnessLevel(rs.getInt("BRIGHTNESS_LEVEL"));
				dto.setStatus(rs.getString("STATUS"));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error(e);

			throw new ApplicationException("Exception in SmartLight Search");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	/*
	 * SmartLight module me login/password/email related methods (authenticate,
	 * registerUser, changePassword, forgetPassword, resetPassword, getRoles)
	 * required nahi hain, isliye remove kar diye gaye hain.
	 */

}