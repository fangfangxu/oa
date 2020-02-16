package com.imooc.oa.controller;

import com.imooc.oa.biz.GlobalBiz;
import com.imooc.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller("globalController")

public class GlobalController {

    @Autowired
    private GlobalBiz globalBiz;


    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpSession session,@RequestParam String sn, @RequestParam String password){
        Employee employee=globalBiz.login(sn,password);
        if(employee==null){
           return "redirect:to_login";
        }
        session.setAttribute("employee",employee);
        return "redirect:self";
    }

    /**
     * 個人信息
     * @return
     */
    @RequestMapping("/self")
    public String self(){
      return "self";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "login";
    }


    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session,@RequestParam String old,
                                 @RequestParam String new1,@RequestParam String new2){
        Employee employee1=(Employee)session.getAttribute("employee");
        if(employee1.getPassword().equals(old) && new1.equals(new2)){
            employee1.setPassword(new2);
            globalBiz.changePassword(employee1);
//            session.setAttribute("employee",null);
            return "self";
        }else{
            return "to_change_password";
        }
    }
}
