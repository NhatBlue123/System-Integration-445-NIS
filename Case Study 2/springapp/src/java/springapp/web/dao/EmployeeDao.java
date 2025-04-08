/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.dao;

/**
 *
 * @author Bluez
 */
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import springapp.web.model.Employee;
import springapp.web.model.HibernateUtil;
@Repository
public class EmployeeDao {
    
    public void insert(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            session.save(employee);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    public void insertBatch(List<Employee> employees) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    try {
        for (int i = 0; i < employees.size(); i++) {
            session.save(employees.get(i));

            if (i % 50 == 0) { // Flush theo batch nhỏ để giảm memory
                session.flush();
                session.clear();
            }
        }
        tx.commit();
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        throw e;
    } finally {
        session.close();
    }
    
    
}
    public int getEmployeeCount() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
        Long count = (Long) session.createQuery("select count(e) from Employee e").uniqueResult();
        return count.intValue();
    } finally {
        session.close();
    }
}


}
