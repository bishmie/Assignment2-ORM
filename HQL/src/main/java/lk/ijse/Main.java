package lk.ijse;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.entity.Batch;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student(1001, "bishmi malshi", 22, "kalutara", null);
        Student student2 = new Student(1002, "dimalki Perera", 24, "galle", null);
        Student student3 = new Student(1003, "jehan dias", 18, "kandy", null);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        Batch batch = new Batch();
        batch.setBid(2001);
        batch.setBname("B001");
        batch.setBdescription("Software Engineering");
        batch.setStudents(students);

        student1.setBatch(batch);
        student2.setBatch(batch);
        student3.setBatch(batch);

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        //HQL Query - INSERT

        session.save(batch);

        session.save(student1);
        session.save(student2);
        session.save(student3);

        transaction.commit();
        session.close();

        Session session2 = FactoryConfiguration.getInstance().getSession();
        Transaction transaction2 = session2.beginTransaction();


        //HQL Query - DELETE
        Query query1 = session2.createQuery("DELETE FROM Student WHERE sid=:sid");
        query1.setParameter("sid", 1004);

        System.out.println("Deleted : "+query1.executeUpdate());

        //HQL Query - UPDATE
        Query query2 = session2.createQuery("UPDATE Student SET sname=:sname WHERE sid=:sid");
        query2.setParameter("sname", "Kamal Perera");
        query2.setParameter("sid", 1003);

        System.out.println("Updated : "+query2.executeUpdate());

        // HQL Query - search by column name
        Query query3 = session2.createQuery("SELECT sid, sname, age FROM Student WHERE sid=?1");
        query3.setParameter(1, 1001);

        Object[] obj1 = (Object[]) query3.uniqueResult();

        System.out.println("Student : "+obj1[0]+" "+obj1[1]+" "+obj1[2]);

        // HQL Query - get multiple columns
        Query query4 = session2.createQuery("SELECT sid, sname FROM Student");

        List<Object[]> objects = query4.list();
        for (Object[] obj : objects) {
            System.out.println("Student : "+obj[0]+" "+obj[1]);
        }

        //HQL Query - join query
        Query query5 = session2.createQuery("SELECT s.sid, s.sname, s.age, s.address, b.bname FROM Student s INNER JOIN s.batch b");

        List<Object[]> objects1 = query5.list();

        for (Object[] obj : objects1) {
            System.out.println("Student : "+obj[0]+" "+obj[1]+" "+obj[2]+" "+obj[3]+" "+obj[4]);
        }

        transaction2.commit();
        session2.close();

    }
}