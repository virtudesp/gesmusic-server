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
import net.daw.dao.implementation.MedicoDao;
import net.daw.dao.implementation.TipousuarioDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class UsuarioBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String dni;
    @Expose
    private String nombre;
    @Expose
    private String primerapellido;
    @Expose
    private String segundoapellido;
    @Expose
    private String login;
    @Expose
    private String direccion;
    @Expose
    private String ciudad;
    @Expose
    private String codigopostal;
    @Expose
    private String provincia;
    @Expose
    private String pais;
    @Expose
    private String email;
    @Expose
    private String telefono;

    @Expose(serialize = false)
    private Integer id_tipousuario = 0;
    @Expose(deserialize = false)
    private TipousuarioBean obj_tipousuario = null;

    @Expose(serialize = false)
    private Integer id_medico = 0;

    @Expose(deserialize = false)
    private MedicoBean obj_medico = null;

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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerapellido() {
        return primerapellido;
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    public String getSegundoapellido() {
        return segundoapellido;
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public Integer getId_medico() {
        return id_medico;
    }

    public void setId_medico(Integer id_medico) {
        this.id_medico = id_medico;
    }

    public MedicoBean getObj_medico() {
        return obj_medico;
    }

    public void setObj_medico(MedicoBean obj_medico) {
        this.obj_medico = obj_medico;
    }
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "dni,";
        strColumns += "nombre,";
        strColumns += "primerapellido,";
        strColumns += "segundoapellido,";
        strColumns += "login,";        
        strColumns += "direccion,";
        strColumns += "ciudad,";
        strColumns += "codigopostal,";
        strColumns += "provincia,";
        strColumns += "pais,";
        strColumns += "email,";
        strColumns += "telefono,";
        strColumns += "id_tipousuario,";
        strColumns += "id_medico";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(dni) + ",";
        strColumns += EncodingUtilHelper.quotate(nombre) + ",";
        strColumns += EncodingUtilHelper.quotate(primerapellido) + ",";
        strColumns += EncodingUtilHelper.quotate(segundoapellido) + ",";
        strColumns += EncodingUtilHelper.quotate(login) + ",";
        strColumns += EncodingUtilHelper.quotate(direccion) + ",";
        strColumns += EncodingUtilHelper.quotate(ciudad) + ",";
        strColumns += EncodingUtilHelper.quotate(codigopostal) + ",";
        strColumns += EncodingUtilHelper.quotate(provincia) + ",";
        strColumns += EncodingUtilHelper.quotate(pais) + ",";
        strColumns += EncodingUtilHelper.quotate(email) + ",";
        strColumns += EncodingUtilHelper.quotate(telefono) + ",";
        strColumns += id_tipousuario + ",";
        strColumns += id_medico;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "dni=" + EncodingUtilHelper.quotate(dni) + ",";
        strPairs += "nombre=" + EncodingUtilHelper.quotate(nombre) + ",";
        strPairs += "primerapellido=" + EncodingUtilHelper.quotate(primerapellido) + ",";
        strPairs += "segundoapellido=" + EncodingUtilHelper.quotate(segundoapellido) + ",";
        strPairs += "login=" + EncodingUtilHelper.quotate(login) + ",";
        strPairs += "direccion=" + EncodingUtilHelper.quotate(direccion) + ",";
        strPairs += "ciudad=" + EncodingUtilHelper.quotate(ciudad) + ",";
        strPairs += "codigopostal=" + EncodingUtilHelper.quotate(codigopostal) + ",";
        strPairs += "provincia=" + EncodingUtilHelper.quotate(provincia) + ",";
        strPairs += "pais=" + EncodingUtilHelper.quotate(pais) + ",";
        strPairs += "email=" + EncodingUtilHelper.quotate(email) + ",";
        strPairs += "telefono=" + EncodingUtilHelper.quotate(telefono) + ",";
        strPairs += "id_tipousuario=" + id_tipousuario + ",";
        strPairs += "id_medico=" + id_medico;
        return strPairs;
    }

    @Override
    public UsuarioBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDni(oResultSet.getString("dni"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setPrimerapellido(oResultSet.getString("primerapellido"));
        this.setSegundoapellido(oResultSet.getString("segundoapellido"));
        this.setLogin(oResultSet.getString("login"));
        this.setDireccion(oResultSet.getString("direccion"));
        this.setCiudad(oResultSet.getString("ciudad"));
        this.setCodigopostal(oResultSet.getString("codigopostal"));
        this.setProvincia(oResultSet.getString("provincia"));
        this.setPais(oResultSet.getString("pais"));
        this.setEmail(oResultSet.getString("email"));
        this.setTelefono(oResultSet.getString("telefono"));

        if (expand > 0) {
            TipousuarioBean oTipousuarioBean = new TipousuarioBean();
            TipousuarioDao oTipousuarioDao = new TipousuarioDao(pooledConnection, oPuserBean_security, null);
            oTipousuarioBean.setId(oResultSet.getInt("id_tipousuario"));
            oTipousuarioBean = oTipousuarioDao.get(oTipousuarioBean, expand - 1);
            this.setObj_tipousuario(oTipousuarioBean);
        } else {
            this.setId_tipousuario(oResultSet.getInt("id_tipousuario"));
        }

        if (expand > 0) {
            MedicoBean oMedicoBean = new MedicoBean();
            MedicoDao oMedicoDao = new MedicoDao(pooledConnection, oPuserBean_security, null);
            oMedicoBean.setId(oResultSet.getInt("id_medico"));
            oMedicoBean = oMedicoDao.get(oMedicoBean, expand - 1);
            this.setObj_medico(oMedicoBean);
        } else {
            this.setId_medico(oResultSet.getInt("id_medico"));
        }
        return this;
    }
}
