package com.alicyu.springcloud.service;

import com.alicyu.springcloud.entities.dbone.Dept;

import java.util.List;

/**
 * @author zph
 * @classname DeptService
 * @description TODO
 * @date 2019/9/8 10:26
 */
public interface DeptService {
    public boolean add(Dept dept);
    public Dept    get(Long id);
    public List<Dept> list();
}
