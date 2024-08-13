package Model;

import db.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ItemModelDB {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public ArrayList<Item> getAllStock() {
        try {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("SELECT * FROM producto");
            //Obtenemos los resultados
            resultado = conexion.getResultado();
            ArrayList<Item> im_list = new ArrayList<>();
            while (resultado.next()) {
                Item item_model = new Item(resultado.getInt("id"), resultado.getString("nombre"), resultado.getInt("precio"), resultado.getInt("cantidad"));
                im_list.add(item_model);
            }
            conexion.cerrarConexion();
            return im_list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void modifyProductAvailability(int id, int availability) {
        try {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("UPDATE producto\n" +
                    "SET cantidad =" + availability + "\n" +
                    "WHERE ID = " + id);
            //Obtenemos los resultados
            //resultado = conexion.getResultado();
            int rowsAffected = conexion.update();
            System.out.println("Rows Affected: " + rowsAffected);

            conexion.cerrarConexion();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        public Item insertNewProduct;(int id, int availability){
            try
            {
                //Abrimos la conexión
                conexion.setConexion();
                //Definimos la consulta
                conexion.setConsulta("UPDATE STOCK\n" +
                        "SET Availability =" + availability +"\n" +
                        "WHERE ID = 1");
                //Obtenemos los resultados
                resultado = conexion.getResultado();


                conexion.cerrarConexion();
                return resultado.toString();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

     */
}

