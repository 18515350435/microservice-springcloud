package com.alicyu.springcloud.service.impl;

import com.alicyu.springcloud.dao.dbone.DeptDao;
import com.alicyu.springcloud.dao.dbone.DepttwoMapper;
import com.alicyu.springcloud.entities.dbone.Dept;
import com.alicyu.springcloud.entities.dbone.Depttwo;
import com.alicyu.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zph
 * @classname DeptServiceImpl
 * @description TODO
 * @date 2019/9/4 20:30
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao dao ;
    @Autowired
    private DepttwoMapper depttwoMapper ;
    @Autowired
    private com.alicyu.springcloud.dao.dbtwo.DepttwoMapper2 depttwoMapper2 ;

    @Override
    public Depttwo getdepttwo(Long id)
    {
        return depttwoMapper.selectByPrimaryKey(id);
    }
    @Override
    public com.alicyu.springcloud.entities.dbtwo.Depttwo getdepttwo2(Long id)
    {
        return depttwoMapper2.selectByPrimaryKey(id);
    }
    @Override
    public boolean add(Dept dept)
    {
        return dao.addDept(dept);
    }

    @Transactional//是用了atomikos分布式事务控制，不要在显示的指定事务控制器了
    @Override
    public void addBoth()
    {
        Dept dept=new Dept();
        dept.setDeptno(1232311111L);
        dept.setDname("kaqiu");
        com.alicyu.springcloud.entities.dbtwo.Depttwo depttwo=new com.alicyu.springcloud.entities.dbtwo.Depttwo();
        depttwo.setDeptno(1232322222L);
        depttwo.setDname("kaqiu22");
        depttwoMapper2.insert(depttwo);//数据源2
        dao.addDept(dept);//数据源1
//        int a=1/0;
    }

    @Override
    public Dept get(Long id)
    {
        return dao.findById(id);
    }

    @Override
    public List<Dept> list()
    {
        return dao.findAll();
    }

}
