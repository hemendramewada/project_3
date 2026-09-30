package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.FreelancerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.util.JDBCDataSource;

public class FreelancerModelJDBCImpl implements FreelancerModelInt {

	private static Logger log = Logger.getLogger(FreelancerModelJDBCImpl.class);

	Connection con = null;

	public long nextPK() throws DatabaseException {

		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_FREELANCER");

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
	public long add(FreelancerDTO dto) throws ApplicationException {

		long pk = 0;

		try {

			pk = nextPK();

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("INSERT INTO ST_FREELANCER VALUES(?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getSkill());
			ps.setInt(4, dto.getExperience());
			ps.setDouble(5, dto.getHourlyRate());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in Freelancer Add");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	@Override
	public void delete(FreelancerDTO dto) throws ApplicationException {

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_FREELANCER WHERE ID=?");

			ps.setLong(1, dto.getId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in Freelancer Delete");

		} finally {

			JDBCDataSource.closeConnection(con);
		}
	}

	@Override
	public void update(FreelancerDTO dto) throws ApplicationException {

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(

					"UPDATE ST_FREELANCER SET " + "NAME=?,SKILL=?,EXPERIENCE=?,HOURLY_RATE=?,"
							+ "CREATED_BY=?,MODIFIED_BY=?," + "CREATED_DATETIME=?,MODIFIED_DATETIME=? " + "WHERE ID=?");

			ps.setString(1, dto.getName());
			ps.setString(2, dto.getSkill());
			ps.setInt(3, dto.getExperience());
			ps.setDouble(4, dto.getHourlyRate());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, dto.getCreatedDatetime());
			ps.setTimestamp(8, dto.getModifiedDatetime());
			ps.setLong(9, dto.getId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in Freelancer Update");

		} finally {

			JDBCDataSource.closeConnection(con);
		}
	}

	@Override
	public FreelancerDTO findByPK(long pk) throws ApplicationException {

		FreelancerDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_FREELANCER WHERE ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				dto = new FreelancerDTO();

				dto.setId(rs.getLong("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setSkill(rs.getString("SKILL"));
				dto.setExperience(rs.getInt("EXPERIENCE"));
				dto.setHourlyRate(rs.getDouble("HOURLY_RATE"));
			}

			ps.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in Find By PK");
		}

		return dto;
	}

	@Override
	public FreelancerDTO findByName(String name) throws ApplicationException {

		FreelancerDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_FREELANCER WHERE NAME=?");

			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				dto = new FreelancerDTO();

				dto.setId(rs.getLong("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setSkill(rs.getString("SKILL"));
				dto.setExperience(rs.getInt("EXPERIENCE"));
				dto.setHourlyRate(rs.getDouble("HOURLY_RATE"));
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in Find By Name");
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

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FREELANCER");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				FreelancerDTO dto = new FreelancerDTO();

				dto.setId(rs.getLong("ID"));
				dto.setName(rs.getString("NAME"));
				dto.setSkill(rs.getString("SKILL"));
				dto.setExperience(rs.getInt("EXPERIENCE"));
				dto.setHourlyRate(rs.getDouble("HOURLY_RATE"));

				list.add(dto);
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in Freelancer List");
		}

		return list;
	}

	@Override
	public List search(FreelancerDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public List search(FreelancerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FREELANCER WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}

			if (dto.getName() != null && dto.getName().length() > 0) {

				sql.append(" AND NAME LIKE '" + dto.getName() + "%'");
			}

			if (dto.getSkill() != null && dto.getSkill().length() > 0) {

				sql.append(" AND SKILL LIKE '" + dto.getSkill() + "%'");
			}

			if (dto.getExperience() > 0) {

				sql.append(" AND EXPERIENCE=" + dto.getExperience());
			}

			if (dto.getHourlyRate() > 0) {

				sql.append(" AND HOURLY_RATE=" + dto.getHourlyRate());
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				FreelancerDTO fdto = new FreelancerDTO();

				fdto.setId(rs.getLong("ID"));
				fdto.setName(rs.getString("NAME"));
				fdto.setSkill(rs.getString("SKILL"));
				fdto.setExperience(rs.getInt("EXPERIENCE"));
				fdto.setHourlyRate(rs.getDouble("HOURLY_RATE"));

				list.add(fdto);
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in Freelancer Search");
		}

		return list;
	}
}