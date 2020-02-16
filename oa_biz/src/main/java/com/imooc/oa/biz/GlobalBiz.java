package com.imooc.oa.biz;

import com.imooc.oa.entity.Employee;

public interface GlobalBiz {
    public Employee login(String sn,String password);

    public void changePassword(Employee employee);

}
