package com.alicyu.springcloud.service;

import com.alicyu.springcloud.entities.dbone.Dept;
import com.alicyu.springcloud.entities.dbone.Depttwo;

import java.util.List;

/**
 * @author zph
 * @classname DeptService
 * @description TODO
 * @date 2019/9/4 20:30
 */
public interface DeptService {
    Depttwo getdepttwo(Long id);
    com.alicyu.springcloud.entities.dbtwo.Depttwo getdepttwo2(Long id);
    public boolean add(Dept dept);
    public Dept    get(Long id);
    public List<Dept> list();
}
