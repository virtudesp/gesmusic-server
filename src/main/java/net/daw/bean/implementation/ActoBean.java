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
    private String nombre;
    @Expose
    private String parte;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
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
        strColumns += "nombre,";
        strColumns += "parte,";
        strColumns += "lugar,";
        strColumns += "fecha";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(getNombre()) + ",";
        strColumns += EncodingUtilHelper.quotate(getParte()) + ",";
        strColumns += EncodingUtilHelper.quotate(getLugar()) + ",";
        //strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha);
        /*Hay que comprobar si la fecha es null porque si es null inserta la fecha actual del sistema*/
        if (fecha == null) {
            strColumns += "null";
        } else {
            strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha);
        }
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";        
//        strPairs += "nombre=" + EncodingUtilHelper.quotate(getnombre()) + ",";
//        strPairs += "parte=" + EncodingUtilHelper.quotate(getparte()) + ",";
//        strPairs += "lugar=" + EncodingUtilHelper.quotate(getLugar()) + ",";
//        strPairs += "fecha=" + EncodingUtilHelper.stringifyAndQuotate(fecha);
        Boolean hay = false;
        if (nombre != null) {
            strPairs += "nombre=" + EncodingUtilHelper.quotate(nombre);
            hay = true;
        }
        if (parte != null) {
            strPairs += (hay) ? ",parte=" : "parte=";
            strPairs += EncodingUtilHelper.quotate(parte);
            hay = true;
        }
        if (lugar != null) {
            strPairs += (hay) ? ",lugar=" : "lugar=";
            strPairs += EncodingUtilHelper.quotate(lugar);
            hay = true;
        }
        if (fecha != null) {
            strPairs += (hay) ? ",fecha=" : "fecha=";
            strPairs += EncodingUtilHelper.stringifyAndQuotate(fecha);
        }
        return strPairs;
    }

    @Override
    public ActoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setParte(oResultSet.getString("parte"));
        this.setLugar(oResultSet.getString("lugar"));
        this.setFecha(oResultSet.getDate("fecha"));

        return this;
    }

}
