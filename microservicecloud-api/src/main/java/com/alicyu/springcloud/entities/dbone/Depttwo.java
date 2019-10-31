package com.alicyu.springcloud.entities.dbone;

import java.io.Serializable;
import javax.persistence.*;

public class Depttwo implements Serializable {
    @Id
    private Long deptno;

    private String dname;

    @Column(name = "db_source")
    private String dbSource;

    private static final long serialVersionUID = 1L;

    /**
     * @return deptno
     */
    public Long getDeptno() {
        return deptno;
    }

    /**
     * @param deptno
     */
    public void setDeptno(Long deptno) {
        this.deptno = deptno;
    }

    /**
     * @return dname
     */
    public String getDname() {
        return dname;
    }

    /**
     * @param dname
     */
    public void setDname(String dname) {
        this.dname = dname;
    }

    /**
     * @return db_source
     */
    public String getDbSource() {
        return dbSource;
    }

    /**
     * @param dbSource
     */
    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deptno=").append(deptno);
        sb.append(", dname=").append(dname);
        sb.append(", dbSource=").append(dbSource);
        sb.append("]");
        return sb.toString();
    }
}