/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frmabm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author C.andres
 */
public class FAbm extends Conexion {

    void InsertarPersonas(String Nombre, int Dni) {
        String Consulta = "Insert into personas(Nombre,Dni) values('" + Nombre + "','" + Dni + "')";
        Statement Comando;
        Conectar();
        try {
            Comando = Conector.createStatement();
            Comando.executeUpdate(Consulta);//ejecutamos consulta
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            Desconectar();
        }
    }

    void Editar(int ID, String Nombre, int Dni) {
        String Consulta = "Update personas set Nombre='" + Nombre + "',Dni='"+ Dni +"' Where IDPersona="+ ID;
        Statement Comando;
        Conectar();
        try {
            Comando = Conector.createStatement();
            Comando.executeUpdate(Consulta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        finally{
            Desconectar();
        }
    }
    
    void Eliminar(int ID){
        String Consulta = "Delete from personas Where IDPersona="+ID;
        Statement Comando;
        Conectar();
        try {
            Comando = Conector.createStatement();
            Comando.executeUpdate(Consulta);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
        }
        finally{
            Desconectar();
        }
    }

    DefaultTableModel CargaGrilla() {

        String Consulta = "Select * from personas";
        DefaultTableModel Modelo = new DefaultTableModel(); //es similar a DataTable en Visual Basic.net
        Statement Comando;
        ResultSet RS; //
        ResultSetMetaData RSMD;
        Conectar(); //nos conectamos a MySql
        try {
            Comando = Conector.createStatement(); //preparamos el conector para armar el comando
            RS = Comando.executeQuery(Consulta); //ejecutamos consulta
            RSMD = RS.getMetaData(); //obtiene los metadatos del resultset
            int Columnas;
            Columnas = RSMD.getColumnCount(); //cuenta la cantidad de columnas que hay
            //recorremos el ResultSetMetaData
            for (int i = 1; i <= Columnas; i++) {
                Modelo.addColumn(RSMD.getColumnLabel(i)); //obtiene el nombre de la columna
            }
            //comenzamos a recorrer columna por columa del ResultSet, obteniendo el contenido de las mismas
            while (RS.next()) {
                Object Fila[] = new Object[Columnas]; //armamos un array
                for (int a = 0; a < Columnas; a++) {
                    Fila[a] = RS.getObject(a + 1); //se le pone + 1 como arreglo
                }
                Modelo.addRow(Fila);
            }
            return Modelo;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        } finally {
            Desconectar();
        }
    }
}

