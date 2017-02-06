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
package net.daw.connection.implementation;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.helper.statics.ConnectionClassHelper;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.Log4j;

public class BoneConnectionPoolImpl implements ConnectionInterface {

    private BoneCP connectionPool = null;

    @Override
    public Connection newConnection() throws Exception {
        Connection c = null;
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(ConnectionClassHelper.getConnectionChain());
        config.setUsername(ConnectionClassHelper.getDatabaseLogin());
        config.setPassword(ConnectionClassHelper.getDatabasePassword());
        config.setMinConnectionsPerPartition(1);
        config.setMaxConnectionsPerPartition(2);
        config.setPartitionCount(1);
        try {
            connectionPool = new BoneCP(config); // setup the connection pool
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(
                    this.getClass().getName() + ":newConnection ERROR al crear la clase BoneCP: " + ex.getMessage()
            //                            + " // " 
            //                            + ConnectionClassHelper.getConnectionChain() 
            //                            + " // " 
            //                            + ConnectionClassHelper.getOpenShift() 
            //                            + " // " 
            //                            + ConnectionClassHelper.getDatabaseName()
            //                            + " // " 
            //                            + ConnectionClassHelper.getDatabaseLogin()
            //                            + " // " 
            //                            + ConnectionClassHelper.getDatabasePassword() 
            //                            + " // " 
            //                            + ConnectionClassHelper.getDatabasePort()
            //                            + " // " 
            //                            + ConnectionClassHelper.getDatabaseHost()
            ));
        }
        try {
            c = connectionPool.getConnection();
        } catch (SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
        return c;

    }

    @Override
    public void disposeConnection() throws Exception {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }
}
