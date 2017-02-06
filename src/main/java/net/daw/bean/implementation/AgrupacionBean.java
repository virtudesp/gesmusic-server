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
 *
 * @author a044887852v
 */
public class AgrupacionBean implements GenericBean{
    
    @Expose
    private int id;
    @Expose
    private String agrupacion;
    
    public AgrupacionBean(int id){
        this.id = id;
    }
    
    public AgrupacionBean(){
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(String agrupacion) {
        this.agrupacion = agrupacion;
    }
    
    @Override
    public String getColumns() {
        return "id,agrupacion";
    }
    
    @Override
    public String getValues() {
        return id + "," + EncodingUtilHelper.quotate(agrupacion);
    }

    @Override
    public String toPairs() {
        String pairs;
        pairs  = "id = " + id + ",";
        pairs += "agrupacion = " + EncodingUtilHelper.quotate(agrupacion);
        return pairs;
    }

    @Override
    public AgrupacionBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        
        this.id = oResultSet.getInt("id");
        this.agrupacion = oResultSet.getString("agrupacion");
        
        return this; 
    }
    
}
