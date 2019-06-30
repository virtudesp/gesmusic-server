/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * sisane-server: Helps you to develop easily AJAX web applications 
 *               by copying and modifying this Java Server.
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
package net.daw.dao.implementation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.implementation.PusuarioBean;
import net.daw.bean.implementation.RolBean;
import net.daw.dao.publicinterface.TableDaoInterface;
import net.daw.dao.publicinterface.ViewDaoInterface;
import net.daw.data.implementation.MysqlData;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.SqlBuilder;

public class RolDao implements ViewDaoInterface<RolBean>, TableDaoInterface<RolBean> {

    private String strTable = "rol";
    private String strSQL = "select * from " + strTable + " where 1=1 ";
    private String strSQLCount = "SELECT COUNT(*) FROM " + strTable + " WHERE 1=1 ";
    private MysqlData oMysql = null;
    private Connection oConnection = null;
    private PusuarioBean oPuserSecurity = null;

    public RolDao(Connection oPooledConnection, PusuarioBean oPuserBean_security, String strWhere) throws Exception {
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
    public ArrayList<RolBean> getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<RolBean> arrRol = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                RolBean oRolBean = new RolBean();
                arrRol.add(oRolBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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
        return arrRol;
    }

    @Override
    public ArrayList<RolBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<RolBean> arrRol = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                RolBean oRolBean = new RolBean();
                arrRol.add(oRolBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
        }
        return arrRol;
    }

    @Override
    public RolBean get(RolBean oRolBean, Integer expand) throws Exception {
        if (oRolBean.getId() > 0) {
            ResultSet oResultSet = null;
            try {
                oResultSet = oMysql.getAllSQL(strSQL + " And id= " + oRolBean.getId() + " ");
                Boolean empty = true;
                while (oResultSet.next()) {
                    oRolBean = oRolBean.fill(oResultSet, oConnection, oPuserSecurity, expand);
                    empty = false;
                }
                if (empty) {
                    oRolBean.setId(0);
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
            oRolBean.setId(0);
        }
        return oRolBean;
    }

    @Override
    public Integer set(RolBean oRolBean) throws Exception {
        Integer iResult = null;
        try {
            if (oRolBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oRolBean.getColumns() + ")";
                strSQL += "VALUES(" + oRolBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oRolBean.toPairs();
                strSQL += " WHERE id=" + oRolBean.getId();
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
