/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean.implementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.helper.statics.EncodingUtilHelper;

public class ActoBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String nombre_general;
    @Expose
    private String nombre_particular;
    @Expose
    private String lugar;
    @Expose
    private Date fecha;

    public ActoBean() {
    }

    public ActoBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre_general() {
        return nombre_general;
    }

    public void setNombre_general(String nombre_general) {
        this.nombre_general = nombre_general;
    }

    public String getNombre_particular() {
        return nombre_particular;
    }

    public void setNombre_particular(String nombre_particular) {
        this.nombre_particular = nombre_particular;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "nombre_general,";
        strColumns += "nombre_particular,";
        strColumns += "fecha,";
        strColumns += "lugar";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(getNombre_general()) + ",";
        strColumns += EncodingUtilHelper.quotate(getNombre_particular()) + ",";
        strColumns += EncodingUtilHelper.quotate(getLugar()) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha);
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "nombre_general=" + EncodingUtilHelper.quotate(getNombre_general()) + ",";
        strPairs += "nombre_particular=" + EncodingUtilHelper.quotate(getNombre_particular()) + ",";
        strPairs += "lugar=" + EncodingUtilHelper.quotate(getLugar()) + ",";
        strPairs += "fecha=" + EncodingUtilHelper.stringifyAndQuotate(fecha);
        return strPairs;
    }

    @Override
    public ActoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNombre_general(oResultSet.getString("nombre_general"));
        this.setNombre_particular(oResultSet.getString("nombre_particular"));
        this.setLugar(oResultSet.getString("lugar"));
        this.setFecha(oResultSet.getDate("fecha"));

        return this;
    }

}
