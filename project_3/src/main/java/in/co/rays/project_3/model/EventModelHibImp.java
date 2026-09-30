package in.co.rays.project_3.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class EventModelHibImp implements EventModelInt {

	/**
	 * Hibernate implementation of Event model
	 * 
	 * @author Rajendra Singh
	 */

	private static Logger log = Logger.getLogger(EventModelHibImp.class.getName());

	@Override
	public long add(EventDTO dto) throws DatabaseException, DuplicateRecordException {

		log.info("EventModelHibImp add started");

		EventDTO existDto = findByEventCode(dto.getEventCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Event Code already exists");
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
			throw new DatabaseException("Exception in Event add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(EventDTO dto) throws DatabaseException {

		log.info("EventModelHibImp delete started");

		EventDTO eDto = findByPK(dto.getId());

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(eDto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DatabaseException("Exception in Event delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(EventDTO dto) throws DatabaseException, DuplicateRecordException {

		log.info("EventModelHibImp update started");

		EventDTO existDto = findByEventCode(dto.getEventCode());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Event Code already exists");
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
			throw new DatabaseException("Exception in Event update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public EventDTO findByPK(long pk) throws DatabaseException {

		Session session = null;
		EventDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (EventDTO) session.get(EventDTO.class, pk);

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in getting Event by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public EventDTO findByEventCode(String eventCode) throws DatabaseException {

		Session session = null;
		EventDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EventDTO.class);
			criteria.add(Restrictions.eq("eventCode", eventCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (EventDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in findByEventCode " + e.getMessage());
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
			Criteria criteria = session.createCriteria(EventDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Event list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(EventDTO dto, int pageNo, int pageSize) throws DatabaseException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EventDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getEventCode() != null && dto.getEventCode().length() > 0) {
					criteria.add(Restrictions.like("eventCode", dto.getEventCode() + "%"));
				}

				if (dto.getEventName() != null && dto.getEventName().length() > 0) {
					criteria.add(Restrictions.like("eventName", dto.getEventName() + "%"));
				}

				if (dto.getEventDate() != null) {
					criteria.add(Restrictions.eq("eventDate", dto.getEventDate()));
				}

				if (dto.getOrganizer() != null && dto.getOrganizer().length() > 0) {
					criteria.add(Restrictions.like("organizer", dto.getOrganizer() + "%"));
				}

				if (dto.getEventStatus() != null && dto.getEventStatus().length() > 0) {
					criteria.add(Restrictions.like("eventStatus", dto.getEventStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new DatabaseException("Exception in Event search");
		} finally {
			session.close();
		}

		return list;
	}
}