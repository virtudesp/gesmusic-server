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
import net.daw.dao.implementation.EntidadDao;
import net.daw.dao.implementation.RolDao;
//import net.daw.helper.statics.EncodingUtilHelper;

public class ElencoBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose(serialize = false)
    private Integer id_entidad = 0;
    @Expose(deserialize = false)
    private EntidadBean obj_entidad = null;
    @Expose(serialize = false)
    private Integer id_agrupacion = 0;
    @Expose(deserialize = false)
    private AgrupacionBean obj_agrupacion = null;
    @Expose(serialize = false)
    private Integer id_rol = 0;
    @Expose(deserialize = false)
    private RolBean obj_rol = null;

    public ElencoBean() {
    }

    public ElencoBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(Integer id_entidad) {
        this.id_entidad = id_entidad;
    }

    public EntidadBean getObj_entidad() {
        return obj_entidad;
    }

    public void setObj_entidad(EntidadBean obj_entidad) {
        this.obj_entidad = obj_entidad;
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

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public RolBean getObj_rol() {
        return obj_rol;
    }

    public void setObj_rol(RolBean obj_rol) {
        this.obj_rol = obj_rol;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "id_entidad,";
        strColumns += "id_agrupacion,";
        strColumns += "id_rol";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += id_entidad + ",";
        strColumns += id_agrupacion + ",";
        strColumns += id_rol;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "id_entidad=" + id_entidad + ",";
        strPairs += "id_agrupacion=" + id_agrupacion + ",";
        strPairs += "id_rol=" + id_rol;
        return strPairs;
    }

    @Override
    public ElencoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        if (expand > 0) {
            EntidadBean oEntidadBean = new EntidadBean();
            EntidadDao oEntidadDao = new EntidadDao(pooledConnection, oPusuarioBean_security, null);
            oEntidadBean.setId(oResultSet.getInt("id_entidad"));
            oEntidadBean = oEntidadDao.get(oEntidadBean, expand - 1);
            this.setObj_entidad(oEntidadBean);
        } else {
            this.setId_entidad(oResultSet.getInt("id_entidad"));
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

        if (expand > 0) {
            RolBean oRolBean = new RolBean();
            RolDao oRolDao = new RolDao(pooledConnection, oPusuarioBean_security, null);
            oRolBean.setId(oResultSet.getInt("id_rol"));
            oRolBean = oRolDao.get(oRolBean, expand - 1);
            this.setObj_rol(oRolBean);
        } else {
            this.setId_rol(oResultSet.getInt("id_rol"));
        }

        return this;
    }

}
