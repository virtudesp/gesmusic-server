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
import net.daw.helper.statics.EncodingUtilHelper;
import net.daw.dao.implementation.TipodiagnosticoDao;
import net.daw.dao.implementation.EpisodioDao;

/**
 *
 * @author a044887852v
 */
public class DiagnosticoBean implements GenericBean {

    @Expose
    private int id;
    @Expose
    private String informe;
    @Expose
    private Date fecha;
    @Expose(serialize = false)
    private int id_episodio;
    @Expose(deserialize = false)
    private EpisodioBean obj_episodio;
    @Expose(serialize = false)
    private int id_tipodiagnostico;
    @Expose(deserialize = false)
    private TipodiagnosticoBean obj_tipodiagnostico;

    public DiagnosticoBean(int id) {
        this.id = id;
    }

    public DiagnosticoBean() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_episodio() {
        return id_episodio;
    }

    public void setId_episodio(int id_episodio) {
        this.id_episodio = id_episodio;
    }

    public EpisodioBean getObj_episodio() {
        return obj_episodio;
    }

    public void setObj_episodio(EpisodioBean obj_episodio) {
        this.obj_episodio = obj_episodio;
    }

    public int getId_tipodiagnostico() {
        return id_tipodiagnostico;
    }

    public void setId_tipodiagnostico(int id_tipodiagnostico) {
        this.id_tipodiagnostico = id_tipodiagnostico;
    }

    public TipodiagnosticoBean getObj_tipodiagnostico() {
        return obj_tipodiagnostico;
    }

    public void setObj_tipodiagnostico(TipodiagnosticoBean obj_tipodiagnostico) {
        this.obj_tipodiagnostico = obj_tipodiagnostico;
    }

    @Override
    public String getColumns() {
        return "id,informe,fecha,id_episodio,id_tipodiagnostico";
    }

    @Override
    public String getValues() {
        String values;
        values = id + ",";
        values += EncodingUtilHelper.quotate(informe) + ",";
        values += EncodingUtilHelper.stringifyAndQuotate(fecha) + ",";
        values += id_episodio + ",";
        values += id_tipodiagnostico;

        return values;
    }

    @Override
    public String toPairs() {
        String pairs;
        pairs = "id = " + id + ",";
        pairs += "informe = " + EncodingUtilHelper.quotate(informe) + ",";
        pairs += "fecha = " + EncodingUtilHelper.stringifyAndQuotate(fecha) + ",";
        pairs += "id_episodio = " + id_episodio + ",";
        pairs += "id_tipodiagnostico = " + id_tipodiagnostico;

        return pairs;
    }

    @Override
    public DiagnosticoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.id = oResultSet.getInt("id");
        this.informe = oResultSet.getString("informe");
        this.fecha = oResultSet.getTimestamp("fecha");
        this.id_episodio = oResultSet.getInt("id_episodio");
        this.id_tipodiagnostico = oResultSet.getInt("id_tipodiagnostico");

        if (expand > 0) {
            EpisodioDao eDao = new EpisodioDao(pooledConnection, oPusuarioBean_security, null);
            EpisodioBean eBean = new EpisodioBean();
            eBean.setId(this.id_episodio);
            this.obj_episodio = eDao.get(eBean, expand - 1);
            TipodiagnosticoDao tDao = new TipodiagnosticoDao(pooledConnection, oPusuarioBean_security, null);
            TipodiagnosticoBean tBean = new TipodiagnosticoBean();
            tBean.setId(this.id_tipodiagnostico);
            this.obj_tipodiagnostico = tDao.get(tBean, expand - 1);
        }
        return this;
    }

}
