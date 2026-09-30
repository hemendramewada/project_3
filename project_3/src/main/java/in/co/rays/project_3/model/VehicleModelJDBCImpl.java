
package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.VehicleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implementation of Vehicle model
 *
 * @author Rajendra Singh
 */
public class VehicleModelJDBCImpl implements VehicleModelInt {

	private static Logger log = Logger.getLogger(VehicleModelJDBCImpl.class);

	@Override
	public long nextPK() throws DatabaseException {

		log.debug("Vehicle PK start");

		Connection con = null;
		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select max(VEHICLE_ID) from ST_VEHICLE");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {

			log.error(e);

			throw new DatabaseException("Database Exception " + e);

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Vehicle PK end");

		return pk + 1;
	}

	@Override
	public long add(VehicleDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Vehicle add started");

		Connection con = null;
		long pk = 0;

		VehicleDTO existDto = findByVehicleName(dto.getVehicleName());

		if (existDto != null) {

			throw new DuplicateRecordException("Vehicle Name already exists");
		}

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement("insert into ST_VEHICLE "
					+ "(VEHICLE_ID, VEHICLE_NAME, MODEL, COLOR, PRICE) " + "values (?, ?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getVehicleName());
			ps.setString(3, dto.getModel());
			ps.setString(4, dto.getColor());
			ps.setDouble(5, dto.getPrice());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error(e);

			try {

				if (con != null) {
					con.rollback();
				}

			} catch (Exception e2) {

				throw new ApplicationException("Exception : Add rollback exception " + e2.getMessage());
			}

			throw new ApplicationException("Exception : Exception in add Vehicle");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Vehicle add ended");

		return pk;
	}

	@Override
	public void delete(VehicleDTO dto) throws ApplicationException {

		log.debug("Vehicle delete started");

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from ST_VEHICLE where VEHICLE_ID=?");

			ps.setLong(1, dto.getVechicleId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			try {

				if (con != null) {
					con.rollback();
				}

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in delete Vehicle");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Vehicle delete ended");
	}

	@Override
	public void update(VehicleDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Vehicle update started");

		Connection con = null;

		VehicleDTO dtoExist = findByVehicleName(dto.getVehicleName());

		if (dtoExist != null && dtoExist.getVechicleId() != dto.getVechicleId()) {

			throw new DuplicateRecordException("Vehicle Name already exists");
		}

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"update ST_VEHICLE set " + "VEHICLE_NAME=?, MODEL=?, COLOR=?, PRICE=? " + "where VEHICLE_ID=?");

			ps.setString(1, dto.getVehicleName());
			ps.setString(2, dto.getModel());
			ps.setString(3, dto.getColor());
			ps.setDouble(4, dto.getPrice());
			ps.setLong(5, dto.getVechicleId());

			ps.executeUpdate();

			con.commit();

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			try {

				if (con != null) {
					con.rollback();
				}

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating Vehicle");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Vehicle update ended");
	}

	@Override
	public VehicleDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;
		VehicleDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement("select * from ST_VEHICLE where VEHICLE_ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new VehicleDTO();

				dto.setVechicleId(rs.getLong(1));
				dto.setVehicleName(rs.getString(2));
				dto.setModel(rs.getString(3));
				dto.setColor(rs.getString(4));
				dto.setPrice(rs.getDouble(5));
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting Vehicle by PK");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Vehicle findByPK End");

		return dto;
	}

	public VehicleDTO findByVehicleName(String vehicleName) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;
		VehicleDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement("select * from ST_VEHICLE where VEHICLE_NAME=?");

			ps.setString(1, vehicleName);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new VehicleDTO();

				dto.setVechicleId(rs.getLong(1));
				dto.setVehicleName(rs.getString(2));
				dto.setModel(rs.getString(3));
				dto.setColor(rs.getString(4));
				dto.setPrice(rs.getDouble(5));
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting Vehicle by name");

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

		ArrayList<VehicleDTO> array = new ArrayList<VehicleDTO>();

		StringBuffer sql = new StringBuffer("select * from ST_VEHICLE");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			con = JDBCDataSource.getConnection();

			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				VehicleDTO dto = new VehicleDTO();

				dto.setVechicleId(rs.getLong(1));
				dto.setVehicleName(rs.getString(2));
				dto.setModel(rs.getString(3));
				dto.setColor(rs.getString(4));
				dto.setPrice(rs.getDouble(5));

				array.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in getting list of vehicles");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Vehicle list End");

		return array;
	}

	@Override
	public List search(VehicleDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Connection con = null;
		PreparedStatement ps = null;

		ArrayList<VehicleDTO> array = new ArrayList<VehicleDTO>();

		StringBuffer sql = new StringBuffer("select * from ST_VEHICLE where 1=1");

		if (dto != null) {

			if (dto.getVechicleId() != null && dto.getVechicleId() > 0) {

				sql.append(" AND VEHICLE_ID = " + dto.getVechicleId());
			}

			if (dto.getVehicleName() != null && dto.getVehicleName().length() > 0) {

				sql.append(" AND VEHICLE_NAME like '" + dto.getVehicleName() + "%'");
			}

			if (dto.getModel() != null && dto.getModel().length() > 0) {

				sql.append(" AND MODEL like '" + dto.getModel() + "%'");
			}

			if (dto.getColor() != null && dto.getColor().length() > 0) {

				sql.append(" AND COLOR like '" + dto.getColor() + "%'");
			}

			if (dto.getPrice() != null && dto.getPrice() > 0) {

				sql.append(" AND PRICE = " + dto.getPrice());
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

				VehicleDTO vehicleDto = new VehicleDTO();

				vehicleDto.setVechicleId(rs.getLong(1));
				vehicleDto.setVehicleName(rs.getString(2));
				vehicleDto.setModel(rs.getString(3));
				vehicleDto.setColor(rs.getString(4));
				vehicleDto.setPrice(rs.getDouble(5));

				array.add(vehicleDto);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception : Exception in search Vehicle");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		log.debug("Vehicle search End");

		return array;
	}

	@Override
	public List search(VehicleDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}
}
