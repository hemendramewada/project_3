package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BugTrackerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC Implementation of BugTracker Model
 * 
 * @author Rajendra Singh
 *
 */
public class BugTrackerModelJDBCImpl implements BugTrackerModelInt {

	private static Logger log = Logger.getLogger(BugTrackerModelJDBCImpl.class);

	Connection con = null;

	public long nextPK() throws DatabaseException {

		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_BUGTRACKER");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);
			}

		} catch (Exception e) {

			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting PK");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk + 1;
	}

	@Override
	public long add(BugTrackerDTO dto) throws ApplicationException {

		long pk = 0;

		try {

			pk = nextPK();

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("INSERT INTO ST_BUGTRACKER VALUES(?,?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setInt(2, dto.getBugId());
			ps.setString(3, dto.getTitle());
			ps.setString(4, dto.getSeverity());
			ps.setString(5, dto.getAssignedTo());
			ps.setString(6, dto.getStatus());
			ps.setString(7, dto.getCreatedBy());
			ps.setString(8, dto.getModifiedBy());
			ps.setTimestamp(9, dto.getCreatedDatetime());
			ps.setTimestamp(10, dto.getModifiedDatetime());
			ps.setLong(11, dto.getId());

			ps.executeUpdate();

			ps.close();

			con.commit();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Add BugTracker");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	@Override
	public void delete(BugTrackerDTO dto) throws ApplicationException {

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_BUGTRACKER WHERE ID=?");

			ps.setLong(1, dto.getId());

			ps.executeUpdate();

			ps.close();

			con.commit();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete Rollback Exception");
			}

			throw new ApplicationException("Exception in Delete BugTracker");

		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	@Override
	public void update(BugTrackerDTO dto) throws DatabaseException, ApplicationException {

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"UPDATE ST_BUGTRACKER SET BUG_ID=?,TITLE=?,SEVERITY=?,ASSIGNED_TO=?,STATUS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			ps.setInt(1, dto.getBugId());
			ps.setString(2, dto.getTitle());
			ps.setString(3, dto.getSeverity());
			ps.setString(4, dto.getAssignedTo());
			ps.setString(5, dto.getStatus());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());
			ps.setLong(10, dto.getId());

			ps.executeUpdate();

			ps.close();

			con.commit();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update Rollback Exception");
			}

			throw new ApplicationException("Exception in Update BugTracker");

		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	@Override
	public BugTrackerDTO findByPK(long pk) throws ApplicationException {

		BugTrackerDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_BUGTRACKER WHERE ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new BugTrackerDTO();

				dto.setId(rs.getLong("ID"));
				dto.setBugId(rs.getInt("BUG_ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setSeverity(rs.getString("SEVERITY"));
				dto.setAssignedTo(rs.getString("ASSIGNED_TO"));
				dto.setStatus(rs.getString("STATUS"));
			}

			ps.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in Find By PK");

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

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_BUGTRACKER");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				BugTrackerDTO dto = new BugTrackerDTO();

				dto.setId(rs.getLong("ID"));
				dto.setBugId(rs.getInt("BUG_ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setSeverity(rs.getString("SEVERITY"));
				dto.setAssignedTo(rs.getString("ASSIGNED_TO"));
				dto.setStatus(rs.getString("STATUS"));

				list.add(dto);
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in List");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public List search(BugTrackerDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(BugTrackerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_BUGTRACKER WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}

			if (dto.getBugId() > 0) {
				sql.append(" AND BUG_ID=" + dto.getBugId());
			}

			if (dto.getTitle() != null && dto.getTitle().length() > 0) {

				sql.append(" AND TITLE LIKE '" + dto.getTitle() + "%'");
			}

			if (dto.getSeverity() != null && dto.getSeverity().length() > 0) {

				sql.append(" AND SEVERITY LIKE '" + dto.getSeverity() + "%'");
			}

			if (dto.getAssignedTo() != null && dto.getAssignedTo().length() > 0) {

				sql.append(" AND ASSIGNED_TO LIKE '" + dto.getAssignedTo() + "%'");
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {

				sql.append(" AND STATUS LIKE '" + dto.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		ArrayList<BugTrackerDTO> list = new ArrayList<BugTrackerDTO>();

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				BugTrackerDTO bdto = new BugTrackerDTO();

				bdto.setId(rs.getLong("ID"));
				bdto.setBugId(rs.getInt("BUG_ID"));
				bdto.setTitle(rs.getString("TITLE"));
				bdto.setSeverity(rs.getString("SEVERITY"));
				bdto.setAssignedTo(rs.getString("ASSIGNED_TO"));
				bdto.setStatus(rs.getString("STATUS"));

				list.add(bdto);
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in Search");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public BugTrackerDTO findByBugId(int bugId) throws ApplicationException {

		BugTrackerDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_BUGTRACKER WHERE BUG_ID=?");

			ps.setInt(1, bugId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				dto = new BugTrackerDTO();

				dto.setId(rs.getLong("ID"));
				dto.setBugId(rs.getInt("BUG_ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setSeverity(rs.getString("SEVERITY"));
				dto.setAssignedTo(rs.getString("ASSIGNED_TO"));
				dto.setStatus(rs.getString("STATUS"));
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByBugId");
		}

		return dto;
	}

	@Override
	public BugTrackerDTO findByTitle(String title) throws ApplicationException {

		return null;
	}

	@Override
	public BugTrackerDTO findByAssignedTo(String assignedTo) throws ApplicationException {

		return null;
	}
}