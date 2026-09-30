package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ListenerDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ListenerModelHibImpl implements ListenerModelInt {

    private static Logger log = Logger.getLogger(ListenerModelHibImpl.class.getName());

    @Override
    public long add(ListenerDTO dto) throws DatabaseException, DuplicateRecordException {

        ListenerDTO existDto = findByListenerCode(dto.getListenerCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Listener Code already exists");
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
            throw new DatabaseException("Exception in Listener add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(ListenerDTO dto) throws DatabaseException {

        ListenerDTO lDto = findByPK(dto.getId());

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(lDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Listener delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(ListenerDTO dto) throws DatabaseException, DuplicateRecordException {

        ListenerDTO existDto = findByListenerCode(dto.getListenerCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Listener Code already exists");
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
            throw new DatabaseException("Exception in Listener update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public ListenerDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        ListenerDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (ListenerDTO) session.get(ListenerDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Listener by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public ListenerDTO findByListenerCode(String listenerCode) throws DatabaseException {

        Session session = null;
        ListenerDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ListenerDTO.class);
            criteria.add(Restrictions.eq("listenerCode", listenerCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (ListenerDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByListenerCode " + e.getMessage());
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
            Criteria criteria = session.createCriteria(ListenerDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Listener list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(ListenerDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ListenerDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getListenerCode() != null && dto.getListenerCode().length() > 0) {
                    criteria.add(Restrictions.like("listenerCode", dto.getListenerCode() + "%"));
                }

                if (dto.getQueueName() != null && dto.getQueueName().length() > 0) {
                    criteria.add(Restrictions.like("queueName", dto.getQueueName() + "%"));
                }

                if (dto.getConsumerGroup() != null && dto.getConsumerGroup().length() > 0) {
                    criteria.add(Restrictions.like("consumerGroup", dto.getConsumerGroup() + "%"));
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
            throw new DatabaseException("Exception in Listener search");
        } finally {
            session.close();
        }

        return list;
    }
}