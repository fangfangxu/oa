package com.imooc.oa.controller;

import com.imooc.oa.biz.DepartmentBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Map;


@Controller
@RequestMapping("/department")
public class DepartmentController {
@Autowired
private DepartmentBiz departmentBiz;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",departmentBiz.getAll());
        return "department_list";
    }


    @RequestMapping("/list1")
    @ResponseBody
    public String list1(Map<String,Object> map){
        map.put("list",departmentBiz.getAll());
        return "徐";
    }
}
