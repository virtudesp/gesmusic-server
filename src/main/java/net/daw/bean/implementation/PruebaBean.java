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
import net.daw.dao.implementation.EpisodioDao;
import net.daw.helper.statics.EncodingUtilHelper;

/**
 *
 * @author a003153722p
 */
public class PruebaBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String informe;
    @Expose
    private Date fecha_peticion;
    @Expose
    private Double importe;

    @Expose(serialize = false)
    private Integer id_episodio = 0;
    @Expose(deserialize = false)
    private EpisodioBean obj_episodio = null;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public Date getFechaPeticion() {
        return fecha_peticion;
    }

    public void setFechaPeticion(Date fecha_peticion) {
        this.fecha_peticion = fecha_peticion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getId_episodio() {
        return id_episodio;
    }

    public void setId_episodio(Integer id_episodio) {
        this.id_episodio = id_episodio;
    }

    public EpisodioBean getObj_episodio() {
        return obj_episodio;
    }

    public void setObj_episodio(EpisodioBean obj_episodio) {
        this.obj_episodio = obj_episodio;
    }   

    public PruebaBean() {
    }

    public PruebaBean(Integer id) {
        this.id = id;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "informe,";
        strColumns += "fecha_peticion,";
        strColumns += "id_episodio,";
        strColumns += "importe";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(informe) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_peticion) + ",";
        strColumns += id_episodio + ",";
        strColumns += importe;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        //strPairs += "id=" + id + ",";
        strPairs += "informe=" + EncodingUtilHelper.quotate(informe) + ",";
        strPairs += "fecha_peticion=" + EncodingUtilHelper.stringifyAndQuotate(fecha_peticion) + ",";
        strPairs += "id_episodio=" + id_episodio + ",";
        strPairs += "importe=" + importe;
        return strPairs;
    }

    @Override
    public GenericBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setInforme(oResultSet.getString("informe"));
        this.setFechaPeticion(oResultSet.getTimestamp("fecha_peticion"));
        this.setImporte(oResultSet.getDouble("importe"));

        if (expand > 0) {
            EpisodioBean oEpisodioBean = new EpisodioBean();
            EpisodioDao oEpisodioDao = new EpisodioDao(pooledConnection, oPusuarioBean_security, null);
            oEpisodioBean.setId(oResultSet.getInt("id_episodio"));
            oEpisodioBean = oEpisodioDao.get(oEpisodioBean, expand - 1);
            this.setObj_episodio(oEpisodioBean);
        } else {
            this.setId_episodio(oResultSet.getInt("id_episodio"));
        }

        return this;
    }

}
