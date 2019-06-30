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
import net.daw.helper.statics.EncodingUtilHelper;

public class TipousuarioBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String tipousuario;
    @Expose
    private Integer total = 1; // Si un tipousuario está en la bbdd es porque al menos hay un usuario de ese tipo

    public TipousuarioBean() {
    }

    public TipousuarioBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }
    
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "tipousuario";//,";
//        strColumns += "total";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(tipousuario);// + ",";
//        strColumns += total;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";     
        Boolean hay = false;
//        strPairs += "tipousuario=" + EncodingUtilHelper.quotate(tipousuario);
        if (tipousuario != null) {
            strPairs += "tipousuario=" + EncodingUtilHelper.quotate(tipousuario);
//            hay = true;
        }
//        if (total != null) {
//            strPairs += (hay) ? ",total=" : "total=";
//            strPairs += total;            
//        }
        return strPairs;
    }

    @Override
    public TipousuarioBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setTipousuario(oResultSet.getString("tipousuario"));
        this.setTotal(oResultSet.getInt("total"));
        return this;
    }

}
