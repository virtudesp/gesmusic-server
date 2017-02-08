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
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.TipousuarioDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class ObraBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String titulo;
    @Expose
    private String subtitulo;
    @Expose
    private String notas;

    @Expose(serialize = false)
    private Integer id_compositor = 0;
    @Expose(deserialize = false)
    private TipousuarioBean obj_compositor = null;

    public ObraBean() {
    }

    public ObraBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getId_compositor() {
        return id_compositor;
    }

    public void setId_compositor(Integer id_compositor) {
        this.id_compositor = id_compositor;
    }

    public TipousuarioBean getObj_compositor() {
        return obj_compositor;
    }

    public void setObj_compositor(TipousuarioBean obj_compositor) {
        this.obj_compositor = obj_compositor;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "titulo,";
        strColumns += "subtitulo,";
        strColumns += "notas,";
        strColumns += "id_compositor";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(titulo) + ",";
        strColumns += EncodingUtilHelper.quotate(subtitulo) + ",";
        strColumns += EncodingUtilHelper.quotate(notas) + ",";
        strColumns += id_compositor;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "titulo=" + EncodingUtilHelper.quotate(titulo) + ",";
        strPairs += "subtitulo=" + EncodingUtilHelper.quotate(subtitulo) + ",";
        strPairs += "notas=" + EncodingUtilHelper.quotate(notas) + ",";
        strPairs += "id_compositor=" + id_compositor;
        return strPairs;
    }

    @Override
    public ObraBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setTitulo(oResultSet.getString("titulo"));
        this.setSubtitulo(oResultSet.getString("subtitulo"));
        this.setNotas(oResultSet.getString("notas"));
        
        if (expand > 0) {
            TipousuarioBean oTipousuarioBean = new TipousuarioBean();
            TipousuarioDao oTipousuarioDao = new TipousuarioDao(pooledConnection, oPuserBean_security, null);
            oTipousuarioBean.setId(oResultSet.getInt("id_compositor"));
            oTipousuarioBean = oTipousuarioDao.get(oTipousuarioBean, expand - 1);
            this.setObj_compositor(oTipousuarioBean);
        } else {
            this.setId_compositor(oResultSet.getInt("id_compositor"));
        }
        
        return this;
    }
}