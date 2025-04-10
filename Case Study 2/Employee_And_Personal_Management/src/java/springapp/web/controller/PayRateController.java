/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;

/**
 *
 * @author AnhDao
 */
@Controller
@RequestMapping(value = "/admin")
public class PayRateController {

    @RequestMapping(value = {"/payrates/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        String value = "";
        if (user != null) {
            try {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                List listPayRates = session.createQuery("from PayRates").list();
                model.addAttribute("listPayRates", listPayRates);
                session.getTransaction().commit();
                value = "admin/listPayRate";
            } catch (Exception e) {
                value = "admin/listPayRate";
            }

        } else {
            model.addAttribute("user", new Users());
            value= "redirect:/admin/login.html";
        }
        return value;
    }
}