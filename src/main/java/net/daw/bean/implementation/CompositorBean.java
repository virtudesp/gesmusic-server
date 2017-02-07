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
import net.daw.helper.statics.EncodingUtilHelper;

public class CompositorBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String nombre;
    @Expose
    private String apellidos;
    @Expose
    private Date fecha_nac;
    @Expose
    private Date fecha_def;
    @Expose
    private String lugar_nac;
    @Expose
    private String lugar_def;

    public CompositorBean() {
    }

    public CompositorBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Date getFecha_def() {
        return fecha_def;
    }

    public void setFecha_def(Date fecha_def) {
        this.fecha_def = fecha_def;
    }

    public String getLugar_nac() {
        return lugar_nac;
    }

    public void setLugar_nac(String lugar_nac) {
        this.lugar_nac = lugar_nac;
    }

    public String getLugar_def() {
        return lugar_def;
    }

    public void setLugar_def(String lugar_def) {
        this.lugar_def = lugar_def;
    }

    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "nombre,";
        strColumns += "apellidos,";
        strColumns += "fecha_nac,";
        strColumns += "fecha_def,";
        strColumns += "lugar_nac,";
        strColumns += "lugar_def";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(nombre) + ",";
        strColumns += EncodingUtilHelper.quotate(apellidos) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_nac) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_def) + ",";
        strColumns += EncodingUtilHelper.quotate(lugar_nac) + ",";
        strColumns += EncodingUtilHelper.quotate(lugar_def);
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strColumns = "";
        strColumns += "nombre=" + EncodingUtilHelper.quotate(nombre) + ",";
        strColumns += "apellidos=" + EncodingUtilHelper.quotate(apellidos) + ",";
        strColumns += "fecha_nac=" + EncodingUtilHelper.stringifyAndQuotate(fecha_nac) + ",";
        strColumns += "fecha_def=" + EncodingUtilHelper.stringifyAndQuotate(fecha_def) + ",";
        strColumns += "lugar_nac=" + EncodingUtilHelper.quotate(lugar_nac) + ",";
        strColumns += "lugar_def=" + EncodingUtilHelper.quotate(lugar_def);
        return strColumns;
    }

    @Override
    public CompositorBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApellidos(oResultSet.getString("apellidos"));
        this.setFecha_nac(oResultSet.getTimestamp("fecha_nac"));
        this.setFecha_def(oResultSet.getTimestamp("fecha_def"));
        this.setLugar_nac(oResultSet.getString("lugar_nac"));
        this.setLugar_def(oResultSet.getString("lugar_def"));

        return this;
    }

}
