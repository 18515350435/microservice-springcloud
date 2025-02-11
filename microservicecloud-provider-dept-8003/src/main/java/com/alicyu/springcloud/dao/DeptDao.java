package com.alicyu.springcloud.dao;

import com.alicyu.springcloud.entities.dbone.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zph
 * @classname DeptDao
 * @description TODO
 * @date 2019/9/4 20:28
 */
@Mapper
public interface DeptDao {
    public boolean addDept(Dept dept);

    public Dept findById(Long id);

    public List<Dept> findAll();
}
