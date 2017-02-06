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
    private Integer id_tipoentidad = 0;
    @Expose(deserialize = false)
    private TipomuestraBean obj_tipoentidad = null;


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

    public Integer getId_tipoentidad() {
        return id_tipoentidad;
    }

    public void setId_tipoentidad(Integer id_tipoentidad) {
        this.id_tipoentidad = id_tipoentidad;
    }

    public TipomuestraBean getObj_tipoentidad() {
        return obj_tipoentidad;
    }

    public void setObj_tipoentidad(TipomuestraBean obj_tipoentidad) {
        this.obj_tipoentidad = obj_tipoentidad;
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
        strColumns += "id_tipoentidad";
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
        strColumns += getId_tipoentidad();
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
        strPairs += "id_tipoentidad=" + id_tipoentidad;
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

        /*if (expand > 0) {
            TipoentidadBean oTipoentidadBean = new TipoentidadBean();
            TipoentidadDao oTipomuestraDao = new TipoentidadDao(pooledConnection, oPuserBean_security, null);
            oTipoentidadBean.setId(oResultSet.getInt("id_tipoentidad"));
            oTipoentidadBean = oTipomuestraDao.get(oTipoentidadBean, expand - 1);
            this.setObj_tipoentidad(oTipoentidadBean);
        } else {
            this.setId_tipoentidad(oResultSet.getInt("id_tipoentidad"));
        }*/

        return this;
    }

}
