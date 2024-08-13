package Model;

import db.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;

public class SaleModelDB {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public ArrayList<Sale> getAllSales()
    {
        try
        {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("SELECT * FROM compra");
            //Obtenemos los resultados
            resultado = conexion.getResultado();
            ArrayList<Sale> sm_list = new ArrayList<Sale>();
            while(resultado.next())
            {
                Sale sale_model = new Sale(resultado.getInt("id"), resultado.getString("producto"), resultado.getDate("fecha"), resultado.getInt("total"));
                sm_list.add(sale_model);
            }
            conexion.cerrarConexion();
            return sm_list;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String RegisterSale(String date, String concatedItems, int total){
        try
        {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("INSERT INTO compra(fecha, producto, total) VALUES ('"+date+"','"+concatedItems+"',"+total+");");
            int rowsAffected = conexion.update();
            System.out.println("Rows Affected: " + rowsAffected);
            //Obtenemos los resultados
            //resultado = conexion.getResultado();

            conexion.cerrarConexion();
            return null;
            //return resultado.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Sale> getSalesByCustomer(int customerId)
    {
        try
        {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("SELECT CustomerID,  Items, Date, SalesAgent, Total, C.Name as CustomerName, C.LastName as CustomerLastName, C.email, C.preferedPaymentMethod\n" +
                    "FROM Sales S\n" +
                    "INNER Join Customer C ON S.CustomerID = C.id\n" +
                    "WHERE CustomerID = " + customerId);
            //Obtenemos los resultados
            resultado = conexion.getResultado();
            ArrayList<Sale> sm_list = new ArrayList<Sale>();
            while(resultado.next())
            {
                Sale sale_model = new Sale(resultado.getInt("CustomerID"), resultado.getString("Items"), resultado.getDate("Date"), resultado.getInt("Total"));
                sm_list.add(sale_model);
            }
            conexion.cerrarConexion();
            return sm_list;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
