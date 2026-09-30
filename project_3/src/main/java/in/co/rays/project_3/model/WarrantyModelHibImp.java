package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.WarrantyDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class WarrantyModelHibImp implements WarrantyModelInt {

    /**
     * Hibernate implementation of Warranty model
     * @author Rajendra Singh
     */

    private static Logger log = Logger.getLogger(WarrantyModelHibImp.class.getName());

    @Override
    public long add(WarrantyDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("WarrantyModelHibImp add started");

        WarrantyDTO existDto = findByProductName(dto.getProductName());
        if (existDto != null) {
            throw new DuplicateRecordException("Product Name already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Warranty add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(WarrantyDTO dto) throws DatabaseException {

        log.info("WarrantyModelHibImp delete started");

        WarrantyDTO wDto = findByPK(dto.getId());

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(wDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Warranty delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(WarrantyDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("WarrantyModelHibImp update started");

        WarrantyDTO existDto = findByProductName(dto.getProductName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Product Name already exists");
        }

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Warranty update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public WarrantyDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        WarrantyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (WarrantyDTO) session.get(WarrantyDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Warranty by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public WarrantyDTO findByProductName(String productName) throws DatabaseException {

        Session session = null;
        WarrantyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WarrantyDTO.class);
            criteria.add(Restrictions.eq("productName", productName));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (WarrantyDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByProductName " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public List list() throws DatabaseException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WarrantyDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Warranty list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(WarrantyDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(WarrantyDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getProductName() != null && dto.getProductName().length() > 0) {
                    criteria.add(Restrictions.like("productName", dto.getProductName() + "%"));
                }

            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Warranty search");
        } finally {
            session.close();
        }

        return list;
    }
}