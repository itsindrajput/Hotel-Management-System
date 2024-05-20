package org.example;
import org.example.config.Hibernate;
import org.example.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class App
{
    public static void main( String[] args )
    {
        Session session= Hibernate.getSession();
        Transaction transaction=null;
        try {
            transaction = session.beginTransaction();
            Room room = new Room();
            room.setRoomNumber("104");
            room.setRoomType("Double");
            room.setPrice(500.0);


            session.save(room);
            transaction.commit();
            System.out.println("Record inserted sucessessfully");
        }catch (HibernateException e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

}
