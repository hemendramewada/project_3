package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DonationDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DonationModelHibImp implements DonationModelInt {

    /**
     * Hibernate implementation of Donation model
     * @author Rajendra Singh
     */

    private static Logger log = Logger.getLogger(DonationModelHibImp.class.getName());

    @Override
    public long add(DonationDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("DonationModelHibImp add started");

        DonationDTO existDto = findByDonorName(dto.getDonorName());
        if (existDto != null) {
            throw new DuplicateRecordException("Donor Name already exists");
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
            throw new DatabaseException("Exception in Donation add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    @Override
    public void delete(DonationDTO dto) throws DatabaseException {

        log.info("DonationModelHibImp delete started");

        DonationDTO dDto = findByPK(dto.getId());

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dDto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DatabaseException("Exception in Donation delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(DonationDTO dto) throws DatabaseException, DuplicateRecordException {

        log.info("DonationModelHibImp update started");

        DonationDTO existDto = findByDonorName(dto.getDonorName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Donor Name already exists");
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
            throw new DatabaseException("Exception in Donation update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public DonationDTO findByPK(long pk) throws DatabaseException {

        Session session = null;
        DonationDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (DonationDTO) session.get(DonationDTO.class, pk);

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in getting Donation by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    @Override
    public DonationDTO findByDonorName(String donorName) throws DatabaseException {

        Session session = null;
        DonationDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DonationDTO.class);
            criteria.add(Restrictions.eq("donorName", donorName));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (DonationDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in findByDonorName " + e.getMessage());
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
            Criteria criteria = session.createCriteria(DonationDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Donation list");
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List search(DonationDTO dto, int pageNo, int pageSize) throws DatabaseException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(DonationDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getDonorName() != null && dto.getDonorName().length() > 0) {
                    criteria.add(Restrictions.like("donorName", dto.getDonorName() + "%"));
                }

                if (dto.getAmount() > 0) {
                    criteria.add(Restrictions.eq("amount", dto.getAmount()));
                }

                if (dto.getDonationDate() != null) {
                    criteria.add(Restrictions.eq("donationDate", dto.getDonationDate()));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new DatabaseException("Exception in Donation search hjgcshjahsg");
        } finally {
            session.close();
        }

        return list;
    }
}