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

public class CompositorBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String nombre;
    @Expose
    private String apellidos;
    @Expose
    private String notas;
    @Expose
    private Integer total = 1; // Si un compositor está en la bbdd es porque al menos hay una obra de él

    public CompositorBean() {
    }

    public CompositorBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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
        strColumns += "nombre,";
        strColumns += "apellidos,";
        strColumns += "notas";//,";
//        strColumns += "total";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(nombre) + ",";
        strColumns += EncodingUtilHelper.quotate(apellidos) + ",";
        strColumns += EncodingUtilHelper.quotate(notas);// + ",";
//        strColumns += total;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";        
        Boolean hay = false;
        if (nombre != null) {
            strPairs += "nombre=" + EncodingUtilHelper.quotate(nombre);
            hay = true;
        }
        if (apellidos != null) {
            strPairs += (hay) ? ",apellidos=" : "apellidos=";
            strPairs += EncodingUtilHelper.quotate(apellidos);
            hay = true;
        }
        if (notas != null) {
            strPairs += (hay) ? ",notas=" : "notas=";
            strPairs += EncodingUtilHelper.quotate(notas); 
//            hay = true;           
        }
//        if (total != null) {
//            strPairs += (hay) ? ",total=" : "total=";
//            strPairs += total;            
//        }
        return strPairs;
    }

    @Override
    public CompositorBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApellidos(oResultSet.getString("apellidos"));
        this.setNotas(oResultSet.getString("notas"));
        this.setTotal(oResultSet.getInt("total"));
        return this;
    }

}
