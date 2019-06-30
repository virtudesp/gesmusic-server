/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * bauxer server: Helps you to develop easily AJAX web applications 
 *                   by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/bauxer
 * 
 * bauxer server is distributed under the MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.daw.bean.implementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.AgrupacionDao;
import net.daw.dao.implementation.ActoDao;
//import net.daw.helper.statics.EncodingUtilHelper;

public class ParticipaBean implements GenericBean {

    @Expose
    private Integer id = 0;
    
    @Expose(serialize = false)
    private Integer id_acto;
    @Expose(deserialize = false)
    private ActoBean obj_acto = null;
    
    @Expose(serialize = false)
    private Integer id_agrupacion;
    @Expose(deserialize = false)
    private AgrupacionBean obj_agrupacion = null;

    public ParticipaBean() {
    }

    public ParticipaBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_agrupacion() {
        return id_agrupacion;
    }

    public void setId_agrupacion(Integer id_agrupacion) {
        this.id_agrupacion = id_agrupacion;
    }

    public AgrupacionBean getObj_agrupacion() {
        return obj_agrupacion;
    }

    public void setObj_agrupacion(AgrupacionBean obj_agrupacion) {
        this.obj_agrupacion = obj_agrupacion;
    }

    public Integer getId_acto() {
        return id_acto;
    }

    public void setId_acto(Integer id_acto) {
        this.id_acto = id_acto;
    }

    public ActoBean getObj_acto() {
        return obj_acto;
    }

    public void setObj_acto(ActoBean obj_acto) {
        this.obj_acto = obj_acto;
    }


    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "id_acto,";
        strColumns += "id_agrupacion";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += id_acto + ",";
        strColumns += id_agrupacion;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
//        strPairs += "id_agrupacion=" + id_agrupacion + ",";
//        strPairs += "id_acto=" + id_acto;
        Boolean hay = false;
        if (id_acto != null) {
            strPairs += "id_acto=" + id_acto;
            hay = true;
        }
        if (id_agrupacion != null) {
            strPairs += (hay) ? ",id_agrupacion=" : "id_agrupacion=";
            strPairs += id_acto;
        }
        return strPairs;
    }

    @Override
    public ParticipaBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));

        if (expand > 0) {
            ActoBean oActoBean = new ActoBean();
            ActoDao oActoDao = new ActoDao(pooledConnection, oPusuarioBean_security, null);
            oActoBean.setId(oResultSet.getInt("id_acto"));
            oActoBean = oActoDao.get(oActoBean, expand - 1);
            this.setObj_acto(oActoBean);
        } else {
            this.setId_acto(oResultSet.getInt("id_acto"));
        }

        if (expand > 0) {
            AgrupacionBean oAgrupacionBean = new AgrupacionBean();
            AgrupacionDao oAgrupacionDao = new AgrupacionDao(pooledConnection, oPusuarioBean_security, null);
            oAgrupacionBean.setId(oResultSet.getInt("id_agrupacion"));
            oAgrupacionBean = oAgrupacionDao.get(oAgrupacionBean, expand - 1);
            this.setObj_agrupacion(oAgrupacionBean);
        } else {
            this.setId_agrupacion(oResultSet.getInt("id_agrupacion"));
        }

        return this;
    }

}
