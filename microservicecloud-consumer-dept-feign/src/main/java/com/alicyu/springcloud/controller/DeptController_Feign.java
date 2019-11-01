package com.alicyu.springcloud.controller;

import com.alicyu.springcloud.entities.dbone.Dept;
import com.alicyu.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zph
 * @classname DeptController_Consumer
 * @description TODO
 * @date 2019/9/4 20:39
 */
@RestController
public class DeptController_Feign {

    @Autowired
    private DeptClientService service = null;

    @RequestMapping(value = "/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id)
    {
        return this.service.get(id);
    }
    @RequestMapping(value = "/consumer/dept/getnew",method= RequestMethod.GET)
    public Dept getnew(@RequestParam Long id, @RequestParam String aaa)
    {
        return this.service.get(id);
    }

    @RequestMapping(value = "/consumer/dept/list")
    public List<Dept> list()
    {
        return this.service.list();
    }

    @RequestMapping(value = "/consumer/dept/add")
    public Object add(Dept dept)
    {
        return this.service.add(dept);
    }

}
