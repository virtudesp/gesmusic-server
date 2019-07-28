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
import net.daw.bean.implementation.ActoBean;
import net.daw.bean.implementation.PusuarioBean;
import net.daw.bean.implementation.ObraBean;
import net.daw.dao.publicinterface.TableDaoInterface;
import net.daw.dao.publicinterface.ViewDaoInterface;
import net.daw.data.implementation.MysqlData;
import net.daw.helper.statics.AppConfigurationHelper;
import net.daw.helper.statics.EncodingUtilHelper;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.SqlBuilder;

public class ObraDao implements ViewDaoInterface<ObraBean>, TableDaoInterface<ObraBean> {

    private String strTable = "obra";
    private String strSQL = "select * from obra where 1=1 ";
    private String strSQLCount = "SELECT COUNT(*) FROM " + strTable + " WHERE 1=1 ";
    private MysqlData oMysql = null;
    private Connection oConnection = null;
    private PusuarioBean oPuserSecurity = null;

    public ObraDao(Connection oPooledConnection, PusuarioBean oPuserBean_security, String strWhere) throws Exception {
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

    // Para la relación compositor --> obras
    public Long getCountXCompositor(int idCompositor, ArrayList<FilterBeanHelper> hmFilter) throws Exception {
        // definir la nueva condición de la sql
        strSQLCount += " and id_compositor= " + idCompositor + " ";
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

    // Para obtener las obras de un repertorio
    public Long getCountXRepertorio(int idActo, int idAgrupacion, ArrayList<FilterBeanHelper> hmFilter) throws Exception {
        // definir la nueva  sql
        strSQLCount = "SELECT COUNT(*) FROM repertorio r, obra o, agrupacion ag, acto ac "
                + "WHERE r.id_acto = ac.id AND r.id_obra = o.id AND r.id_agrupacion = ag.id "
                + "AND r.id_acto = " + idActo + " AND r.id_agrupacion = " + idAgrupacion;
//        strSQLCount += SqlBuilder.buildSqlWhere(hmFilter);
        Long pages = 0L;
        try {
            pages = oMysql.getCount(strSQLCount);
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return pages;
    }
    
    // Para obtener los actos en los que se ha interpretado una obra    
    public Long getCountXHistorial(int idObra, ArrayList<FilterBeanHelper> hmFilter) throws Exception {
        // definir la nueva  sql
        strSQLCount = "SELECT COUNT(*) FROM acto a, repertorio r, obra o "
                + "WHERE o.id = r.id_obra AND a.id = r.id_acto "
                + "AND o.id = " + idObra;
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
    public ArrayList<ObraBean> getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<ObraBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                ObraBean oUserBean = new ObraBean();
                arrUser.add((ObraBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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

    public ArrayList<ObraBean> getPageXCompositor(int idCompositor, int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir las nuevas sql
        strSQLCount += " and id_compositor= " + idCompositor + " ";
        strSQL += " and id_compositor= " + idCompositor + " ";
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<ObraBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                ObraBean oUserBean = new ObraBean();
                arrUser.add((ObraBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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
    
    public ArrayList<ObraBean> getPageXRepertorio(int idActo, int idAgrupacion, int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir las nuevas sql
        strSQLCount = "SELECT COUNT(*) FROM repertorio r, obra o, agrupacion ag, acto ac "
                + "WHERE r.id_acto = ac.id AND r.id_obra = o.id AND r.id_agrupacion = ag.id "
                + "AND r.id_acto = " + idActo + " AND r.id_agrupacion = " + idAgrupacion;
        strSQL = "SELECT o.titulo, o.subtitulo, o.notas, o.id_compositor "
                + "FROM repertorio r, obra o, agrupacion ag, acto ac "
                + "WHERE r.id_acto = ac.id AND r.id_obra = o.id AND r.id_agrupacion = ag.id "
                + "AND r.id_acto = " + idActo + " AND r.id_agrupacion = " + idAgrupacion;
//        strSQL += SqlBuilder.buildSqlWhere(alFilter);
//        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<ObraBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                ObraBean oUserBean = new ObraBean();
                arrUser.add((ObraBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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

    public ArrayList<ActoBean> getPageXHistorial(int idObra, int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir las nuevas sql        
        strSQLCount = "SELECT COUNT(*) FROM acto a, repertorio r, obra o "
                + "WHERE o.id = r.id_obra AND a.id = r.id_acto "
                + "AND o.id = " + idObra;
        strSQL = "SELECT a.nombre, IFNULL(a.parte,' ') AS parte, a.lugar, a.fecha "
                + "FROM acto a, repertorio r, obra o "
                + "WHERE o.id = r.id_obra AND a.id = r.id_acto "
                + "AND o.id = " + idObra;
//        strSQL += SqlBuilder.buildSqlWhere(alFilter);
//        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
        ArrayList<ActoBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                ActoBean oActoBean = new ActoBean();
                arrUser.add((ActoBean) oActoBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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

    /*
    
        // Nueva SQL
SELECT a.nombre, IFNULL(a.parte,' ') AS parte, a.lugar, a.fecha 
FROM acto a, repertorio r, obra o 
WHERE 1
AND o.id = r.id_obra
AND a.id = r.id_acto
AND o.id = 29
//                + "AND r.id_acto = " + idActo + " AND r.id_agrupacion = " + idAgrupacion;
     */
    @Override
    public ArrayList<ObraBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<ObraBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                ObraBean oUserBean = new ObraBean();
                arrUser.add((ObraBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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

    // Para obtener todas las obras de un compositor    @Override
    public ArrayList<ObraBean> getAllXCompositor(int idCompositor, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir la nueva condición de la sql
        strSQL += " and id_compositor= " + idCompositor + " ";
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<ObraBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                ObraBean oUserBean = new ObraBean();
                arrUser.add((ObraBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand));
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
    public ObraBean get(ObraBean oUserBean, Integer expand) throws Exception {
        if (oUserBean.getId() > 0) {
            ResultSet oResultSet = null;
            try {
                oResultSet = oMysql.getAllSQL(strSQL + " And id= " + oUserBean.getId() + " ");
                Boolean empty = true;
                while (oResultSet.next()) {
                    oUserBean = (ObraBean) oUserBean.fill(oResultSet, oConnection, oPuserSecurity, expand);
                    empty = false;
                }
                if (empty) {
                    oUserBean.setId(0);
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
            oUserBean.setId(0);
        }
        return oUserBean;
    }

    @Override
    public Integer set(ObraBean oObraBean) throws Exception {
        Integer iResult = null;
        try {
            if (oObraBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oObraBean.getColumns() + ")";
                strSQL += "VALUES(" + oObraBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oObraBean.toPairs();
                strSQL += " WHERE id=" + oObraBean.getId();
                iResult = oMysql.executeUpdateSQL(strSQL);
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return iResult;
    }

    // Dado un compositor, crear una nueva obra o modificar una ya existente
    public Integer setXCompositor(ObraBean oObraBean, Integer idCompositor) throws Exception {
        Integer iResult = null;
        try {
            if (oObraBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oObraBean.getColumns() + ")";
                strSQL += "VALUES(" + oObraBean.getValuesXCompositor(idCompositor) + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable;
                strSQL += " SET " + oObraBean.toPairsXCompositor(idCompositor);
                strSQL += " WHERE id=" + oObraBean.getId();
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

    public PusuarioBean getFromLogin(PusuarioBean oPuser) throws Exception {
        try {
            String strId = oMysql.getId(strTable, "username", oPuser.getUsername());
            if (strId == null) {
                oPuser.setId(0);
            } else {
                Integer intId = Integer.parseInt(strId);
                oPuser.setId(intId);
                String pass = oPuser.getUserpass();
                oPuser.setUserpass(oMysql.getOne(strSQL, "userpass", oPuser.getId()));
                if (!pass.equals(oPuser.getUserpass())) {
                    oPuser.setId(0);
                }
                oPuser = this.getP(oPuser, AppConfigurationHelper.getJsonMsgDepth());
            }
            return oPuser;
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
    }

    public PusuarioBean getP(PusuarioBean oPuserBean, Integer expand) throws Exception {
        if (oPuserBean.getId() > 0) {
            ResultSet oResultSet = null;
            try {
                oResultSet = oMysql.getAllSQL(strSQL + " And id= " + oPuserBean.getId() + " ");
                Boolean empty = true;
                while (oResultSet.next()) {
                    oPuserBean = (PusuarioBean) oPuserBean.fill(oResultSet, oConnection, oPuserSecurity, expand);
                    empty = false;
                }
                if (empty) {
                    oPuserBean.setId(0);
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
            oPuserBean.setId(0);
        }
        return oPuserBean;
    }

    public Integer setP(PusuarioBean oPuserBean) throws Exception {
        //only 4 fill service
        Integer iResult = null;
        try {
            if (oPuserBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oPuserBean.getColumns() + ")";
                strSQL += "VALUES(" + oPuserBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oPuserBean.toPairs();
                strSQL += " WHERE id=" + oPuserBean.getId();
                iResult = oMysql.executeUpdateSQL(strSQL);
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return iResult;
    }

    public Integer passchange(String oldPassword, String newPassword) throws Exception {
        Integer iResult = null;
        try {
            strSQL = "UPDATE user";
            strSQL += " SET password = " + EncodingUtilHelper.quotate(newPassword);
            strSQL += " WHERE id=" + oPuserSecurity.getId();
            strSQL += " and password=" + EncodingUtilHelper.quotate(oldPassword);
            iResult = oMysql.executeUpdateSQL(strSQL);
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return iResult;
    }
}
