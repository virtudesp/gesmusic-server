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
import net.daw.bean.publicinterface.GenericBean;
import net.daw.helper.statics.EncodingUtilHelper;

/**
 * @author MatarredonaDS
 */

public class TipodocumentoBean implements GenericBean {
    
    @Expose
    private Integer id = 0;
    @Expose
    private String descripcion;
    
    public TipodocumentoBean() {
    }

    public TipodocumentoBean(Integer id) {
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
    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "descripcion";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(descripcion);
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strColumns = "";
        strColumns += "id=" + id + ",";
        strColumns += "description=" + EncodingUtilHelper.quotate(descripcion);
        return strColumns;
    }

    @Override
    public TipodocumentoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        
        return this;
    }
}