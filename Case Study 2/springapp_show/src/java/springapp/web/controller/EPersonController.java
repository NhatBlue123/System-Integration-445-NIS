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

@Controller
@RequestMapping(value = "/admin")
public class EPersonController {

    @RequestMapping(value = "/EPerson", method = RequestMethod.GET)
    public String listEPerson(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        String value = "";
        if (user != null) {
            try {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                List listEPersons = session.createQuery("from EPerson").list();
                model.addAttribute("listEPersons", listEPersons);
                session.getTransaction().commit();
                value = "admin/EPerson";
            } catch (Exception e) {
                value = "admin/EPerson";
            }
        } else {
                value = "admin/EPerson";
        }
        return value;
    }
} 