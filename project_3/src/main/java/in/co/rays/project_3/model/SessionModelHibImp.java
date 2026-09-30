package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SessionDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SessionModelHibImp implements SessionModelInt {

    /**
     * Hibernate implementation of Session model
     * @author Rajendra Singh
     */

    private static Logger log = Logger.getLogger(SessionModelHibImp.class.getName());

    @Override
    public long add(SessionDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("SessionModelHibImp add started");

        SessionDTO existDto = findByToken(dto.getSessionToken());
        if (existDto != null) {
            throw new DuplicateRecordException("Session Token already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
            log.info("Session added successfully");

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Session add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(SessionDTO dto) throws DatabaseException {

        log.info("SessionModelHibImp delete started");
        SessionDTO sessionDto = findByPK(dto.getId());

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(sessionDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }e.printStackTrace();
            throw new DatabaseException("Exception in Session delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(SessionDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("SessionModelHibImp update started");

        SessionDTO existDto = findByToken(dto.getSessionToken());
        if (existDto != null && existDto.getSessionToken() != dto.getSessionToken()) {
            throw new DuplicateRecordException("Session Token already exists");
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
            throw new DatabaseException("Exception in Session update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public SessionDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        SessionDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (SessionDTO) session.get(SessionDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Session by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    public SessionDTO findByToken(String token) throws DatabaseException {

        Session session = null;
        SessionDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SessionDTO.class);
            criteria.add(Restrictions.eq("sessionToken", token));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (SessionDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByToken " + e.getMessage());
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
            Criteria criteria = session.createCriteria(SessionDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Session list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(SessionDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SessionDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getSessionToken() != null && dto.getSessionToken().length() > 0) {
                    criteria.add(Restrictions.like("sessionToken", dto.getSessionToken() + "%"));
                }

                if (dto.getUserName() != null && dto.getUserName().length() > 0) {
                    criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
                }

                if (dto.getSessionStatus() != null && dto.getSessionStatus().length() > 0) {
                    criteria.add(Restrictions.eq("sessionStatus", dto.getSessionStatus()));
                }

                if (dto.getLoginTime() != null) {
                    criteria.add(Restrictions.eq("loginTime", dto.getLoginTime()));
                }

                if (dto.getLogoutTime() != null) {
                    criteria.add(Restrictions.eq("logoutTime", dto.getLogoutTime()));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Session search");
        } finally {
            session.close();
        }

        return list;
    }
}