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
import java.sql.DriverManager;
import java.sql.SQLException;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.helper.statics.ConnectionClassHelper;
import net.daw.helper.statics.Log4j;

public class DriverManagerConnectionImpl implements ConnectionInterface {

    @Override
    public Connection newConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + ConnectionClassHelper.getDatabaseHost() + ":" + ConnectionClassHelper.getDatabasePort() + "/" + ConnectionClassHelper.getDatabaseName(), ConnectionClassHelper.getDatabaseLogin(), ConnectionClassHelper.getDatabasePassword());
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
            throw new Exception();
        }
    }

    @Override
    public void disposeConnection() throws Exception {
    }
}
// Cadena de conexión
//http://localhost:8081/gesmusic-server/json?ob=usuario&op=login&user=cacun&pass=cacun
//
//http://localhost:8081/gesmusic-server/json?ob=tipousuario&op=getall
//
//http://localhost:8081/gesmusic-server/json?ob=rol&op=getpage&page=2&10
//
//http://localhost:8081/gesmusic-server/json?ob=obra&op=getpage&foreign=3&page=1&rpp=10
//
//http://localhost:8081/gesmusic-server/json?ob=obra&op=set&foreign=7&json={"titulo":"Habia una vez"}
//http://localhost:8081/gesmusic-server/json?ob=obra&op=set&id=18&foreign=7&json={"id":"17","subtitulo":"pasodoble"}
//
//http://localhost:8081/gesmusic-server/json?ob=tipousuario&op=get&id=2
//
//http://localhost:8081/gesmusic-server/json?ob=tipousuario&op=set&json={"tipousuario":"nsnc"}

//ob=miembro&op=set&json={"numero":"S00022","nombre":"Rosa","apellidos":"Uno Dos","id_sociedad":"1","id_tipomiembro":"2"}
///http://localhost:8081/gesmusic-server/json?ob=elenco&op=set&json={"id_miembro":"1","id_agrupacion":"1","id_rol":"1"}
//http://localhost:8081/gesmusic-server/json?ob=tipomiembro&op=set&json={"id":"4","tipomiembro":"cocineroooooo"}

//http://localhost:8081/gesmusic-server/json?ob=usuario&op=set&json={"id":"0","username":"David","userpass":"iniested","id_tipousuario":"4"}

//ob=miembro&op=remove&id=63
//ALTER TABLE participa DROP FOREIGN KEY fk_participa_acto1;


//json?ob=obra&op=getcount&id=7