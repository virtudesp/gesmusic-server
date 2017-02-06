/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * bauxer server: Helps you to develop easily AJAX web applications 
 *                   by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/bauxer
 * 
 * bauxer server is distributed under the MIT License (MIT)
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
import net.daw.dao.implementation.ZonaDao;
import net.daw.dao.implementation.ImagenDao;

public class ZonaimagenBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose(serialize = false)
    private Integer id_zona = 0;
    @Expose(deserialize = false)
    private ZonaBean obj_zona;
    @Expose(serialize = false)
    private Integer id_imagen = 0;
    @Expose(deserialize = false)
    private ImagenBean obj_imagen;

    public ZonaimagenBean() {
    }

    public ZonaimagenBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_zona() {
        return id_zona;
    }

    public void setId_zona(Integer id_zona) {
        this.id_zona = id_zona;
    }

    public ZonaBean getObj_zona() {
        return obj_zona;
    }

    public void setObj_zona(ZonaBean obj_zona) {
        this.obj_zona = obj_zona;
    }

    public Integer getId_imagen() {
        return id_imagen;
    }

    public void setId_imagen(Integer id_imagen) {
        this.id_imagen = id_imagen;
    }

    public ImagenBean getObj_imagen() {
        return obj_imagen;
    }

    public void setObj_imagen(ImagenBean obj_imagen) {
        this.obj_imagen = obj_imagen;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id" + ",";
        strColumns += "id_zona,";
        strColumns += "id_imagen";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += id_zona + ",";
        strColumns += id_imagen;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strColumns = "";
        //strColumns +="id="; id + ";
        strColumns += "id_zona=" + id_zona + ",";
        strColumns += "id_imagen=" + id_imagen;
        return strColumns;
    }

    @Override
    public ZonaimagenBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oUsuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        if (expand > 0) {
            ZonaBean oZonaBean = new ZonaBean();
            ZonaDao oZonaDao = new ZonaDao(pooledConnection, oUsuarioBean_security,null);
            oZonaBean.setId(oResultSet.getInt("id_zona"));
            oZonaBean = oZonaDao.get(oZonaBean, expand - 1);
            this.setObj_zona(oZonaBean);
        } else {
            this.setId_zona(oResultSet.getInt("id_zona"));
        }
        if (expand > 0) {
            ImagenBean oImagenBean = new ImagenBean();
            ImagenDao oImagenDao = new ImagenDao(pooledConnection, oUsuarioBean_security,null);
            oImagenBean.setId(oResultSet.getInt("id_imagen"));
            oImagenBean = oImagenDao.get(oImagenBean, expand - 1);
            this.setObj_imagen(oImagenBean);
        } else {
            this.setId_imagen(oResultSet.getInt("id_imagen"));
        }
        return this;
    }

}
