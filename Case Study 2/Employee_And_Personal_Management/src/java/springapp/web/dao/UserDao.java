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

    public Boolean Login(String username, String password) {
        Boolean returnBool;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            List lstUser = session.createQuery("from Users where User_name=:username and Password=:password").setParameter("username", username).setParameter("password", password).list();
            returnBool = !lstUser.isEmpty();
            session.getTransaction().commit();
        } catch (Exception e) {
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
