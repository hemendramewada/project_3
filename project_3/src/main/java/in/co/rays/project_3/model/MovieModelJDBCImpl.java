package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.MovieDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implementation of Movie model
 * @author Hemendra mewada
 *
 */
public class MovieModelJDBCImpl implements MovieModelInt {

	private static Logger log = Logger.getLogger(MovieModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_MOVIE");
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
		log.debug("Model nextPK End");
		return pk + 1;
	}

	@Override
	public long add(MovieDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");
		Connection conn = null;
		long pk = 0;

		MovieDTO existDto = findByName(dto.getMovieName());
		if (existDto != null) {
			throw new DuplicateRecordException("Movie Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO ST_MOVIE VALUES(?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getMovieName());
			pstmt.setString(3, dto.getDirector());
			pstmt.setString(4, dto.getProducer());
			pstmt.setString(5, dto.getDuration());
			pstmt.setString(6, dto.getGenre());
			pstmt.setDate(7, new java.sql.Date(dto.getReleaseDate().getTime()));
			pstmt.setString(8, dto.getLanguage());
			pstmt.setString(9, dto.getCreatedBy());
			pstmt.setString(10, dto.getModifiedBy());
			pstmt.setTimestamp(11, dto.getCreatedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Movie");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model add End");
		return pk;
	}

	@Override
	public void delete(MovieDTO dto) throws ApplicationException {

		log.debug("Model delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_MOVIE WHERE ID=?");
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
			throw new ApplicationException("Exception in delete Movie");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete End");
	}

	@Override
	public void update(MovieDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model update Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_MOVIE SET MOVIE_NAME=?,DIRECTOR=?,PRODUCER=?,DURATION=?,GENRE=?,RELEASE_DATE=?,LANGUAGE=?,CREATED_BY=?,MODIFIED_BY=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, dto.getMovieName());
			pstmt.setString(2, dto.getDirector());
			pstmt.setString(3, dto.getProducer());
			pstmt.setString(4, dto.getDuration());
			pstmt.setString(5, dto.getGenre());
			pstmt.setDate(6, new java.sql.Date(dto.getReleaseDate().getTime()));
			pstmt.setString(7, dto.getLanguage());
			pstmt.setString(8, dto.getCreatedBy());
			pstmt.setString(9, dto.getModifiedBy());
			pstmt.setTimestamp(10, dto.getModifiedDatetime());
			pstmt.setLong(11, dto.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Movie");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MOVIE");

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
				MovieDTO dto = new MovieDTO();
				dto.setId(rs.getLong(1));
				dto.setMovieName(rs.getString(2));
				dto.setDirector(rs.getString(3));
				dto.setProducer(rs.getString(4));
				dto.setDuration(rs.getString(5));
				dto.setGenre(rs.getString(6));
				dto.setReleaseDate(rs.getDate(7));
				dto.setLanguage(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in list Movie");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

	@Override
	public List search(MovieDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(MovieDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MOVIE WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}
			if (dto.getMovieName() != null && dto.getMovieName().length() > 0) {
				sql.append(" AND MOVIE_NAME like '" + dto.getMovieName() + "%'");
			}
			if (dto.getDirector() != null && dto.getDirector().length() > 0) {
				sql.append(" AND DIRECTOR like '" + dto.getDirector() + "%'");
			}
			if (dto.getGenre() != null && dto.getGenre().length() > 0) {
				sql.append(" AND GENRE like '" + dto.getGenre() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new MovieDTO();
				dto.setId(rs.getLong(1));
				dto.setMovieName(rs.getString(2));
				dto.setDirector(rs.getString(3));
				dto.setProducer(rs.getString(4));
				dto.setDuration(rs.getString(5));
				dto.setGenre(rs.getString(6));
				dto.setReleaseDate(rs.getDate(7));
				dto.setLanguage(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in search Movie");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	@Override
	public MovieDTO findByPK(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MOVIE WHERE ID=?");
		MovieDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new MovieDTO();
				dto.setId(rs.getLong(1));
				dto.setMovieName(rs.getString(2));
				dto.setDirector(rs.getString(3));
				dto.setProducer(rs.getString(4));
				dto.setDuration(rs.getString(5));
				dto.setGenre(rs.getString(6));
				dto.setReleaseDate(rs.getDate(7));
				dto.setLanguage(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK Movie");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	@Override
	public MovieDTO findByName(String movieName) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MOVIE WHERE MOVIE_NAME=?");
		MovieDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, movieName);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new MovieDTO();
				dto.setId(rs.getLong(1));
				dto.setMovieName(rs.getString(2));
				dto.setDirector(rs.getString(3));
				dto.setProducer(rs.getString(4));
				dto.setDuration(rs.getString(5));
				dto.setGenre(rs.getString(6));
				dto.setReleaseDate(rs.getDate(7));
				dto.setLanguage(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in findByName Movie");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}
}