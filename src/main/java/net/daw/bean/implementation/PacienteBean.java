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
import net.daw.dao.implementation.ServicioDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class PacienteBean implements GenericBean {

    @Expose
    private Integer id = 0;
    @Expose
    private String dni;
    @Expose
    private String name;
    @Expose
    private String primer_apellido;
    @Expose
    private String segundo_apellido;
    @Expose
    private String login;
    @Expose
    private String password;
    @Expose
    private String estado;
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
    private Integer id_servicio = 0;
    @Expose(deserialize = false)
    private ServicioBean obj_servicio = null;

    @Expose
    private Date fecha_salida;

    public PacienteBean() {
    }

    public PacienteBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the primer_apellido
     */
    public String getPrimer_apellido() {
        return primer_apellido;
    }

    /**
     * @param primer_apellido the primer_apellido to set
     */
    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    /**
     * @return the segundo_apellido
     */
    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    /**
     * @param segundo_apellido the segundo_apellido to set
     */
    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the codigopostal
     */
    public String getCodigopostal() {
        return codigopostal;
    }

    /**
     * @param codigopostal the codigopostal to set
     */
    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the id_servicio
     */
    public Integer getId_servicio() {
        return id_servicio;
    }

    /**
     * @param id_servicio the id_servicio to set
     */
    public void setId_servicio(Integer id_servicio) {
        this.id_servicio = id_servicio;
    }

    /**
     * @return the obj_servicio
     */
    public ServicioBean getObj_servicio() {
        return obj_servicio;
    }

    /**
     * @param obj_servicio the obj_servicio to set
     */
    public void setObj_servicio(ServicioBean obj_servicio) {
        this.obj_servicio = obj_servicio;
    }

    /**
     * @return the fecha_salida
     */
    public Date getFecha_salida() {
        return fecha_salida;
    }

    /**
     * @param fecha_salida the fecha_salida to set
     */
    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "dni,";
        strColumns += "name,";
        strColumns += "primer_apellido,";
        strColumns += "segundo_apellido,";
        strColumns += "login,";
        strColumns += "password,";
        strColumns += "estado,";
        strColumns += "direccion,";
        strColumns += "ciudad,";
        strColumns += "codigopostal,";
        strColumns += "provincia,";
        strColumns += "pais,";
        strColumns += "email,";
        strColumns += "telefono,";
        strColumns += "id_servicio,";
        strColumns += "fecha_salida";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(getDni()) + ",";
        strColumns += EncodingUtilHelper.quotate(getName()) + ",";
        strColumns += EncodingUtilHelper.quotate(getPrimer_apellido()) + ",";
        strColumns += EncodingUtilHelper.quotate(getSegundo_apellido()) + ",";
        strColumns += EncodingUtilHelper.quotate(getLogin()) + ",";
        strColumns += EncodingUtilHelper.quotate(getPassword()) + ",";
        strColumns += EncodingUtilHelper.quotate(getEstado()) + ",";
        strColumns += EncodingUtilHelper.quotate(getDireccion()) + ",";
        strColumns += EncodingUtilHelper.quotate(getCiudad()) + ",";
        strColumns += EncodingUtilHelper.quotate(getCodigopostal()) + ",";
        strColumns += EncodingUtilHelper.quotate(getProvincia()) + ",";
        strColumns += EncodingUtilHelper.quotate(getPais()) + ",";
        strColumns += EncodingUtilHelper.quotate(getEmail()) + ",";
        strColumns += EncodingUtilHelper.quotate(getTelefono()) + ",";
        strColumns += getId_servicio() + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_salida);
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "dni=" + EncodingUtilHelper.quotate(getDni()) + ",";
        strPairs += "name=" + EncodingUtilHelper.quotate(getName()) + ",";
        strPairs += "primer_apellido=" + EncodingUtilHelper.quotate(getPrimer_apellido()) + ",";
        strPairs += "segundo_apellido=" + EncodingUtilHelper.quotate(getSegundo_apellido()) + ",";
        strPairs += "login=" + EncodingUtilHelper.quotate(getLogin()) + ",";
        strPairs += "password=" + EncodingUtilHelper.quotate(getPassword()) + ",";
        strPairs += "estado=" + EncodingUtilHelper.quotate(getEstado()) + ",";
        strPairs += "direccion=" + EncodingUtilHelper.quotate(getDireccion()) + ",";
        strPairs += "ciudad=" + EncodingUtilHelper.quotate(getCiudad()) + ",";
        strPairs += "codigopostal=" + EncodingUtilHelper.quotate(getCodigopostal()) + ",";
        strPairs += "provincia=" + EncodingUtilHelper.quotate(getProvincia()) + ",";
        strPairs += "pais=" + EncodingUtilHelper.quotate(getPais()) + ",";
        strPairs += "email=" + EncodingUtilHelper.quotate(getEmail()) + ",";
        strPairs += "telefono=" + EncodingUtilHelper.quotate(getTelefono()) + ",";
        strPairs += "id_servicio=" + getId_servicio() + ",";
        strPairs += "fecha_salida=" + EncodingUtilHelper.stringifyAndQuotate(fecha_salida);
        return strPairs;
    }

    @Override
    public PacienteBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDni(oResultSet.getString("dni"));
        this.setName(oResultSet.getString("name"));
        this.setPrimer_apellido(oResultSet.getString("primer_apellido"));
        this.setSegundo_apellido(oResultSet.getString("segundo_apellido"));
        this.setLogin(oResultSet.getString("login"));
        this.setPassword(oResultSet.getString("password"));
        this.setEstado(oResultSet.getString("estado"));
        this.setDireccion(oResultSet.getString("direccion"));
        this.setCiudad(oResultSet.getString("ciudad"));
        this.setCodigopostal(oResultSet.getString("codigopostal"));
        this.setProvincia(oResultSet.getString("provincia"));
        this.setPais(oResultSet.getString("pais"));
        this.setEmail(oResultSet.getString("email"));
        this.setTelefono(oResultSet.getString("telefono"));

        if (expand > 0) {
            ServicioBean oServicioBean = new ServicioBean();
            ServicioDao oServicioDao = new ServicioDao(pooledConnection, oPuserBean_security, null);
            oServicioBean.setId(oResultSet.getInt("id_servicio"));
            oServicioBean = oServicioDao.get(oServicioBean, expand - 1);
            this.setObj_servicio(oServicioBean);
        } else {
            this.setId_servicio(oResultSet.getInt("id_servicio"));
        }

        this.setFecha_salida(oResultSet.getDate("fecha_salida"));

        return this;
    }

}
