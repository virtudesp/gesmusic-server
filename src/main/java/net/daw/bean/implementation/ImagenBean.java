/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * sisane-server: Helps you to develop easily AJAX web applications 
 *                   by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/sisane-server
 * 
 * sisane-server is distributed under the MIT License (MIT)
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
import java.util.Date;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.PruebaDao;
import net.daw.dao.implementation.TecnicaDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class ImagenBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String descripcion;
    @Expose
    private Date fecha;
    @Expose
    private String ubicacion;

    @Expose(serialize = false)
    private Integer id_tecnica = 0;
    @Expose(deserialize = false)
    private TecnicaBean obj_tecnica;

    @Expose(serialize = false)
    private Integer id_prueba = 0;
    @Expose(deserialize = false)
    private PruebaBean obj_prueba;

    public ImagenBean() {
    }

    public ImagenBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getId_tecnica() {
        return id_tecnica;
    }

    public void setId_tecnica(Integer id_tecnica) {
        this.id_tecnica = id_tecnica;
    }

    public TecnicaBean getObj_tecnica() {
        return obj_tecnica;
    }

    public void setObj_tecnica(TecnicaBean obj_tecnica) {
        this.obj_tecnica = obj_tecnica;
    }

    public Integer getId_prueba() {
        return id_prueba;
    }

    public void setId_prueba(Integer id_prueba) {
        this.id_prueba = id_prueba;
    }

    public PruebaBean getObj_prueba() {
        return obj_prueba;
    }

    public void setObj_prueba(PruebaBean obj_prueba) {
        this.obj_prueba = obj_prueba;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "descripcion,";
        strColumns += "fecha,";
        strColumns += "ubicacion,";
        strColumns += "id_tecnica,";
        strColumns += "id_prueba";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(descripcion) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha) + ",";
        strColumns += EncodingUtilHelper.quotate(ubicacion) + ",";
        strColumns += id_tecnica + ",";
        strColumns += id_prueba;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strColumns = "";
        //strColumns +="id="; id + ";
        strColumns += "descripcion=" + EncodingUtilHelper.quotate(descripcion) + ",";
        strColumns += "fecha=" + EncodingUtilHelper.stringifyAndQuotate(fecha) + ",";
        strColumns += "ubicacion=" + EncodingUtilHelper.quotate(ubicacion) + ",";
        strColumns += "id_tecnica=" + id_tecnica + ",";
        strColumns += "id_prueba=" + id_prueba;
        return strColumns;
    }

    @Override
    public ImagenBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        this.setFecha(oResultSet.getTimestamp("fecha"));
        this.setUbicacion(oResultSet.getString("ubicacion"));

        if (expand > 0) {
            TecnicaBean oTecnicaBean = new TecnicaBean();
            TecnicaDao oTecnicaDao = new TecnicaDao(pooledConnection, oPuserBean_security, null);
            oTecnicaBean.setId(oResultSet.getInt("id_tecnica"));
            oTecnicaBean = oTecnicaDao.get(oTecnicaBean, expand - 1);
            this.setObj_tecnica(oTecnicaBean);
        } else {
            this.setId_tecnica(oResultSet.getInt("id_tecnica"));
        }

        if (expand > 0) {
            PruebaBean oPruebaBean = new PruebaBean();
            PruebaDao oPruebaDao = new PruebaDao(pooledConnection, oPuserBean_security, null);
            oPruebaBean.setId(oResultSet.getInt("id_tecnica"));
            oPruebaBean = oPruebaDao.get(oPruebaBean, expand - 1);
            this.setObj_prueba(oPruebaBean);
        } else {
            this.setId_prueba(oResultSet.getInt("id_tecnica"));
        }

        return this;
    }

}
