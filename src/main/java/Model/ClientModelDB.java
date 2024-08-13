package Model;


import db.ConexionBD;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ClientModelDB {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public ArrayList<Client> getAllUsers()
    {
        try
        {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("SELECT U.id as UserId, username, password, Roleid, R.role as RoleType, C.id as CustomerId, C.Name as CustomerName, C.lastName as CustomerLastName, Address, MoneySum, PreferedPaymentMethod\n" +
                    "FROM User U\n" +
                    "INNER JOIN RoleCatalog R ON U.RoleId = R.id\n" +
                    "INNER JOIN Customer C ON U.id = C.id\n" +
                    "WHERE U.roleid = 2");
            //Obtenemos los resultados
            resultado = conexion.getResultado();
            ArrayList<Client> cm_list = new ArrayList<Client>();
            while(resultado.next())
            {
                Client client_model = new Client(resultado.getInt("UserId"), resultado.getString("CustomerName"), resultado.getString("CustomerLastName"), resultado.getString("Address"), resultado.getString("email"),resultado.getInt("MoneySum"), resultado.getString("PreferedPaymentMethod"));
                cm_list.add(client_model);
            }
            conexion.cerrarConexion();
            return cm_list;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Client getUserByUsername(String username)
    {
        try
        {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("SELECT U.id as UserId, username, password, Roleid, R.role as RoleType, C.id as CustomerId, C.Name as CustomerName, C.lastName as CustomerLastName, Address, MoneySum, PreferedPaymentMethod\n" +
                    "FROM User U\n" +
                    "INNER JOIN RoleCatalog R ON U.RoleId = R.id\n" +
                    "INNER JOIN Customer C ON U.id = C.id\n" +
                    "WHERE U.roleid = 2 AND username = '"+ username + "'");
            //Obtenemos los resultados
            resultado = conexion.getResultado();

            while(resultado.next())
            {
                Client client_model = new Client(resultado.getInt("UserId"), resultado.getString("CustomerName"), resultado.getString("CustomerLastName"), resultado.getString("Address"), resultado.getString("email"),resultado.getInt("MoneySum"), resultado.getString("PreferedPaymentMethod"));
                return client_model;
            }

            conexion.cerrarConexion();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String InsertNewCustomer(String username, String password, String customerName, String customerLastName, String address, String email, int moneySum, String preferedPaymentMethod){
        try
        {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("INSERT INTO User(Username, Password, RoleId)\n" +
                    "VALUES (" + username +", " + password + ", 2);" +
                    "INSERT INTO Customer(id, name, LastName, Address, Email, MoneySum, PreferedPaymentMethod)\n" +
                    "VALUES ("+ username + ",'"+ customerName + "','" + customerLastName + "', '" + address + "','" + email + "' ," + moneySum + ", '" + preferedPaymentMethod +"')");
            //Obtenemos los resultados
            resultado = conexion.getResultado();


            conexion.cerrarConexion();
            return resultado.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

