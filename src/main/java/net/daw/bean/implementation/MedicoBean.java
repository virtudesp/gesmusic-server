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
import net.daw.dao.implementation.ServicioDao;
import net.daw.dao.implementation.EspecialidadDao;
//import net.daw.helper.statics.EncodingUtilHelper;

public class MedicoBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose(serialize = false)
    private Integer id_servicio = 0;
    @Expose(deserialize = false)
    private ServicioBean obj_servicio = null;
    @Expose(serialize = false)
    private Integer id_especialidad = 0;
    @Expose(deserialize = false)
    private EspecialidadBean obj_especialidad = null;

    public Integer getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(Integer id_servicio) {
        this.id_servicio = id_servicio;
    }

    public ServicioBean getObj_servicio() {
        return obj_servicio;
    }

    public void setObj_servicio(ServicioBean obj_servicio) {
        this.obj_servicio = obj_servicio;
    }

    public Integer getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(Integer id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public EspecialidadBean getObj_especialidad() {
        return obj_especialidad;
    }

    public void setObj_especialidad(EspecialidadBean obj_especialidad) {
        this.obj_especialidad = obj_especialidad;
    }

    public MedicoBean() {
    }

    public MedicoBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "id_servicio,";
        strColumns += "id_especialidad";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += id_servicio+ ",";
        strColumns += id_especialidad;
        return strColumns;
    }

    @Override
    public String toPairs() {

        String strPairs = "";
        //strPairs += "id=" + id + ",";
        strPairs += "id_servicio=" + id_servicio + ",";
        strPairs += "id_especialidad=" + id_especialidad;
        return strPairs;
    }

    @Override
    public MedicoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        if (expand > 0) {
            ServicioBean oServicioBean = new ServicioBean();
            ServicioDao oServicioDao = new ServicioDao(pooledConnection, oPusuarioBean_security, null);
            oServicioBean.setId(oResultSet.getInt("id_servicio"));
            oServicioBean = oServicioDao.get(oServicioBean, expand - 1);
            this.setObj_servicio(oServicioBean);
        } else {
            this.setId_servicio(oResultSet.getInt("id_servicio"));
        }
        
        if (expand > 0) {
            EspecialidadBean oEspecialidadBean = new EspecialidadBean();
            EspecialidadDao oEspecialidadDao = new EspecialidadDao(pooledConnection, oPusuarioBean_security, null);
            oEspecialidadBean.setId(oResultSet.getInt("id_especialidad"));
            oEspecialidadBean = oEspecialidadDao.get(oEspecialidadBean, expand - 1);
            this.setObj_especialidad(oEspecialidadBean);
        } else {
            this.setId_especialidad(oResultSet.getInt("id_especialidad"));
        }
        
        return this;
    }

}
