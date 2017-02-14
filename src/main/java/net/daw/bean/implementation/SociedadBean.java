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

public class SociedadBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String nombre;
    @Expose
    private String razon_social;
    @Expose
    private String nif;
    @Expose
    private String direccion;
    @Expose
    private String poblacion;
    @Expose
    private String provincia;
    @Expose
    private String pais;
    @Expose
    private String codigopostal;
    @Expose
    private String telefono;
    @Expose
    private String email;
    @Expose
    private String web;


    public SociedadBean() {
    }

    public SociedadBean(Integer id) {
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

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
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

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }  

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "nombre,";
        strColumns += "razon_social,";
        strColumns += "nif,";
        strColumns += "direccion,";
        strColumns += "poblacion,";
        strColumns += "provincia,";
        strColumns += "pais,";
        strColumns += "codigopostal,";
        strColumns += "telefono,";
        strColumns += "email,";
        strColumns += "web";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(getNombre()) + ",";
        strColumns += EncodingUtilHelper.quotate(getRazon_social()) + ",";
        strColumns += EncodingUtilHelper.quotate(getNif()) + ",";
        strColumns += EncodingUtilHelper.quotate(getDireccion()) + ",";
        strColumns += EncodingUtilHelper.quotate(getPoblacion()) + ",";
        strColumns += EncodingUtilHelper.quotate(getProvincia()) + ",";
        strColumns += EncodingUtilHelper.quotate(getPais()) + ",";
        strColumns += EncodingUtilHelper.quotate(getCodigopostal()) + ",";
        strColumns += EncodingUtilHelper.quotate(getTelefono()) + ",";
        strColumns += EncodingUtilHelper.quotate(getEmail()) + ",";
        strColumns += EncodingUtilHelper.quotate(getWeb());
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "nombre=" + EncodingUtilHelper.quotate(nombre) + ",";
        strPairs += "razon_social=" + EncodingUtilHelper.quotate(razon_social) + ",";
        strPairs += "nif=" + EncodingUtilHelper.quotate(nif) + ",";
        strPairs += "direccion=" + EncodingUtilHelper.quotate(direccion) + ",";
        strPairs += "poblacion=" + EncodingUtilHelper.quotate(poblacion) + ",";
        strPairs += "provincia=" + EncodingUtilHelper.quotate(provincia) + ",";
        strPairs += "pais=" + EncodingUtilHelper.quotate(pais) + ",";
        strPairs += "codigopostal=" + EncodingUtilHelper.quotate(codigopostal) + ",";
        strPairs += "telefono=" + EncodingUtilHelper.quotate(telefono) + ",";
        strPairs += "email=" + EncodingUtilHelper.quotate(email) + ",";
        strPairs += "web=" + EncodingUtilHelper.quotate(web);
        return strPairs;
    }

    @Override
    public SociedadBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setRazon_social(oResultSet.getString("razon_social"));
        this.setNif(oResultSet.getString("nif"));
        this.setDireccion(oResultSet.getString("direccion"));
        this.setPoblacion(oResultSet.getString("poblacion"));
        this.setProvincia(oResultSet.getString("provincia"));
        this.setPais(oResultSet.getString("pais"));
        this.setCodigopostal(oResultSet.getString("codigopostal"));
        this.setTelefono(oResultSet.getString("telefono"));
        this.setEmail(oResultSet.getString("email"));
        this.setWeb(oResultSet.getString("web"));

        return this;
    }

}
