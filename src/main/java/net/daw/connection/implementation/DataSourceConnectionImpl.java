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

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.helper.statics.ConnectionClassHelper;
import net.daw.helper.statics.Log4j;

public class DataSourceConnectionImpl implements ConnectionInterface {

    InitialContext initialContext;

    @Override
    public Connection newConnection() throws Exception {

        try {
            initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/" + ConnectionClassHelper.getDatabaseName());
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (NamingException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void disposeConnection() throws Exception {
        try {
            if (initialContext != null) {
                initialContext.close();
            }
        } catch (NamingException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
    }
}
