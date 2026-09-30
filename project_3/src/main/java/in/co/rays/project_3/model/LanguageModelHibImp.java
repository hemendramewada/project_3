package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.LanguageDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class LanguageModelHibImp implements LanguageModelInt {

    /**
     * Hibernate implementation of Language model
     * @author Rajendra Singh
     */

    private static Logger log = Logger.getLogger(LanguageModelHibImp.class.getName());

    @Override
    public long add(LanguageDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("LanguageModelHibImp add started");

        LanguageDTO existDto = findByLanguageCode(dto.getLanguageCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Language Code already exists");
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
            throw new DatabaseException("Exception in Language add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(LanguageDTO dto) throws DatabaseException {

        Session session = null;
        Transaction tx = null;

        LanguageDTO languageDto = findByPK(dto.getId());
        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(languageDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Language delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(LanguageDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("LanguageModelHibImp update started");

        LanguageDTO existDto = findByLanguageCode(dto.getLanguageCode());

        if (existDto != null ) {
            throw new DuplicateRecordException("Language Code already exists");
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
            throw new DatabaseException("Exception in Language update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public LanguageDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        LanguageDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (LanguageDTO) session.get(LanguageDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Language by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public LanguageDTO findByLanguageCode(String languageCode) throws DatabaseException {

        Session session = null;
        LanguageDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(LanguageDTO.class);
            criteria.add(Restrictions.eq("languageCode", languageCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (LanguageDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByLanguageCode " + e.getMessage());
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
            Criteria criteria = session.createCriteria(LanguageDTO.class);

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Language list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(LanguageDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(LanguageDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getLanguageCode() != null && dto.getLanguageCode().length() > 0) {
                    criteria.add(Restrictions.like("languageCode", dto.getLanguageCode() + "%"));
                }

                if (dto.getLanguageName() != null && dto.getLanguageName().length() > 0) {
                    criteria.add(Restrictions.like("languageName", dto.getLanguageName() + "%"));
                }

                if (dto.getDirection() != null && dto.getDirection().length() > 0) {
                    criteria.add(Restrictions.eq("direction", dto.getDirection()));
                }

                if (dto.getLanguageStatus() != null && dto.getLanguageStatus().length() > 0) {
                    criteria.add(Restrictions.eq("languageStatus", dto.getLanguageStatus()));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Language search");
        } finally {
            session.close();
        }

        return list;
    }
}