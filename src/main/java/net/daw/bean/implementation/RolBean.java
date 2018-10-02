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
public class RolBean implements GenericBean{
    
    @Expose
    private int id;
    @Expose
    private String rol;
    
    public RolBean(int id){
        this.id = id;
    }
    
    public RolBean(){
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
    @Override
    public String getColumns() {
        return "id,rol";
    }
    
    @Override
    public String getValues() {
        return id + "," + EncodingUtilHelper.quotate(rol);
    }

    @Override
    public String toPairs() {
        
        String strPairs = "";
//        pairs  = "id = " + id + ",";
        strPairs += "rol = " + EncodingUtilHelper.quotate(rol);
        return strPairs;
    }

    @Override
    public RolBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        
        this.id = oResultSet.getInt("id");
        this.rol = oResultSet.getString("rol");
        
        return this; 
    }
    
}
