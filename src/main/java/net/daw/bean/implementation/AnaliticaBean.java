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
import net.daw.dao.implementation.AnticoagulanteDao;
import net.daw.dao.implementation.EpisodioDao;
import net.daw.dao.implementation.PrioridadDao;
import net.daw.dao.implementation.TipomuestraDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class AnaliticaBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String informe;
    @Expose
    private Date fecha_peticion;
    @Expose
    private Double importe;

    @Expose(serialize = false)
    private Integer id_tipomuestra = 0;
    @Expose(deserialize = false)
    private TipomuestraBean obj_tipomuestra = null;

    @Expose(serialize = false)
    private Integer id_anticoagulante = 0;
    @Expose(deserialize = false)
    private AnticoagulanteBean obj_anticoagulante = null;

    @Expose(serialize = false)
    private Integer id_prioridad = 0;
    @Expose(deserialize = false)
    private PrioridadBean obj_prioridad = null;

    @Expose(serialize = false)
    private Integer id_episodio = 0;
    @Expose(deserialize = false)
    private EpisodioBean obj_episodio = null;

    public AnaliticaBean() {
    }

    public AnaliticaBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the informe
     */
    public String getInforme() {
        return informe;
    }

    /**
     * @param informe the informe to set
     */
    public void setInforme(String informe) {
        this.informe = informe;
    }

    /**
     * @return the fecha_peticion
     */
    public Date getFecha_peticion() {
        return fecha_peticion;
    }

    /**
     * @param fecha_peticion the fecha_peticion to set
     */
    public void setFecha_peticion(Date fecha_peticion) {
        this.fecha_peticion = fecha_peticion;
    }

    /**
     * @return the importe
     */
    public Double getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(Double importe) {
        this.importe = importe;
    }

    /**
     * @return the id_tipomuestra
     */
    public Integer getId_tipomuestra() {
        return id_tipomuestra;
    }

    /**
     * @param id_tipomuestra the id_tipomuestra to set
     */
    public void setId_tipomuestra(Integer id_tipomuestra) {
        this.id_tipomuestra = id_tipomuestra;
    }

    /**
     * @return the obj_tipomuestra
     */
    public TipomuestraBean getObj_tipomuestra() {
        return obj_tipomuestra;
    }

    /**
     * @param obj_tipomuestra the obj_tipomuestra to set
     */
    public void setObj_tipomuestra(TipomuestraBean obj_tipomuestra) {
        this.obj_tipomuestra = obj_tipomuestra;
    }

    /**
     * @return the id_anticoagulante
     */
    public Integer getId_anticoagulante() {
        return id_anticoagulante;
    }

    /**
     * @param id_anticoagulante the id_anticoagulante to set
     */
    public void setId_anticoagulante(Integer id_anticoagulante) {
        this.id_anticoagulante = id_anticoagulante;
    }

    /**
     * @return the obj_anticoagulante
     */
    public AnticoagulanteBean getObj_anticoagulante() {
        return obj_anticoagulante;
    }

    /**
     * @param obj_anticoagulante the obj_anticoagulante to set
     */
    public void setObj_anticoagulante(AnticoagulanteBean obj_anticoagulante) {
        this.obj_anticoagulante = obj_anticoagulante;
    }

    /**
     * @return the id_prioridad
     */
    public Integer getId_prioridad() {
        return id_prioridad;
    }

    /**
     * @param id_prioridad the id_prioridad to set
     */
    public void setId_prioridad(Integer id_prioridad) {
        this.id_prioridad = id_prioridad;
    }

    /**
     * @return the obj_prioridad
     */
    public PrioridadBean getObj_prioridad() {
        return obj_prioridad;
    }

    /**
     * @param obj_prioridad the obj_prioridad to set
     */
    public void setObj_prioridad(PrioridadBean obj_prioridad) {
        this.obj_prioridad = obj_prioridad;
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

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "informe,";
        strColumns += "fecha_peticion,";
        strColumns += "importe,";
        strColumns += "id_tipomuestra,";
        strColumns += "id_anticoagulante,";
        strColumns += "id_prioridad,";
        strColumns += "id_episodio";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(getInforme()) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_peticion) + ",";
        strColumns += getImporte() + ",";
        strColumns += getId_tipomuestra() + ",";
        strColumns += getId_anticoagulante() + ",";
        strColumns += getId_prioridad() + ",";
        strColumns += getId_episodio();
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "informe=" + EncodingUtilHelper.quotate(getInforme()) + ",";
        strPairs += "fecha_peticion=" + EncodingUtilHelper.stringifyAndQuotate(fecha_peticion) + ",";
        strPairs += "importe=" + getImporte() + ",";
        strPairs += "id_tipomuestra=" + getId_tipomuestra() + ",";
        strPairs += "id_anticoagulante=" + getId_anticoagulante() + ",";
        strPairs += "id_prioridad=" + getId_prioridad() + ",";
        strPairs += "id_episodio=" + getId_episodio();
        return strPairs;
    }

    @Override
    public AnaliticaBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setInforme(oResultSet.getString("informe"));
        this.setFecha_peticion(oResultSet.getTimestamp("fecha_peticion"));
        this.setImporte(oResultSet.getDouble("importe"));

        if (expand > 0) {
            TipomuestraBean oTipomuestraBean = new TipomuestraBean();
            TipomuestraDao oTipomuestraDao = new TipomuestraDao(pooledConnection, oPuserBean_security, null);
            oTipomuestraBean.setId(oResultSet.getInt("id_tipomuestra"));
            oTipomuestraBean = oTipomuestraDao.get(oTipomuestraBean, expand - 1);
            this.setObj_tipomuestra(oTipomuestraBean);
        } else {
            this.setId_tipomuestra(oResultSet.getInt("id_tipomuestra"));
        }

        if (expand > 0) {
            AnticoagulanteBean oAnticoagulanteBean = new AnticoagulanteBean();
            AnticoagulanteDao oAnticoagulanteDao = new AnticoagulanteDao(pooledConnection, oPuserBean_security, null);
            oAnticoagulanteBean.setId(oResultSet.getInt("id_anticoagulante"));
            oAnticoagulanteBean = oAnticoagulanteDao.get(oAnticoagulanteBean, expand - 1);
            this.setObj_anticoagulante(oAnticoagulanteBean);
        } else {
            this.setId_anticoagulante(oResultSet.getInt("id_anticoagulante"));
        }

        if (expand > 0) {
            PrioridadBean oPrioridadBean = new PrioridadBean();
            PrioridadDao oPrioridadDao = new PrioridadDao(pooledConnection, oPuserBean_security, null);
            oPrioridadBean.setId(oResultSet.getInt("id_prioridad"));
            oPrioridadBean = oPrioridadDao.get(oPrioridadBean, expand - 1);
            this.setObj_prioridad(oPrioridadBean);
        } else {
            this.setId_prioridad(oResultSet.getInt("id_prioridad"));
        }

        if (expand > 0) {
            EpisodioBean oEpisodioBean = new EpisodioBean();
            EpisodioDao oEpisodioDao = new EpisodioDao(pooledConnection, oPuserBean_security, null);
            oEpisodioBean.setId(oResultSet.getInt("id_prioridad"));
            oEpisodioBean = oEpisodioDao.get(oEpisodioBean, expand - 1);
            this.setObj_episodio(oEpisodioBean);
        } else {
            this.setId_episodio(oResultSet.getInt("id_prioridad"));
        }

        return this;
    }

}
