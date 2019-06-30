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
import net.daw.bean.implementation.SociedadBean;
import net.daw.dao.publicinterface.TableDaoInterface;
import net.daw.dao.publicinterface.ViewDaoInterface;
import net.daw.data.implementation.MysqlData;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.SqlBuilder;

public class SociedadDao implements ViewDaoInterface<SociedadBean>, TableDaoInterface<SociedadBean> {

    private String strTable = "sociedad";
    private String strSQL = "select * from " + strTable + " where 1=1 ";
    private String strSQLCount = "SELECT COUNT(*) FROM " + strTable + " WHERE 1=1 ";
    private MysqlData oMysql = null;
    private Connection oConnection = null;
    private PusuarioBean oPuserSecurity = null;

    public SociedadDao(Connection oPooledConnection, PusuarioBean oPuserBean_security, String strWhere) throws Exception {
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

    @Override
    public ArrayList<SociedadBean> getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<SociedadBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                SociedadBean oSociedadBean = new SociedadBean();
                arrUser.add((SociedadBean) oSociedadBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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
    public ArrayList<SociedadBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<SociedadBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                SociedadBean oSociedadBean = new SociedadBean();
                arrUser.add((SociedadBean) oSociedadBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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
    public SociedadBean get(SociedadBean oSociedadBean, Integer expand) throws Exception {
        if (oSociedadBean.getId() > 0) {
            ResultSet oResultSet = null;
            try {
                oResultSet = oMysql.getAllSQL(strSQL + " And id= " + oSociedadBean.getId() + " ");
                Boolean empty = true;
                while (oResultSet.next()) {
                    oSociedadBean = (SociedadBean) oSociedadBean.fill(oResultSet, oConnection, oPuserSecurity, expand);
                    empty = false;
                }
                if (empty) {
                    oSociedadBean.setId(0);
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
            oSociedadBean.setId(0);
        }
        return oSociedadBean;
    }

    @Override
    public Integer set(SociedadBean oSociedadBean) throws Exception {
        Integer iResult = null;
        try {
            if (oSociedadBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oSociedadBean.getColumns() + ")";
                strSQL += "VALUES(" + oSociedadBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oSociedadBean.toPairs();
                strSQL += " WHERE id=" + oSociedadBean.getId();
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

