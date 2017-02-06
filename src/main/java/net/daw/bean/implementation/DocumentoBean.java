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
import net.daw.dao.implementation.TipodocumentoDao;
import net.daw.helper.statics.EncodingUtilHelper;

/**
 *
 * @author a003153722p
 */
public class DocumentoBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String descripcion;
    @Expose
    private Date fecha;

    @Expose(serialize = false)
    private Integer id_tipodocumento = 0;
    @Expose(deserialize = false)
    private TipodocumentoBean obj_tipodocumento = null;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId_tipodocumento() {
        return id_tipodocumento;
    }

    public void setId_tipodocumento(Integer id_tipodocumento) {
        this.id_tipodocumento = id_tipodocumento;
    }

    public TipodocumentoBean getObj_tipodocumento() {
        return obj_tipodocumento;
    }

    public void setObj_tipodocumento(TipodocumentoBean obj_tipodocumento) {
        this.obj_tipodocumento = obj_tipodocumento;
    }

    public DocumentoBean() {
    }

    public DocumentoBean(Integer id) {
        this.id = id;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "descripcion,";
        strColumns += "fecha,";
        strColumns += "id_tipodocumento";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(descripcion) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha) + ",";
        strColumns += id_tipodocumento;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        //strPairs += "id=" + id + ",";
        strPairs += "descripcion=" + EncodingUtilHelper.quotate(descripcion) + ",";
        strPairs += "fecha=" + EncodingUtilHelper.stringifyAndQuotate(fecha) + ",";
        strPairs += "id_tipodocumento=" + id_tipodocumento;
        return strPairs;
    }

    @Override
    public GenericBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        this.setFecha(oResultSet.getTimestamp("fecha"));

        if (expand > 0) {
            TipodocumentoBean oTipodocumentoBean = new TipodocumentoBean();
            TipodocumentoDao ooTipodocumentoDao = new TipodocumentoDao(pooledConnection, oPusuarioBean_security, null);
            oTipodocumentoBean.setId(oResultSet.getInt("id_tipodocumento"));
            oTipodocumentoBean = ooTipodocumentoDao.get(oTipodocumentoBean, expand - 1);
            this.setObj_tipodocumento(oTipodocumentoBean);
        } else {
            this.setId_tipodocumento(oResultSet.getInt("id_tipodocumento"));
        }
        return this;
    }
}
