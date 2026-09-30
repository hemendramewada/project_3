package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.HospitalDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class HospitalModelHibImp implements HospitalModelInt {

    /**
     * Hibernate implementation of Hospital model
     * @author Rajendra Singh
     */

    private static Logger log = Logger.getLogger(HospitalModelHibImp.class.getName());

    @Override
    public long add(HospitalDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("HospitalModelHibImp add started");

        HospitalDTO existDto = findByHospitalId(dto.getHospitalId());
        if (existDto != null) {
            throw new DuplicateRecordException("Hospital ID already exists");
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
            throw new DatabaseException("Exception in Hospital add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(HospitalDTO dto) throws DatabaseException {

        log.info("HospitalModelHibImp delete started");

        HospitalDTO hDto = findByPK(dto.getId());
        
        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(hDto);
            
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Hospital delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(HospitalDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("HospitalModelHibImp update started");

        HospitalDTO existDto = findByHospitalId(dto.getHospitalId());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Hospital ID already exists");
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
            throw new DatabaseException("Exception in Hospital update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public HospitalDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        HospitalDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (HospitalDTO) session.get(HospitalDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Hospital by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public HospitalDTO findByHospitalId(String hospitalId) throws DatabaseException {

        Session session = null;
        HospitalDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(HospitalDTO.class);
            criteria.add(Restrictions.eq("hospitalId", hospitalId));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (HospitalDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByHospitalId " + e.getMessage());
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
            Criteria criteria = session.createCriteria(HospitalDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Hospital list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(HospitalDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(HospitalDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getHospitalId() != null && dto.getHospitalId().length() > 0) {
                    criteria.add(Restrictions.like("hospitalId", dto.getHospitalId() + "%"));
                }

                if (dto.getHospitalName() != null && dto.getHospitalName().length() > 0) {
                    criteria.add(Restrictions.like("hospitalName", dto.getHospitalName() + "%"));
                }

                if (dto.getCity() != null && dto.getCity().length() > 0) {
                    criteria.add(Restrictions.like("city", dto.getCity() + "%"));
                }

                if (dto.getContactNumber() != null && dto.getContactNumber().length() > 0) {
                    criteria.add(Restrictions.like("contactNumber", dto.getContactNumber() + "%"));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Hospital search");
        } finally {
            session.close();
        }

        return list;
    }
}