package Model;

import db.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AdminModelDB {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public ArrayList<Admin> getAllAdmins()
    {
        try
        {
            //Abrimos la conexión
            conexion.setConexion();
            //Definimos la consulta
            conexion.setConsulta("SELECT * FROM funcionario");

            //Obtenemos los resultados
            resultado = conexion.getResultado();
            ArrayList<Admin> am_list = new ArrayList<Admin>();
            while(resultado.next())
            {
                Admin admin_model = new Admin(resultado.getString("usuario"), resultado.getString("clave"));
                am_list.add(admin_model);
            }
            conexion.cerrarConexion();
            return am_list;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
