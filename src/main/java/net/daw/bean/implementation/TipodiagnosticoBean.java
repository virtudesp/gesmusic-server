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
import net.daw.dao.implementation.ServicioDao;
import net.daw.dao.implementation.TipousuarioDao;
import net.daw.helper.statics.EncodingUtilHelper;
import static net.daw.helper.statics.MetaEnum.FieldType.Date;

public class TipodiagnosticoBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String descripcion;

    public TipodiagnosticoBean() {
    }

    public TipodiagnosticoBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return the dni
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param dni the dni to set
     */
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
        strColumns += EncodingUtilHelper.quotate(getDescripcion());
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "descripcion=" + EncodingUtilHelper.quotate(getDescripcion());
        return strPairs;
    }

    @Override
    public TipodiagnosticoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        
        return this;
    }

}
