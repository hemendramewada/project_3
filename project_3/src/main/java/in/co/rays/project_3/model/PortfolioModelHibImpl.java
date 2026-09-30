package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PortfolioDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PortfolioModelHibImpl implements PortfolioModelInt {

    /**
     * Hibernate implementation of Portfolio model
     * @author Rajendra Singh
     */

    private static Logger log = Logger.getLogger(PortfolioModelHibImpl.class.getName());

    @Override
    public long add(PortfolioDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("PortfolioModelHibImpl add started");

        PortfolioDTO existDto = findByPortfolioName(dto.getPortfolioName());
        if (existDto != null) {
            throw new DuplicateRecordException("Portfolio Name already exists");
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
            throw new DatabaseException("Exception in Portfolio add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(PortfolioDTO dto) throws DatabaseException {

        log.info("PortfolioModelHibImpl delete started");

        PortfolioDTO pDto = findByPK(dto.getId());

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(pDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Portfolio delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(PortfolioDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("PortfolioModelHibImpl update started");

        PortfolioDTO existDto = findByPortfolioName(dto.getPortfolioName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Portfolio Name already exists");
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
            throw new DatabaseException("Exception in Portfolio update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public PortfolioDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        PortfolioDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (PortfolioDTO) session.get(PortfolioDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Portfolio by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public PortfolioDTO findByPortfolioName(String portfolioName) throws DatabaseException {

        Session session = null;
        PortfolioDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PortfolioDTO.class);
            criteria.add(Restrictions.eq("portfolioName", portfolioName));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (PortfolioDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByPortfolioName " + e.getMessage());
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
            Criteria criteria = session.createCriteria(PortfolioDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Portfolio list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(PortfolioDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PortfolioDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getPortfolioName() != null && dto.getPortfolioName().length() > 0) {
                    criteria.add(Restrictions.like("portfolioName", dto.getPortfolioName() + "%"));
                }

            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Portfolio search");
        } finally {
            session.close();
        }

        return list;
    }
}