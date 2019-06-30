/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * bauxer server: Helps you to develop easily AJAX web applications 
 *               by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/bauxer
 * 
 * bauxer server is distributed under the MIT License (MIT)
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
import net.daw.bean.implementation.AsisteactoBean;
import net.daw.bean.implementation.PusuarioBean;
import net.daw.dao.publicinterface.TableDaoInterface;
import net.daw.dao.publicinterface.ViewDaoInterface;
import net.daw.data.implementation.MysqlData;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.SqlBuilder;

public class AsisteactoDao implements ViewDaoInterface<AsisteactoBean>, TableDaoInterface<AsisteactoBean> {

    private String strTable = "asisteacto";
    private String strSQL = "select * from " + strTable + " where 1=1 ";
    private String strSQLCount = "SELECT COUNT(*) FROM " + strTable + " WHERE 1=1 ";
    private MysqlData oMysql = null;
    private Connection oConnection = null;
    private PusuarioBean oPusuarioSecurity = null;

    public AsisteactoDao(Connection oPooledConnection, PusuarioBean oPusuarioBean_security, String strWhere) throws Exception {
        try {
            oConnection = oPooledConnection;
            oMysql = new MysqlData(oConnection);
            oPusuarioSecurity = oPusuarioBean_security;
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
    public ArrayList<AsisteactoBean> getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<AsisteactoBean> arrAsisteacto = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                AsisteactoBean oAsisteactoBean = new AsisteactoBean();
                arrAsisteacto.add(oAsisteactoBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand));
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
        return arrAsisteacto;
    }

    @Override
    public ArrayList<AsisteactoBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<AsisteactoBean> arrAsisteacto = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                AsisteactoBean oAsisteactoBean = new AsisteactoBean();
                arrAsisteacto.add(oAsisteactoBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand));
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
        }
        return arrAsisteacto;
    }

    @Override
    public AsisteactoBean get(AsisteactoBean oAsisteactoBean, Integer expand) throws Exception {
        if (oAsisteactoBean.getId() > 0) {
            ResultSet oResultSet = null;
            try {
                oResultSet = oMysql.getAllSQL(strSQL + " And id= " + oAsisteactoBean.getId() + " ");
                Boolean empty = true;
                while (oResultSet.next()) {
                    oAsisteactoBean = oAsisteactoBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand);
                    empty = false;
                }
                if (empty) {
                    oAsisteactoBean.setId(0);
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
            oAsisteactoBean.setId(0);
        }
        return oAsisteactoBean;
    }

    @Override
    public Integer set(AsisteactoBean oAsisteactoBean) throws Exception {
        Integer iResult = null;
        try {
            if (oAsisteactoBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oAsisteactoBean.getColumns() + ")";
                strSQL += "VALUES(" + oAsisteactoBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oAsisteactoBean.toPairs();
                strSQL += " WHERE id=" + oAsisteactoBean.getId();
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
