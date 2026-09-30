package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implementation of Podcast Model
 * 
 * @author Rajendra Singh
 *
 */
public class PodcastModelJDBCImpl implements PodcastModelInt {

	private static Logger log = Logger.getLogger(PodcastModelJDBCImpl.class);

	Connection con = null;

	/**
	 * Next Primary Key
	 */
	public long nextPK() throws DatabaseException {

		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_PODCAST");

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

	/**
	 * Add Podcast
	 */
	@Override
	public long add(PodcastDTO dto) throws ApplicationException, DuplicateRecordException {

		long pk = 0;

		try {

			pk = nextPK();

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("INSERT INTO ST_PODCAST VALUES(?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getTitle());
			ps.setString(3, dto.getHostName());
			ps.setString(4, dto.getDuration());
			ps.setString(5, dto.getCategory());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(ex.getMessage());
			}

			throw new ApplicationException("Exception in Add Podcast");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	/**
	 * Delete Podcast
	 */
	@Override
	public void delete(PodcastDTO dto) throws ApplicationException {

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_PODCAST WHERE ID=?");

			ps.setLong(1, dto.getId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(ex.getMessage());
			}

			throw new ApplicationException("Exception in Delete Podcast");

		} finally {

			JDBCDataSource.closeConnection(con);
		}
	}

	/**
	 * Update Podcast
	 */
	@Override
	public void update(PodcastDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"UPDATE ST_PODCAST SET TITLE=?,HOST_NAME=?,DURATION=?,CATEGORY=?,MODIFIED_BY=?,MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getHostName());
			ps.setString(3, dto.getDuration());
			ps.setString(4, dto.getCategory());
			ps.setString(5, dto.getModifiedBy());
			ps.setTimestamp(6, dto.getModifiedDatetime());
			ps.setLong(7, dto.getId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(ex.getMessage());
			}

			throw new ApplicationException("Exception in Update Podcast");

		} finally {

			JDBCDataSource.closeConnection(con);
		}
	}

	@Override
	public PodcastDTO findByPK(long pk) throws ApplicationException {

		PodcastDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_PODCAST WHERE ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new PodcastDTO();

				dto.setId(rs.getLong("ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setHostName(rs.getString("HOST_NAME"));
				dto.setDuration(rs.getString("DURATION"));
				dto.setCategory(rs.getString("CATEGORY"));
			}

			ps.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in FindByPK Podcast");

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

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_PODCAST");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				PodcastDTO dto = new PodcastDTO();

				dto.setId(rs.getLong("ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setHostName(rs.getString("HOST_NAME"));
				dto.setDuration(rs.getString("DURATION"));
				dto.setCategory(rs.getString("CATEGORY"));

				list.add(dto);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in Podcast List");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public List search(PodcastDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(PodcastDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_PODCAST WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}

			if (dto.getTitle() != null && dto.getTitle().length() > 0) {

				sql.append(" AND TITLE LIKE '" + dto.getTitle() + "%'");
			}

			if (dto.getHostName() != null && dto.getHostName().length() > 0) {

				sql.append(" AND HOST_NAME LIKE '" + dto.getHostName() + "%'");
			}

			if (dto.getDuration() != null && dto.getDuration().length() > 0) {

				sql.append(" AND DURATION LIKE '" + dto.getDuration() + "%'");
			}

			if (dto.getCategory() != null && dto.getCategory().length() > 0) {

				sql.append(" AND CATEGORY LIKE '" + dto.getCategory() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		ArrayList<PodcastDTO> list = new ArrayList<>();

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				PodcastDTO pdto = new PodcastDTO();

				pdto.setId(rs.getLong("ID"));
				pdto.setTitle(rs.getString("TITLE"));
				pdto.setHostName(rs.getString("HOST_NAME"));
				pdto.setDuration(rs.getString("DURATION"));
				pdto.setCategory(rs.getString("CATEGORY"));

				list.add(pdto);
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in Podcast Search");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public PodcastDTO findByTitle(String title) throws ApplicationException {

		PodcastDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_PODCAST WHERE TITLE=?");

			ps.setString(1, title);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				dto = new PodcastDTO();

				dto.setId(rs.getLong("ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setHostName(rs.getString("HOST_NAME"));
				dto.setDuration(rs.getString("DURATION"));
				dto.setCategory(rs.getString("CATEGORY"));
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByTitle");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	@Override
	public PodcastDTO findByHostName(String hostName) throws ApplicationException {

		PodcastDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_PODCAST WHERE HOST_NAME=?");

			ps.setString(1, hostName);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				dto = new PodcastDTO();

				dto.setId(rs.getLong("ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setHostName(rs.getString("HOST_NAME"));
				dto.setDuration(rs.getString("DURATION"));
				dto.setCategory(rs.getString("CATEGORY"));
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByHostName");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	@Override
	public PodcastDTO findByCategory(String category) throws ApplicationException {

		PodcastDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_PODCAST WHERE CATEGORY=?");

			ps.setString(1, category);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				dto = new PodcastDTO();

				dto.setId(rs.getLong("ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setHostName(rs.getString("HOST_NAME"));
				dto.setDuration(rs.getString("DURATION"));
				dto.setCategory(rs.getString("CATEGORY"));
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByCategory");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}
}