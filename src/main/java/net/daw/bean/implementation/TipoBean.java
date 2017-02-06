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
public class TipoBean implements GenericBean{
    
    @Expose
    private int id;
    @Expose
    private String descripcion;
    
    public TipoBean(int id){
        this.id = id;
    }
    
    public TipoBean(){
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "id,descripcion";
    }
    
    @Override
    public String getValues() {
        return id + "," + EncodingUtilHelper.quotate(descripcion);
    }

    @Override
    public String toPairs() {
        String pairs;
        pairs  = "id = " + id + ",";
        pairs += "descripcion = " + EncodingUtilHelper.quotate(descripcion);
        return pairs;
    }

    @Override
    public TipoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        
        this.id = oResultSet.getInt("id");
        this.descripcion = oResultSet.getString("descripcion");
        
        return this;
        
        
    }
    
}
