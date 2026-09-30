package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ItemDTO;
import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ItemModelHibImp implements ItemModelInt {

	@Override
	public long add(ItemDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
//		ProductDTO duplicateProductName = fingByName(dto.getProductName());
//		if (duplicateProductName != null) {
//			throw new DuplicateRecordException("product name already exist");
//		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in college Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
		
	}

	@Override
	public void delete(ItemDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ItemDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(ItemDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(ItemDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDTO fingByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
