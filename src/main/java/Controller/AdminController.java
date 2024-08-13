package Controller;

import Model.Admin;
import Model.AdminModelDB;
import Model.Item;
import Model.ItemModelDB;

import java.util.ArrayList;

public class AdminController {
    private static AdminModelDB amdb = new AdminModelDB();
    static ArrayList<Admin> admins = amdb.getAllAdmins();


    public static boolean login(String id, String password){
        boolean userFound = false;
        for (Admin admin : admins){
            if (admin.getId().equals(id) && admin.getPassword().equals(password)){
                userFound = true;
                break;
            }
        }
        return userFound;
    }
}
