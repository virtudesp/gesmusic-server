package net.daw.bean.implementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.DocumentoDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class CargoBean implements GenericBean {

    @Expose
    private int id;
    
    @Expose
    private Date date;
    

    
    @Expose(serialize = false)
    private int id_documento;
    
    @Expose(deserialize = false)
    private DocumentoBean obj_documento;
  

    public CargoBean(int id) {
        this.id = id;
    }

    public CargoBean() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return date;
    }

    public void setFecha(Date date) {
        this.date = date;
    }

   
    
    public int getId_documento() {
        return id_documento;
    }

    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    public DocumentoBean getObj_documento() {
        return obj_documento;
    }

    public void setObj_documento(DocumentoBean obj_documento) {
        this.obj_documento = obj_documento;
    }
    
   
    
    @Override
    public String getColumns() {
        return "id,date,id_documento";
    }

    @Override
    public String getValues() {
        String values = "";
        values += id + ",";
        values += EncodingUtilHelper.stringifyAndQuotate(date) + ",";
        values += id_documento;

        return values;
    }

    @Override
    public String toPairs() {
        String pairs;
        pairs = "id = " + id + ",";
        pairs += "date = " + EncodingUtilHelper.stringifyAndQuotate(date) + ",";
        pairs += "id_documento = " + id_documento;

        return pairs;
    }

    @Override
    public CargoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setFecha(oResultSet.getTimestamp("date"));

        if (expand > 0) {
            DocumentoBean oDocumentoBean = new DocumentoBean();
            DocumentoDao oDocumentoDao = new DocumentoDao(pooledConnection, oPusuarioBean_security, null);
            oDocumentoBean.setId(oResultSet.getInt("id_documento"));
            oDocumentoBean = oDocumentoDao.get(oDocumentoBean, expand - 1);
            this.setObj_documento(oDocumentoBean);
        } else {
            this.setId_documento(oResultSet.getInt("id_documento"));
        }
        
      
        return this;
    }

}
