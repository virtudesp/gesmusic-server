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
import net.daw.dao.implementation.ObraDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class ArchivoBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private Date alta;
    @Expose
    private String origen;
    @Expose
    private String arreglo;
    
    @Expose(serialize = false)
    private Integer id_obra; 
    @Expose(deserialize = false)
    private ObraBean obj_obra = null;   
    
    public ArchivoBean() {
    }

    public ArchivoBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getArreglo() {
        return arreglo;
    }

    public void setArreglo(String arreglo) {
        this.arreglo = arreglo;
    }

    public Integer getId_obra() {
        return id_obra;
    }

    public void setId_obra(Integer id_obra) {
        this.id_obra = id_obra;
    }
    
    public ObraBean getObj_obra() {
        return obj_obra;
    }

    public void setObj_obra(ObraBean obj_obra) {
        this.obj_obra = obj_obra;
    }

    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "alta,";
        strColumns += "origen,";
        strColumns += "arreglo,";
        strColumns += "id_obra";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        //strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha);
        /*Hay que comprobar si la fecha es null porque si es null inserta la fecha actual del sistema*/
        if (alta == null) {
            strColumns += "null";
        } else {
            strColumns += EncodingUtilHelper.stringifyAndQuotate(alta);
        }
        strColumns += EncodingUtilHelper.quotate(getOrigen()) + ",";
        strColumns += EncodingUtilHelper.quotate(getArreglo()) + ",";
        strColumns += id_obra;        
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = ""; 
//        strPairs += "alta=" + EncodingUtilHelper.stringifyAndQuotate(alta);       
//        strPairs += "origen=" + EncodingUtilHelper.quotate(getOrigen()) + ",";
//        strPairs += "arreglo=" + EncodingUtilHelper.quotate(getArreglo()) + ",";
//        strPairs += "id_obra=" + getObra();
        Boolean hay = false;
        if (alta != null) {
            strPairs += "alta=" + EncodingUtilHelper.stringifyAndQuotate(alta);
            hay = true;
        }
        if (origen != null) {
            strPairs += (hay) ? ",origen=" : "origen=";
            strPairs += EncodingUtilHelper.quotate(getOrigen());
            hay = true;
        }
        if (arreglo != null) {
            strPairs += (hay) ? ",arreglo=" : "arreglo=";
            strPairs += EncodingUtilHelper.quotate(getArreglo());
            hay = true;
        }
        if (id_obra != null) {
            strPairs += (hay) ? ",id_obra=" : "id_obra=";
            strPairs += getId_obra();
        }
        return strPairs;
    }

    @Override
    public ArchivoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setAlta(oResultSet.getTimestamp("alta"));
        this.setOrigen(oResultSet.getString("origen"));
        this.setArreglo(oResultSet.getString("arreglo"));
        
        if (expand > 0) {
            ObraBean oObraBean = new ObraBean();
            ObraDao oObraDao = new ObraDao(pooledConnection, oPusuarioBean_security, null);
            oObraBean.setId(oResultSet.getInt("id_obra"));
            oObraBean = oObraDao.get(oObraBean, expand - 1);
            this.setObj_obra(oObraBean);
        } else {
            this.setId_obra(oResultSet.getInt("id_obra"));
        }

        return this;
    }

}
