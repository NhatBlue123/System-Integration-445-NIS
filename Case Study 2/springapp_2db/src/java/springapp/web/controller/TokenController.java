package springapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
@Controller
@RequestMapping("/admin")
public class TokenController {

    @RequestMapping(value = "/generate-token", method = RequestMethod.GET)
    @ResponseBody
    public String generateToken(@RequestParam("username") String username) {
        String token = TokenUtil.generateToken(username);
        return token;
    }
    
    
    
}


