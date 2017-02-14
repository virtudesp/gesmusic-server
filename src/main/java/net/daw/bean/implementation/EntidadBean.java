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
import net.daw.dao.implementation.SociedadDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class EntidadBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String numero;
    @Expose
    private String nombre;
    @Expose
    private String apellidos;
    @Expose
    private String nif;
    @Expose
    private String direccion;
    @Expose
    private String codigopostal;
    @Expose
    private String poblacion;
    @Expose
    private String provincia;
    @Expose
    private String pais;
    @Expose
    private String telefono;
    @Expose
    private String movil;
    @Expose
    private String email;
    @Expose
    private String web;
    @Expose
    private Date fecha_alta;
    @Expose
    private Date fecha_baja;

    @Expose(serialize = false)
    private Integer id_sociedad = 0;
   @Expose(deserialize = false)
    private SociedadBean obj_sociedad = null;


    public EntidadBean() {
    }

    public EntidadBean(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
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

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public Integer getId_sociedad() {
        return id_sociedad;
    }

    public void setId_sociedad(Integer id_sociedad) {
        this.id_sociedad = id_sociedad;
    }

    public SociedadBean getObj_sociedad() {
        return obj_sociedad;
    }

    public void setObj_tipoentidad(SociedadBean obj_sociedad) {
        this.obj_sociedad = obj_sociedad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }    

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "numero,";
        strColumns += "nombre,";
        strColumns += "apellidos,";
        strColumns += "nif,";
        strColumns += "direccion,";
        strColumns += "codigopostal,";
        strColumns += "poblacion,";
        strColumns += "provincia,";
        strColumns += "pais,";
        strColumns += "telefono,";
        strColumns += "movil,";
        strColumns += "email,";
        strColumns += "web,";
        strColumns += "fecha_alta,";
        strColumns += "fecha_baja,";
        strColumns += "id_sociedad";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(getNumero()) + ",";
        strColumns += EncodingUtilHelper.quotate(getNombre()) + ",";
        strColumns += EncodingUtilHelper.quotate(getApellidos()) + ",";
        strColumns += EncodingUtilHelper.quotate(getNif()) + ",";
        strColumns += EncodingUtilHelper.quotate(getDireccion()) + ",";
        strColumns += EncodingUtilHelper.quotate(getCodigopostal()) + ",";
        strColumns += EncodingUtilHelper.quotate(getPoblacion()) + ",";
        strColumns += EncodingUtilHelper.quotate(getProvincia()) + ",";
        strColumns += EncodingUtilHelper.quotate(getPais()) + ",";
        strColumns += EncodingUtilHelper.quotate(getTelefono()) + ",";
        strColumns += EncodingUtilHelper.quotate(getMovil()) + ",";
        strColumns += EncodingUtilHelper.quotate(getEmail()) + ",";
        strColumns += EncodingUtilHelper.quotate(getWeb()) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_alta) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_baja) + ",";
        strColumns += getId_sociedad();
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "numero=" + EncodingUtilHelper.quotate(numero) + ",";
        strPairs += "nombre=" + EncodingUtilHelper.quotate(nombre) + ",";
        strPairs += "apellidos=" + EncodingUtilHelper.quotate(apellidos) + ",";
        strPairs += "nif=" + EncodingUtilHelper.quotate(nif) + ",";
        strPairs += "direccion=" + EncodingUtilHelper.quotate(direccion) + ",";
        strPairs += "codigopostal=" + EncodingUtilHelper.quotate(codigopostal) + ",";
        strPairs += "poblacion=" + EncodingUtilHelper.quotate(poblacion) + ",";
        strPairs += "provincia=" + EncodingUtilHelper.quotate(provincia) + ",";
        strPairs += "pais=" + EncodingUtilHelper.quotate(pais) + ",";
        strPairs += "telefono=" + EncodingUtilHelper.quotate(telefono) + ",";
        strPairs += "movil=" + EncodingUtilHelper.quotate(movil) + ",";
        strPairs += "email=" + EncodingUtilHelper.quotate(email) + ",";
        strPairs += "web=" + EncodingUtilHelper.quotate(web) + ",";
        strPairs += "fecha_alta=" + EncodingUtilHelper.stringifyAndQuotate(fecha_alta) + ",";
        strPairs += "fecha_baja=" + EncodingUtilHelper.stringifyAndQuotate(fecha_baja) + ",";
        strPairs += "id_sociedad=" + id_sociedad;
        return strPairs;
    }

    @Override
    public EntidadBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNumero(oResultSet.getString("numero"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApellidos(oResultSet.getString("apellidos"));
        this.setNif(oResultSet.getString("nif"));
        this.setDireccion(oResultSet.getString("direccion"));
        this.setCodigopostal(oResultSet.getString("codigopostal"));
        this.setPoblacion(oResultSet.getString("poblacion"));
        this.setProvincia(oResultSet.getString("provincia"));
        this.setPais(oResultSet.getString("pais"));
        this.setTelefono(oResultSet.getString("telefono"));
        this.setMovil(oResultSet.getString("movil"));
        this.setEmail(oResultSet.getString("email"));
        this.setWeb(oResultSet.getString("web"));
        this.setFecha_alta(oResultSet.getTimestamp("fecha_alta"));
        this.setFecha_baja(oResultSet.getTimestamp("fecha_baja"));

        if (expand > 0) {
            SociedadBean oSociedadBean = new SociedadBean();
            SociedadDao oTipomuestraDao = new SociedadDao(pooledConnection, oPuserBean_security, null);
            oSociedadBean.setId(oResultSet.getInt("id_sociedad"));
            oSociedadBean = oTipomuestraDao.get(oSociedadBean, expand - 1);
            this.setObj_tipoentidad(oSociedadBean);
        } else {
            this.setId_sociedad(oResultSet.getInt("id_sociedad"));
        }

        return this;
    }

}
