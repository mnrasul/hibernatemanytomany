package ca;

/**
 * Created by nrasul on 12/18/14.
 */
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();


        Meeting meeting1 = new Meeting("Quaterly Status meeting");
        Meeting meeting2 = new Meeting("Weekly Status meeting");
        Meeting meeting3 = new Meeting("daily Status meeting");

        Employee employee1 = new Employee("Gingerly", "Brint");
        Employee employee2 = new Employee("Mary", "Kate");

        employee1.getMeetings().add(meeting1);
        employee1.getMeetings().add(meeting2);
        employee2.getMeetings().add(meeting1);
        employee2.getMeetings().add(meeting3);

        meeting1.getEmployees().add(employee1);
        meeting1.getEmployees().add(employee2);
        meeting2.getEmployees().add(employee1);
        meeting3.getEmployees().add(employee2);

        session.save(employee1);
        session.save(employee2);

        Criteria criteria = session.createCriteria(Employee.class, "e");
        criteria.setFetchMode("e.meetings", FetchMode.JOIN);
        criteria = criteria.createAlias("e.meetings", "m", JoinType.LEFT_OUTER_JOIN);
        final ProjectionList projections = Projections.projectionList()
//                .add(Projections.property("e.employeeId"))
                .add(Projections.property("e.firstname"))
//                .add(Projections.property("e.lastname"))
//                .add(Projections.property("e.meetings"))
//                .add(Projections.property("m.meetingId"))
                .add(Projections.property("m.subject"))
//                .add(Projections.property("m.meetingDate"))
//                .add(Projections.property("m.subject"))
                ;
        criteria.setProjection(projections);
//        criteria.setResultTransformer(new MeetingEmployeeResultTransformer());
        final List<Object> list = criteria.list();

//        for (Employee e: list) {
//            for (Meeting m: e.getMeetings()){
//                System.out.println(e.getLastname() + " : " + m.getMeetingId());
//            }
//
//        }
        session.getTransaction().commit();
        session.close();
    }
}
