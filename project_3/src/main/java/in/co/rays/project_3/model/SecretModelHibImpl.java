package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SecretDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SecretModelHibImpl implements SecretModelInt {

  

    private static Logger log = Logger.getLogger(SecretModelHibImpl.class.getName());

    @Override
    public long add(SecretDTO dto) throws DatabaseException, DuplicateRecordException {


        SecretDTO existDto = findBySecretCode(dto.getSecretCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Secret Code already exists");
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
            throw new DatabaseException("Exception in Secret add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(SecretDTO dto) throws DatabaseException {

       

        SecretDTO sDto = findByPK(dto.getId());

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(sDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Secret delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(SecretDTO dto) throws DatabaseException, DuplicateRecordException {

      

        SecretDTO existDto = findBySecretCode(dto.getSecretCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Secret Code already exists");
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
            throw new DatabaseException("Exception in Secret update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public SecretDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        SecretDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (SecretDTO) session.get(SecretDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Secret by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public SecretDTO findBySecretCode(String secretCode) throws DatabaseException {

        Session session = null;
        SecretDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SecretDTO.class);
            criteria.add(Restrictions.eq("secretCode", secretCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (SecretDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findBySecretCode " + e.getMessage());
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
            Criteria criteria = session.createCriteria(SecretDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Secret list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(SecretDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(SecretDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getSecretCode() != null && dto.getSecretCode().length() > 0) {
                    criteria.add(Restrictions.like("secretCode", dto.getSecretCode() + "%"));
                }

                if (dto.getKeyName() != null && dto.getKeyName().length() > 0) {
                    criteria.add(Restrictions.like("keyName", dto.getKeyName() + "%"));
                }

                if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                    criteria.add(Restrictions.eq("status", dto.getStatus()));
                }

            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Secret search");
        } finally {
            session.close();
        }

        return list;
    }
}