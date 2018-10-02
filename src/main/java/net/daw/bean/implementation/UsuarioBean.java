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

public class UsuarioBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String username;
    @Expose
    private String userpass;

    @Expose(serialize = false)
    private Integer id_tipousuario = 0;
    @Expose(deserialize = false)
    private TipousuarioBean obj_tipousuario = null;

    public UsuarioBean() {
    }

    public UsuarioBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public Integer getId_tipousuario() {
        return id_tipousuario;
    }

    public void setId_tipousuario(Integer id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }

    public TipousuarioBean getObj_tipousuario() {
        return obj_tipousuario;
    }

    public void setObj_tipousuario(TipousuarioBean obj_tipousuario) {
        this.obj_tipousuario = obj_tipousuario;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "username,";
        strColumns += "userpass,";
        strColumns += "id_tipousuario";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(username) + ",";
        strColumns += EncodingUtilHelper.quotate(userpass) + ",";
        strColumns += id_tipousuario;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
//        strPairs += "username=" + EncodingUtilHelper.quotate(username) + ",";
//        strPairs += "userpass=" + EncodingUtilHelper.quotate(userpass) + ",";
//        strPairs += "id_tipousuario=" + id_tipousuario;
        Boolean hay = false;
        if (username != null) {
            strPairs += "username=" + EncodingUtilHelper.quotate(username);
            hay = true;
        }
        if (userpass != null) {
            strPairs += (hay) ? ",userpass=" : "userpass=";
            strPairs += EncodingUtilHelper.quotate(userpass);
        }
        return strPairs;
    }

    @Override
    public UsuarioBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setUsername(oResultSet.getString("username"));
        this.setUserpass(oResultSet.getString("userpass"));
        
        if (expand > 0) {
            TipousuarioBean oTipousuarioBean = new TipousuarioBean();
            TipousuarioDao oTipousuarioDao = new TipousuarioDao(pooledConnection, oPuserBean_security, null);
            oTipousuarioBean.setId(oResultSet.getInt("id_tipousuario"));
            oTipousuarioBean = oTipousuarioDao.get(oTipousuarioBean, expand - 1);
            this.setObj_tipousuario(oTipousuarioBean);
            //this.setIdUsuario
        } else {
            this.setId_tipousuario(oResultSet.getInt("id_tipousuario"));
        }
        
        return this;
    }
}