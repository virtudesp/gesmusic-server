/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.dao.implementation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.implementation.PusuarioBean;
import net.daw.bean.implementation.MiembroBean;
import net.daw.dao.publicinterface.TableDaoInterface;
import net.daw.dao.publicinterface.ViewDaoInterface;
import net.daw.data.implementation.MysqlData;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.SqlBuilder;

public class MiembroDao implements ViewDaoInterface<MiembroBean>, TableDaoInterface<MiembroBean> {

    private String strTable = "miembro";
    private String strSQL = "select * from " + strTable + " where 1=1 ";
    private String strSQLCount = "SELECT COUNT(*) FROM " + strTable + " WHERE 1=1 ";
    private MysqlData oMysql = null;
    private Connection oConnection = null;
    private PusuarioBean oPuserSecurity = null;

    public MiembroDao(Connection oPooledConnection, PusuarioBean oPuserBean_security, String strWhere) throws Exception {
        try {
            oConnection = oPooledConnection;
            oMysql = new MysqlData(oConnection);
            oPuserSecurity = oPuserBean_security;
            if (strWhere != null) {
                strSQL += strWhere;
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
    }

    @Override
    public Long getCount(ArrayList<FilterBeanHelper> hmFilter) throws Exception {
        strSQLCount += SqlBuilder.buildSqlWhere(hmFilter);
        Long pages = 0L;
        try {
            pages = oMysql.getCount(strSQLCount);
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return pages;
    }

    // Para la relación tipomiembro:miembro -- 1:N
    public Long getCountXTipomiembro(int idTipomiembro, ArrayList<FilterBeanHelper> hmFilter) throws Exception {
        // definir la nueva condición de la sql
        strSQLCount += " and id_tipomiembro= " + idTipomiembro + " ";
        strSQLCount += SqlBuilder.buildSqlWhere(hmFilter);
        Long pages = 0L;
        try {
            pages = oMysql.getCount(strSQLCount);
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return pages;
    }

    @Override
    public ArrayList<MiembroBean> getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<MiembroBean> arrMiembro = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                MiembroBean oMiembroBean = new MiembroBean();
                arrMiembro.add((MiembroBean) oMiembroBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
            }
            if (oResultSet != null) {
                oResultSet.close();
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
        }
        return arrMiembro;
    }

    public ArrayList<MiembroBean> getPageXTipomiembro(int id_tipomiembro, int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir la nueva condición de la sql
        strSQLCount += " and id_tipomiembro= " + id_tipomiembro + " ";   
        strSQL += " and id_tipomiembro= " + id_tipomiembro + " ";        
        strSQL += SqlBuilder.buildSqlWhere(alFilter);        
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<MiembroBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                MiembroBean oUserBean = new MiembroBean();
                arrUser.add((MiembroBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
            }
            if (oResultSet != null) {
                oResultSet.close();
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
        }
        return arrUser;
    }
    
    @Override
    public ArrayList<MiembroBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<MiembroBean> arrMiembro = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                MiembroBean oMiembroBean = new MiembroBean();
                arrMiembro.add(oMiembroBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
        }
        return arrMiembro;
    }    
    // Para obtener todos los miembros de un tipomiembro
    public ArrayList<MiembroBean> getAllXTipomiembro(int id_tipomiembro, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir la nueva condición de la sql
        strSQL += " and id_tipomiembro= " + id_tipomiembro + " ";
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<MiembroBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                MiembroBean oUserBean = new MiembroBean();
                arrUser.add((MiembroBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
        }
        return arrUser;
    }

    @Override
    public MiembroBean get(MiembroBean oMiembroBean, Integer expand) throws Exception {
        if (oMiembroBean.getId() > 0) {
            ResultSet oResultSet = null;
            try {
                oResultSet = oMysql.getAllSQL(strSQL + " And id= " + oMiembroBean.getId() + " ");
                Boolean empty = true;
                while (oResultSet.next()) {
                    oMiembroBean = (MiembroBean) oMiembroBean.fill(oResultSet, oConnection, oPuserSecurity, expand);
                    empty = false;
                }
                if (empty) {
                    oMiembroBean.setId(0);
                }
            } catch (Exception ex) {
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                throw new Exception();
            } finally {
                if (oResultSet != null) {
                    oResultSet.close();
                }
            }
        } else {
            oMiembroBean.setId(0);
        }
        return oMiembroBean;
    }

    @Override
    public Integer set(MiembroBean oMiembroBean) throws Exception {
        Integer iResult = null;
        try {
            if (oMiembroBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oMiembroBean.getColumns() + ")";
                strSQL += "VALUES(" + oMiembroBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oMiembroBean.toPairs();
                strSQL += " WHERE id=" + oMiembroBean.getId();
                iResult = oMysql.executeUpdateSQL(strSQL);
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return iResult;
    }
    
    // Dado un tipomiembro, crear un nuevo miembro o modificar uno ya existente
    public Integer setXTipousuario(MiembroBean oUsuarioBean, Integer idTipousuario) throws Exception {
        Integer iResult = null;
        try {
            if (oUsuarioBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oUsuarioBean.getColumns() + ")";
                strSQL += "VALUES(" + oUsuarioBean.getValuesXTipomiembro(idTipousuario) + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable;
                strSQL += " SET " + oUsuarioBean.toPairsXTipomiembro(idTipousuario);
                strSQL += " WHERE id=" + oUsuarioBean.getId();
                iResult = oMysql.executeUpdateSQL(strSQL);
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return iResult;
    }

    @Override
    public Integer remove(Integer id) throws Exception {
        int result = 0;
        try {
            result = oMysql.removeOne(id, strTable);
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return result;
    }

}

