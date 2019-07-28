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
import net.daw.bean.implementation.ObraBean;
import net.daw.bean.implementation.RepertorioBean;
import net.daw.bean.implementation.PusuarioBean;
import net.daw.dao.publicinterface.TableDaoInterface;
import net.daw.dao.publicinterface.ViewDaoInterface;
import net.daw.data.implementation.MysqlData;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.SqlBuilder;

public class RepertorioDao implements ViewDaoInterface<RepertorioBean>, TableDaoInterface<RepertorioBean> {

    private String strTable = "repertorio";
    /*
   SELECT o.titulo, o.subtitulo, o.notas, CONCAT(co.nombre, ' ', co.apellidos)
FROM repertorio r, obra o, agrupacion ag, acto ac, compositor co
WHERE r.id_acto = ac.id
AND r.id_obra = o.id
AND o.id_compositor = co.id
AND r.id_agrupacion = ag.id
AND r.id_acto = 31
ORDER BY r.id_agrupacion
     */
    private String strSQL = "select * from " + strTable + " where 1=1 ";
    private String strSQLCount = "SELECT COUNT(*) FROM " + strTable + " WHERE 1=1 ";
//    private String strSQL = "SELECT ac.nombre, o.titulo, ag.agrupacion FROM repertorio r, obra o, agrupacion ag, acto ac "
//            + "WHERE r.id_acto = ac.id AND r.id_obra = o.id AND r.id_agrupacion = ag.id ";
    private MysqlData oMysql = null;
    private Connection oConnection = null;
    private PusuarioBean oPusuarioSecurity = null;

    public RepertorioDao(Connection oPooledConnection, PusuarioBean oPusuarioBean_security, String strWhere) throws Exception {
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

    // Para contar las obras del repertorio de un acto - Relación N:M
    public Long getCountXActo(int idActo, int idAgrupacion, ArrayList<FilterBeanHelper> hmFilter) throws Exception {
        // definir la nueva condición de la sql
        strSQLCount += " AND id_acto= " + idActo + " AND id_agrupacion=" + idAgrupacion;
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
    public ArrayList<RepertorioBean> getPage(int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
//        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += " ORDER BY id_agrupacion";
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
//        ArrayList<RepertorioBean> arrRepertorio = new ArrayList<>();
        ArrayList<RepertorioBean> arrRepertorio = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                RepertorioBean oRepertorioBean = new RepertorioBean();
                arrRepertorio.add(oRepertorioBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand));
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
        return arrRepertorio;
    }

//    La consulta del repertorio devuelve un array de obras
    public ArrayList<ObraBean> getPageXActo(int idActo, int idAgrupacion, int intRegsPerPag, int intPage, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir la nueva condición de la sql
        strSQLCount += " AND id_acto= " + idActo + " AND id_agrupacion=" + idAgrupacion;
//        strSQL += " AND id_acto= " + idActo + " AND id_agrupacion=" + idAgrupacion;
        // Nueva SQL
        strSQL = "SELECT o.id, o.titulo, IFNULL(o.subtitulo,' ') AS subtitulo, IFNULL(o.notas,' ') AS notas, o.id_compositor "
                + "FROM repertorio r, obra o, agrupacion ag, acto ac "
                + "WHERE r.id_acto = ac.id AND r.id_obra = o.id AND r.id_agrupacion = ag.id "
                + "AND r.id_acto = " + idActo + " AND r.id_agrupacion = " + idAgrupacion;
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += SqlBuilder.buildSqlLimit(oMysql.getCount(strSQLCount), intRegsPerPag, intPage);
//        ArrayList<RepertorioBean> arrUser = new ArrayList<>();
        ArrayList<ObraBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
//                RepertorioBean oUserBean = new RepertorioBean();
                ObraBean oUserBean = new ObraBean();
//                arrUser.add((RepertorioBean) oUserBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand));
                arrUser.add((ObraBean) oUserBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand));
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
    public ArrayList<RepertorioBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<RepertorioBean> arrRepertorio = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                RepertorioBean oRepertorioBean = new RepertorioBean();
                arrRepertorio.add(oRepertorioBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand));
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
        }
        return arrRepertorio;
    }

    // Para obtener todas las obras de un acto 
    public ArrayList<RepertorioBean> getAllXActo(int idActo, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        // definir la nueva condición de la sql
        strSQL += " AND id_acto= " + idActo + " ";
        strSQL += SqlBuilder.buildSqlWhere(alFilter);
//        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        strSQL += " ORDER BY id_agrupacion";
        ArrayList<RepertorioBean> arrUser = new ArrayList<>();
        ResultSet oResultSet = null;
        try {
            oResultSet = oMysql.getAllSQL(strSQL);
            while (oResultSet.next()) {
                RepertorioBean oUserBean = new RepertorioBean();
                arrUser.add((RepertorioBean) oUserBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand));
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
    public RepertorioBean get(RepertorioBean oRepertorioBean, Integer expand) throws Exception {
        if (oRepertorioBean.getId() > 0) {
            ResultSet oResultSet = null;
            try {
                oResultSet = oMysql.getAllSQL(strSQL + " And id= " + oRepertorioBean.getId() + " ");
                Boolean empty = true;
                while (oResultSet.next()) {
                    oRepertorioBean = oRepertorioBean.fill(oResultSet, oConnection, oPusuarioSecurity, expand);
                    empty = false;
                }
                if (empty) {
                    oRepertorioBean.setId(0);
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
            oRepertorioBean.setId(0);
        }
        return oRepertorioBean;
    }

    @Override
    public Integer set(RepertorioBean oRepertorioBean) throws Exception {
        Integer iResult = null;
        try {
            if (oRepertorioBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oRepertorioBean.getColumns() + ")";
                strSQL += "VALUES(" + oRepertorioBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oRepertorioBean.toPairs();
                strSQL += " WHERE id=" + oRepertorioBean.getId();
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
