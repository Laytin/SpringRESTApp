package com.laytin.SpringRESTApp.dao;

import com.laytin.SpringRESTApp.models.City;
import com.laytin.SpringRESTApp.models.Trip;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Component
public class TripDAO {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public TripDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<Trip> getTrips(LocalDate ld, City place_from, City place_to, int sits, int page){
        Session s = entityManager.unwrap(Session.class);
        Query q = s.createQuery("SELECT t from Trip t join fetch t.car where t.place_from=?1 and t.place_to=?2 and t.free_sits>?3 " +
                " and t.tm between :do and :after and t.tm>current_timestamp");
                q.setParameter(1,place_from)
                .setParameter(2,place_to)
                .setParameter(3,sits)
                .setParameter("do",Timestamp.valueOf(ld.atStartOfDay()))
                .setParameter("after",Timestamp.valueOf(ld.atStartOfDay().plusDays(1)))
                        .setFirstResult((page-1)*10);
        return q.getResultList();
    }
}
