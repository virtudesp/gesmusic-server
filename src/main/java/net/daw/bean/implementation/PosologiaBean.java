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

import net.daw.helper.statics.EncodingUtilHelper;

public class PosologiaBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String descripcion;

    @Expose(serialize = false)
    private Integer id_medicamento = 0;
    @Expose(deserialize = false)
    private MedicamentoBean obj_medicamento;//habra que cambiar el bean a medicamento

    public PosologiaBean() {
    }

    public PosologiaBean(Integer id) {
        this.id = id;
    }

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

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "descripcion,";

        strColumns += "id_medicamento";

        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(descripcion) + ",";

        strColumns += id_medicamento;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strColumns = "";
        //strColumns +="id="; id + ";
        strColumns += "descripcion=" + EncodingUtilHelper.quotate(descripcion) + ",";

        strColumns += "id_medicamento=" + id_medicamento;
        return strColumns;
    }

    @Override
    public PosologiaBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));

        if (expand > 0) {
            MedicamentoBean oMedicamentoBean = new MedicamentoBean();
            MedicamentoDao oMedicamentoDao = new MedicamentoDao(pooledConnection, oPuserBean_security, null);
            oMedicamentoBean.setId(oResultSet.getInt("id_medicamento"));
            oMedicamentoBean = oMedicamentoDao.get(oMedicamentoBean, expand - 1);
            this.setObj_medicamento(oMedicamentoBean);
        } else {
            this.setId_medicamento(oResultSet.getInt("id_medicamento"));
        }

        return this;
    }

}
