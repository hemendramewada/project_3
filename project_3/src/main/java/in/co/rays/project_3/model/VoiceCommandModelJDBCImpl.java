package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.VoiceCommandDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC Implementation of VoiceCommand Model
 *
 * @author Rajendra Singh
 *
 */
public class VoiceCommandModelJDBCImpl implements VoiceCommandModelInt {

	private static Logger log = Logger.getLogger(VoiceCommandModelJDBCImpl.class);

	/**
	 * Get Next Primary Key
	 */
	public long nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		Connection con = null;

		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT MAX(COMMAND_ID) FROM ST_VOICE_COMMAND");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				pk = rs.getLong(1);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {

			log.error(e);

			throw new DatabaseException("Database Exception : " + e);

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model nextPK End");

		return pk + 1;
	}

	/**
	 * Add Voice Command
	 */
	@Override
	public long add(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		long pk = 0;

		VoiceCommandDTO existDto = findByCommandCode(dto.getCommandCode());

		if (existDto != null) {

			throw new DuplicateRecordException("Command Code already exists");
		}

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement(

					"INSERT INTO ST_VOICE_COMMAND " + "(COMMAND_ID,COMMAND_CODE,USER_NAME,COMMAND_TEXT,STATUS)"
							+ " VALUES(?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getCommandCode());
			ps.setString(3, dto.getUserName());
			ps.setString(4, dto.getCommandText());
			ps.setString(5, dto.getStatus());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			try {

				con.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Rollback Exception : " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Add Voice Command");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	/**
	 * Delete Voice Command
	 */
	@Override
	public void delete(VoiceCommandDTO dto) throws ApplicationException {

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_VOICE_COMMAND WHERE COMMAND_ID=?");

			ps.setLong(1, dto.getCommandId());

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

			throw new ApplicationException("Exception in Delete Voice Command");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model delete End");
	}

	/**
	 * Update Voice Command
	 */
	@Override
	public void update(VoiceCommandDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		PreparedStatement ps = null;

		VoiceCommandDTO existDto = findByCommandCode(dto.getCommandCode());

		// Duplicate Command Code Check
		if (existDto != null && existDto.getCommandId() != dto.getCommandId()) {

			throw new DuplicateRecordException("Command Code already exists");
		}

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			ps = con.prepareStatement(

					"UPDATE ST_VOICE_COMMAND " + "SET COMMAND_CODE=?, USER_NAME=?, " + "COMMAND_TEXT=?, STATUS=? "
							+ "WHERE COMMAND_ID=?");

			ps.setString(1, dto.getCommandCode());
			ps.setString(2, dto.getUserName());
			ps.setString(3, dto.getCommandText());
			ps.setString(4, dto.getStatus());
			ps.setLong(5, dto.getCommandId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {

				con.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Update Rollback Exception : " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Update Voice Command");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model update End");
	}

	/**
	 * Find Voice Command By Primary Key
	 */
	@Override
	public VoiceCommandDTO findByPK(long pk) throws ApplicationException {

		log.debug("Model findByPK Started");

		Connection con = null;
		VoiceCommandDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_VOICE_COMMAND WHERE COMMAND_ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new VoiceCommandDTO();

				dto.setCommandId(rs.getLong("COMMAND_ID"));
				dto.setCommandCode(rs.getString("COMMAND_CODE"));
				dto.setUserName(rs.getString("USER_NAME"));
				dto.setCommandText(rs.getString("COMMAND_TEXT"));
				dto.setStatus(rs.getString("STATUS"));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			throw new ApplicationException("Exception in Find By PK");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model findByPK End");

		return dto;
	}

	/**
	 * Find Voice Command By Command Code
	 */
	@Override
	public VoiceCommandDTO findByCommandCode(String commandCode) throws ApplicationException {

		log.debug("Model findByCommandCode Started");

		Connection con = null;

		VoiceCommandDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_VOICE_COMMAND WHERE COMMAND_CODE=?");

			ps.setString(1, commandCode);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new VoiceCommandDTO();

				dto.setCommandId(rs.getLong("COMMAND_ID"));
				dto.setCommandCode(rs.getString("COMMAND_CODE"));
				dto.setUserName(rs.getString("USER_NAME"));
				dto.setCommandText(rs.getString("COMMAND_TEXT"));
				dto.setStatus(rs.getString("STATUS"));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			throw new ApplicationException("Exception in Find By Command Code");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model findByCommandCode End");

		return dto;
	}

	/**
	 * List
	 */
	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	/**
	 * List With Pagination
	 */
	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");

		Connection con = null;

		List list = new java.util.ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_VOICE_COMMAND");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				VoiceCommandDTO dto = new VoiceCommandDTO();

				dto.setCommandId(rs.getLong("COMMAND_ID"));
				dto.setCommandCode(rs.getString("COMMAND_CODE"));
				dto.setUserName(rs.getString("USER_NAME"));
				dto.setCommandText(rs.getString("COMMAND_TEXT"));
				dto.setStatus(rs.getString("STATUS"));

				list.add(dto);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			throw new ApplicationException("Exception in List");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model list End");

		return list;
	}

	/**
	 * Search Voice Command
	 */
	@Override
	public List search(VoiceCommandDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	/**
	 * Search Voice Command With Pagination
	 */
	@Override
	public List search(VoiceCommandDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");

		Connection con = null;

		List list = new java.util.ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_VOICE_COMMAND WHERE 1=1");

		if (dto != null) {

			if (dto.getCommandId() > 0) {
				sql.append(" AND COMMAND_ID = " + dto.getCommandId());
			}

			if (dto.getCommandCode() != null && dto.getCommandCode().length() > 0) {

				sql.append(" AND COMMAND_CODE LIKE '" + dto.getCommandCode() + "%'");
			}

			if (dto.getUserName() != null && dto.getUserName().length() > 0) {

				sql.append(" AND USER_NAME LIKE '" + dto.getUserName() + "%'");
			}

			if (dto.getCommandText() != null && dto.getCommandText().length() > 0) {

				sql.append(" AND COMMAND_TEXT LIKE '" + dto.getCommandText() + "%'");
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

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				VoiceCommandDTO commandDto = new VoiceCommandDTO();

				commandDto.setCommandId(rs.getLong("COMMAND_ID"));

				commandDto.setCommandCode(rs.getString("COMMAND_CODE"));

				commandDto.setUserName(rs.getString("USER_NAME"));

				commandDto.setCommandText(rs.getString("COMMAND_TEXT"));

				commandDto.setStatus(rs.getString("STATUS"));

				list.add(commandDto);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			throw new ApplicationException("Exception in Search Voice Command");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return list;
	}

}