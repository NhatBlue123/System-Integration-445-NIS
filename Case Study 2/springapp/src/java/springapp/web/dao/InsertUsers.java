/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.dao;

/**
 *
 * @author Bluez
 */
import org.hibernate.Session;
import org.hibernate.Transaction;
import springapp.web.model.Users;
import springapp.web.model.HibernateUtil;

public class InsertUsers {
    public static void main(String[] args) {
        int totalUsers = 3;
        int batchSize = 1000;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            for (int i = 1; i <= totalUsers; i++) {
                Users user = new Users();
                user.setUserName("user" + i);
                user.setPassword("pass" + i);  // Hoặc mã hóa tùy theo ứng dụng
                user.setEmail("user" + i + "@example.com");
                user.setActive(true);

                session.save(user);

                // Xử lý batch để giảm memory
                if (i % batchSize == 0) {
                    session.flush();
                    session.clear();
                    System.out.println("Inserted: " + i);
                }
            }

            tx.commit();
            System.out.println("✅ DONE inserting 500,000 users!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

