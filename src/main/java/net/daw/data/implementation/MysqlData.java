/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * sisane-server: Helps you to develop easily AJAX web applications 
 *                   by copying and modifying this Java Server.
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
package net.daw.data.implementation;

import net.daw.data.publicinterface.DataInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.daw.helper.statics.Log4j;

public class MysqlData implements DataInterface {

    Connection connection = null;

    public MysqlData(Connection pooledConnection) {
        connection = pooledConnection;
    }

    @Override
    public int executeUpdateSQL(String strSQL) throws Exception {
        ResultSet oResultSet = null;
        java.sql.PreparedStatement oPreparedStatement = null;
        int iResult = 0;
        try {
            oPreparedStatement = connection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            iResult = oPreparedStatement.executeUpdate();
            if (iResult == -1) {
                Log4j.errorLog(this.getClass().getName() + ":" + "executeUpdateSQL error");
                throw new SQLException();
            }
        } catch (SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return iResult;
    }

    @Override
    public int executeInsertSQL(String strSQL) throws Exception {
        ResultSet oResultSet = null;
        java.sql.PreparedStatement oPreparedStatement = null;
        int id = 0;
        try {
            oPreparedStatement = connection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            int returnLastInsertId = oPreparedStatement.executeUpdate();
            if (returnLastInsertId != -1) {
                oResultSet = oPreparedStatement.getGeneratedKeys();
                oResultSet.next();
                id = oResultSet.getInt(1);
            } else {
                Log4j.errorLog(this.getClass().getName() + ":" + "executeInsertSQL error");
                throw new SQLException();
            }
        } catch (SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return id;
    }

    @Override
    public int removeOne(int intId, String strTabla) throws Exception {
        PreparedStatement oPreparedStatement = null;
        int intResult = 0;
        try {
            String strSQL = "DELETE FROM " + strTabla + " WHERE id = ?";
            oPreparedStatement = (PreparedStatement) connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, intId);
            intResult = oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public String getId(String strTabla, String strCampo, String strValor) throws Exception {
        String strResult = null;
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT id FROM " + strTabla + " WHERE " + strCampo + "='" + strValor + "'";
            oResultSet = oStatement.executeQuery(strSQL);
            if (oResultSet.next()) {
                strResult = oResultSet.getString("id");
            } else {
                Log4j.errorLog(this.getClass().getName() + ":" + "getId error");
                throw new SQLException();
            }
        } catch (SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return strResult;
    }

    @Override
    public String getOne(String strSqlSelectDataOrigin, String strCampo, int id) throws Exception {
        String strResult = null;
        PreparedStatement oPreparedStatement = null;
        ResultSet oResultSet = null;
        String strSQL = "";
        try {
            strSQL = strSqlSelectDataOrigin.substring(0, strSqlSelectDataOrigin.indexOf("1=1") + 3) + " AND id=" + id + " " + strSqlSelectDataOrigin.substring(strSqlSelectDataOrigin.indexOf("1=1") + 3, strSqlSelectDataOrigin.length());
            oPreparedStatement = connection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                strResult = oResultSet.getString(strCampo);
            } else {
                Log4j.errorLog(this.getClass().getName() + ":" + "getOne error");
                throw new SQLException();
            }
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            oPreparedStatement.close();
        }
        return strResult;
    }

    @Override
    public Long getCount(String strSqlSelectDataOrigin) throws Exception {
        Long longResult = 0L;
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strNewSqlDataSource = "SELECT COUNT(*) " + strSqlSelectDataOrigin.substring(strSqlSelectDataOrigin.toLowerCase().indexOf("from"), strSqlSelectDataOrigin.length());
            oStatement = (Statement) connection.createStatement();
            oResultSet = oStatement.executeQuery(strNewSqlDataSource);
            while (oResultSet.next()) {
                longResult = oResultSet.getLong("COUNT(*)");
            }
        } catch (SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return longResult;
    }

    @Override
    public ResultSet getAllSQL(String strSqlSelectDataOrigin) throws Exception {
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            oStatement = (Statement) connection.createStatement();
            oResultSet = oStatement.executeQuery(strSqlSelectDataOrigin);
        } catch (Exception ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
        }
        return oResultSet;
    }

    public int truncateTable(String strTabla) throws Exception {
        PreparedStatement oPreparedStatement = null;
        int intResult = 0;
        try {
            Statement s = connection.createStatement();;
            s.addBatch("SET FOREIGN_KEY_CHECKS = 0");
            s.addBatch("TRUNCATE TABLE " + strTabla);
            s.addBatch("SET FOREIGN_KEY_CHECKS = 1");
            s.executeBatch();
        } catch (SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

}
