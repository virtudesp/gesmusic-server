/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 *
 * sisane-server: Helps you to develop easily AJAX web applications
 *                   by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/sisane-server
 *
 * sisane-server is distributed under the MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.daw.bean.implementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.MedicamentoDao;
import net.daw.dao.implementation.DiagnosticoDao;
import net.daw.dao.implementation.PosologiaDao;
import net.daw.dao.implementation.ViaDao;

import net.daw.helper.statics.EncodingUtilHelper;

public class TratamientoBean implements GenericBean {

    @Expose
    private Integer id = 0;

    @Expose(serialize = false)
    private Integer id_medicamento = 0;
    @Expose(deserialize = false)
    private MedicamentoBean obj_medicamento;
    @Expose
    private Date fecha_inicio;

    @Expose
    private Date fecha_fin;

    @Expose
    private String cuidados;

    @Expose(serialize = false)
    private Integer id_via = 0;
    @Expose(deserialize = false)
    private ViaBean obj_via;
    @Expose(serialize = false)
    private Integer id_posologia = 0;
    @Expose(deserialize = false)
    private PosologiaBean obj_posologia;
    @Expose
    private double importe;

    @Expose(serialize = false)
    private Integer id_diagnostico = 0;
    @Expose(deserialize = false)
    private DiagnosticoBean obj_diagnostico;

    public TratamientoBean() {
    }

    public TratamientoBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_medicamento() {
        return id_medicamento;
    }

    public void setId_medicamento(Integer id_medicamento) {
        this.id_medicamento = id_medicamento;
    }

    public MedicamentoBean getObj_medicamento() {
        return obj_medicamento;
    }

    public void setObj_medicamento(MedicamentoBean obj_medicamento) {
        this.obj_medicamento = obj_medicamento;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public Integer getId_via() {
        return id_via;
    }

    public void setId_via(Integer id_via) {
        this.id_via = id_via;
    }

    public ViaBean getObj_via() {
        return obj_via;
    }

    public void setObj_via(ViaBean obj_via) {
        this.obj_via = obj_via;
    }

    public Integer getId_posologia() {
        return id_posologia;
    }

    public void setId_posologia(Integer id_posologia) {
        this.id_posologia = id_posologia;
    }

    public PosologiaBean getObj_posologia() {
        return obj_posologia;
    }

    public void setObj_posologia(PosologiaBean obj_posologia) {
        this.obj_posologia = obj_posologia;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Integer getId_diagnostico() {
        return id_diagnostico;
    }

    public void setId_diagnostico(Integer id_diagnostico) {
        this.id_diagnostico = id_diagnostico;
    }

    public DiagnosticoBean getObj_diagnostico() {
        return obj_diagnostico;
    }

    public void setObj_diagnostico(DiagnosticoBean obj_diagnostico) {
        this.obj_diagnostico = obj_diagnostico;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "id_medicamento,";
        strColumns += "fecha_inicio,";
        strColumns += "fecha_fin,";
        strColumns += "cuidados,";
        strColumns += "id_via,";
        strColumns += "id_posologia,";
        strColumns += "importe,";
        strColumns += "id_diagnostico";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += id_medicamento + ",";

        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_inicio) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_fin) + ",";
        strColumns += EncodingUtilHelper.quotate(cuidados) + ",";
        strColumns += id_via + ",";
        strColumns += id_posologia + ",";
        strColumns += importe + ",";
        strColumns += id_diagnostico;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strColumns = "";
        //strColumns +="id="; id + ";
        strColumns += "id_medicamento=" + id_medicamento + ",";

        strColumns += "fecha_inicio=" + EncodingUtilHelper.stringifyAndQuotate(fecha_inicio) + ",";
        strColumns += "fecha_fin=" + EncodingUtilHelper.stringifyAndQuotate(fecha_fin) + ",";
        strColumns += "cuidados=" + EncodingUtilHelper.quotate(cuidados) + ",";

        strColumns += "id_via=" + id_via + ",";
        strColumns += "id_posologia=" + id_posologia + ",";
        strColumns += "importe=" + importe + ",";
        strColumns += "id_diagnostico=" + id_diagnostico;
        return strColumns;
    }

    @Override
    public TratamientoBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setFecha_inicio(oResultSet.getTimestamp("fecha_inicio"));
        this.setFecha_fin(oResultSet.getTimestamp("fecha_fin"));
        this.setCuidados(oResultSet.getString("cuidados"));
        this.setImporte(oResultSet.getDouble("importe"));

        if (expand > 0) {
            MedicamentoBean oMedicamentoBean = new MedicamentoBean();
            MedicamentoDao oMedicamentoDao = new MedicamentoDao(pooledConnection, oPuserBean_security, null);
            oMedicamentoBean.setId(oResultSet.getInt("id_medicamento"));
            oMedicamentoBean = oMedicamentoDao.get(oMedicamentoBean, expand - 1);
            this.setObj_medicamento(oMedicamentoBean);
        } else {
            this.setId_medicamento(oResultSet.getInt("id_medicamento"));
        }

        if (expand > 0) {
            ViaBean oViaBean = new ViaBean();
            ViaDao oViaDao = new ViaDao(pooledConnection, oPuserBean_security, null);
            oViaBean.setId(oResultSet.getInt("id_via"));
            oViaBean = oViaDao.get(oViaBean, expand - 1);
            this.setObj_via(oViaBean);
        } else {
            this.setId_via(oResultSet.getInt("id_via"));
        }
        if (expand > 0) {
            PosologiaBean oPosologiaBean = new PosologiaBean();
            PosologiaDao oPosologiaDao = new PosologiaDao(pooledConnection, oPuserBean_security, null);
            oPosologiaBean.setId(oResultSet.getInt("id_posologia"));
            oPosologiaBean = oPosologiaDao.get(oPosologiaBean, expand - 1);
            this.setObj_posologia(oPosologiaBean);
        } else {
            this.setId_posologia(oResultSet.getInt("id_posologia"));
        }

        if (expand > 0) {
            DiagnosticoBean oDiagnosticoBean = new DiagnosticoBean();
            DiagnosticoDao oDiagnosticoDao = new DiagnosticoDao(pooledConnection, oPuserBean_security, null);
            oDiagnosticoBean.setId(oResultSet.getInt("id_diagnostico"));
            oDiagnosticoBean = oDiagnosticoDao.get(oDiagnosticoBean, expand - 1);
            this.setObj_diagnostico(oDiagnosticoBean);
        } else {
            this.setId_diagnostico(oResultSet.getInt("id_diagnostico"));
        }

        return this;
    }

}
