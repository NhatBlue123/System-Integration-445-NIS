/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.dao;

import java.util.List;
import org.hibernate.Session;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;

/**
 *
 * @author AnhDao
 */
public class UserDao extends Users {

    public Boolean CheckUser(String username) {
        Boolean returnBool;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            List lstUser = session.createQuery("from Users where User_Name=:username").setParameter("username", username).list();
            returnBool = !lstUser.isEmpty();
            session.getTransaction().commit();
        } catch (Exception e) {
            returnBool = false;
        }
        return returnBool;
    }
    
   public Users getUserByUserName(String username) {
    Users user = null;
    try {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        user = (Users) session
                .createQuery("from Users where User_Name = :username")
                .setParameter("username", username)
                .uniqueResult();

        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return user;
}

    
    public boolean addUser(Users newUser) {
        boolean result = false;
        try {
            // Khởi tạo session từ Hibernate
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            // Lưu người dùng mới vào cơ sở dữ liệu
            session.save(newUser);

            // Commit transaction
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }


    
    

//    public Boolean Login(String username, String password) {
//        Boolean returnBool = false;
//        try {
//            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            List lstUser = session.createQuery("from Users where User_Name=:username and Password=:password").setParameter("username", username).setParameter("password", password).list();
//            if(lstUser.isEmpty() == false)
//            {
//                returnBool = true;
//            }
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            returnBool = false;
//        }
//        return returnBool;
//    }
    public Boolean Login(String username, String password) {
    Boolean returnBool = false;
    try {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

       List<Users> lstUser = session
    .createQuery("from Users where userName = :username and password = :password")
    .setParameter("username", username)
    .setParameter("password", password)
    .list();


        System.out.println(">>> Login query size = " + lstUser.size());

        if (!lstUser.isEmpty()) {
            returnBool = true;
        }

        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
        returnBool = false;
    }
    return returnBool;
}


    public List<Users> listUser() {
        List<Users> list;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from Users").list();
        } catch (Exception e) {
            list = null;
        }
        return list;
    }
    
    
    
    
}
