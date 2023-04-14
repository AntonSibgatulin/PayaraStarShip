package my.compary.database;


import my.compary.hibernate.HibernateUtils;
import my.compary.psixol.SpaceMarine;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import my.compary.psixol.StarShip;
import my.compary.psixol.StarShipType;

import java.util.List;

public class DatabaseModel {

    public static final Long default_long = 9L;

    public DatabaseModel() {

    }


    public StarShip starShip(Long id) {
        Session session = HibernateUtils.sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from StarShip s WHERE s.id = " + id);
        StarShip starShip = (StarShip) query.uniqueResult();
        session.getTransaction().commit();

        if(starShip==null)return starShip(default_long);
        starShip.setStarShipTypes(StarShipType.valueOf(starShip.starShipType));
        return starShip;
    }

    public StarShip checkStarShip(Long id) {
        Session session = HibernateUtils.sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from StarShip s WHERE s.id = " + id);
        StarShip starShip = (StarShip) query.uniqueResult();
        session.getTransaction().commit();

        if(starShip==null)return null;
        starShip.setStarShipTypes(StarShipType.valueOf(starShip.starShipType));
        return starShip;
    }






    public void saveStarShip(StarShip starShip){
        starShip.setId(null);
        Session session = HibernateUtils.sessionFactory.openSession();
        session.beginTransaction();
        session.save(starShip);
        session.getTransaction().commit();
    }





    public void updateStarShip(StarShip starShip){

        Session session = HibernateUtils.sessionFactory.openSession();
        session.beginTransaction();
        session.update(starShip);
        session.getTransaction().commit();

    }
}
