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
import net.daw.dao.implementation.TipoentidadDao;
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

    @Expose(serialize = false)
    private Integer id_tipoentidad = 0;
    @Expose(deserialize = false)
    private TipoentidadBean obj_tipoentidad = null;

    public EntidadBean() {
    }

    public EntidadBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setObj_sociedad(SociedadBean obj_sociedad) {
        this.obj_sociedad = obj_sociedad;
    }

    public Integer getId_tipoentidad() {
        return id_tipoentidad;
    }

    public void setId_tipoentidad(Integer id_tipoentidad) {
        this.id_tipoentidad = id_tipoentidad;
    }

    public TipoentidadBean getObj_tipoentidad() {
        return obj_tipoentidad;
    }

    public void setObj_tipoentidad(TipoentidadBean obj_tipoentidad) {
        this.obj_tipoentidad = obj_tipoentidad;
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
        strColumns += "id_sociedad,";
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
        if (fecha_alta == null) {
            java.util.Date hoy = new Date();
            strColumns += EncodingUtilHelper.stringifyAndQuotate(hoy) + ",";
        } else {
            strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_alta) + ",";
        }
        if (fecha_baja == null) {
            strColumns += "null" + ",";
        } else {
            strColumns += EncodingUtilHelper.stringifyAndQuotate(fecha_baja) + ",";
        }
        strColumns += getId_sociedad() + ",";
        strColumns += getId_tipoentidad();
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        Boolean hay = false;
        if (numero != null) {
            strPairs += "numero=" + EncodingUtilHelper.quotate(numero);
            hay = true;
        }
        if (nombre != null) {
            strPairs += (hay) ? ",nombre=" : "nombre=";
            strPairs += EncodingUtilHelper.quotate(nombre);
            hay = true;
            //strPairs += "nombre=" + EncodingUtilHelper.quotate(nombre) + ",";
        }
        if (apellidos != null) {
            //strPairs += "apellidos=" + EncodingUtilHelper.quotate(apellidos) + ",";
            strPairs += (hay) ? ",apellidos=" : "apellidos=";
            strPairs += EncodingUtilHelper.quotate(apellidos);
            hay = true;
        }
        if (nif != null) {
            //strPairs += "nif=" + EncodingUtilHelper.quotate(nif) + ",";
            strPairs += (hay) ? ",nif=" : "nif=";
            strPairs += EncodingUtilHelper.quotate(nif);
            hay = true;
        }
        if (direccion != null) {
            //strPairs += "direccion=" + EncodingUtilHelper.quotate(direccion) + ",";
            strPairs += (hay) ? ",direccion=" : "direccion=";
            strPairs += EncodingUtilHelper.quotate(direccion);
            hay = true;
        }
        if (codigopostal != null) {
            //strPairs += "codigopostal=" + EncodingUtilHelper.quotate(codigopostal) + ",";
            strPairs += (hay) ? ",codigopostal=" : "codigopostal=";
            strPairs += EncodingUtilHelper.quotate(codigopostal);
            hay = true;
        }
        if (poblacion != null) {
            //strPairs += "poblacion=" + EncodingUtilHelper.quotate(poblacion) + ",";
            strPairs += (hay) ? ",poblacion=" : "poblacion=";
            strPairs += EncodingUtilHelper.quotate(poblacion);
            hay = true;
        }
        if (provincia != null) {
            //strPairs += "provincia=" + EncodingUtilHelper.quotate(provincia) + ",";
            strPairs += (hay) ? ",provincia=" : "provincia=";
            strPairs += EncodingUtilHelper.quotate(provincia);
            hay = true;
        }
        if (pais != null) {
            //strPairs += "pais=" + EncodingUtilHelper.quotate(pais) + ",";
            strPairs += (hay) ? ",pais=" : "pais=";
            strPairs += EncodingUtilHelper.quotate(pais);
            hay = true;
        }
        if (telefono != null) {
            //strPairs += "telefono=" + EncodingUtilHelper.quotate(telefono) + ",";
            strPairs += (hay) ? ",telefono=" : "telefono=";
            strPairs += EncodingUtilHelper.quotate(telefono);
            hay = true;
        }
        if (movil != null) {
            //strPairs += "movil=" + EncodingUtilHelper.quotate(movil) + ",";
            strPairs += (hay) ? ",movil=" : "movil=";
            strPairs += EncodingUtilHelper.quotate(movil);
            hay = true;
        }
        if (email != null) {
            //strPairs += "email=" + EncodingUtilHelper.quotate(email) + ",";
            strPairs += (hay) ? ",email=" : "email=";
            strPairs += EncodingUtilHelper.quotate(email);
            hay = true;
        }
        if (web != null) {
            //strPairs += "web=" + EncodingUtilHelper.quotate(web) + ",";
            strPairs += (hay) ? ",web=" : "web=";
            strPairs += EncodingUtilHelper.quotate(web);
            hay = true;
        }
        if (fecha_alta != null) {
            //strPairs += "fecha_alta=" + EncodingUtilHelper.stringifyAndQuotate(fecha_alta) + ",";
            strPairs += (hay) ? ",fecha_alta=" : "fecha_alta=";
            strPairs += EncodingUtilHelper.stringifyAndQuotate(fecha_alta);
            hay = true;
        }
        if (fecha_baja != null) {
            //strPairs += "fecha_baja=" + EncodingUtilHelper.stringifyAndQuotate(fecha_baja) + ",";
            strPairs += (hay) ? ",fecha_baja=" : "fecha_baja=";
            strPairs += EncodingUtilHelper.stringifyAndQuotate(fecha_baja);
            hay = true;
        }
        if (id_sociedad != 0) {
            //strPairs += "id_sociedad=" + id_sociedad + ",";
            strPairs += (hay) ? ",id_sociedad=" : "id_sociedad=";
            strPairs += id_sociedad;
            hay = true;
        }
        if (id_tipoentidad != 0) {
            //strPairs += "id_tipoentidad=" + id_tipoentidad;
            strPairs += (hay) ? ",id_tipoentidad=" : "id_tipoentidad=";
            strPairs += id_tipoentidad;
        }    
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
            SociedadDao oSociedadDao = new SociedadDao(pooledConnection, oPuserBean_security, null);
            oSociedadBean.setId(oResultSet.getInt("id_sociedad"));
            oSociedadBean = oSociedadDao.get(oSociedadBean, expand - 1);
            this.setObj_sociedad(oSociedadBean);
        } else {
            this.setId_sociedad(oResultSet.getInt("id_sociedad"));
        }

        if (expand > 0) {
            TipoentidadBean oTipoentidadBean = new TipoentidadBean();
            TipoentidadDao oTipoentidadDao = new TipoentidadDao(pooledConnection, oPuserBean_security, null);
            oTipoentidadBean.setId(oResultSet.getInt("id_tipoentidad"));
            oTipoentidadBean = oTipoentidadDao.get(oTipoentidadBean, expand - 1);
            this.setObj_tipoentidad(oTipoentidadBean);
        } else {
            this.setId_tipoentidad(oResultSet.getInt("id_tipoentidad"));
        }

        return this;
    }

}
