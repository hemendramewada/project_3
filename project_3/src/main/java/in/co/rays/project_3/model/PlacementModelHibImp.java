package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PlacementDTO;
import in.co.rays.project_3.dto.SessionDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PlacementModelHibImp implements PlacementModelInt {

    private static Logger log = Logger.getLogger(PlacementModelHibImp.class.getName());

    @Override
    public long add(PlacementDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("PlacementModelHibImp add started");

        PlacementDTO existDto = findByPlacementCode(dto.getPlacementCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Placement Code already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
            log.info("Placement added successfully");

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DatabaseException("Exception in Placement add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(PlacementDTO dto) throws DatabaseException {

        log.info("PlacementModelHibImp delete started");

        Session session = null;
        Transaction tx = null;
        PlacementDTO pDto = findByPK(dto.getId());
        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(pDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DatabaseException("Exception in Placement delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(PlacementDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("PlacementModelHibImp update started");

        PlacementDTO existDto = findByPlacementCode(dto.getPlacementCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Placement Code already exists");
        }

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DatabaseException("Exception in Placement update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public PlacementDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        PlacementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (PlacementDTO) session.get(PlacementDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Placement by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    public PlacementDTO findByPlacementCode(String placementCode) throws DatabaseException {

        Session session = null;
        PlacementDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PlacementDTO.class);
            criteria.add(Restrictions.eq("placementCode", placementCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (PlacementDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByPlacementCode " + e.getMessage());
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
            Criteria criteria = session.createCriteria(PlacementDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Placement list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(PlacementDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PlacementDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getPlacementCode() != null && dto.getPlacementCode().length() > 0) {
                    criteria.add(Restrictions.like("placementCode", dto.getPlacementCode() + "%"));
                }

                if (dto.getStudentName() != null && dto.getStudentName().length() > 0) {
                    criteria.add(Restrictions.like("studentName", dto.getStudentName() + "%"));
                }

                if (dto.getCompanyName() != null && dto.getCompanyName().length() > 0) {
                    criteria.add(Restrictions.like("companyName", dto.getCompanyName() + "%"));
                }

                if (dto.getPlacementStatus() != null && dto.getPlacementStatus().length() > 0) {
                    criteria.add(Restrictions.eq("placementStatus", dto.getPlacementStatus()));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Placement search");
        } finally {
            session.close();
        }

        return list;
    }
}