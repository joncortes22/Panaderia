package Controller;

import Model.Admin;
import Model.AdminModelDB;
import Model.Sale;
import Model.SaleModelDB;

import java.util.ArrayList;

public class SaleController {
    private static SaleModelDB smdb = new SaleModelDB();
    static ArrayList<Sale> sales = smdb.getAllSales();


    public static Sale getSale(int id){
        Sale returnSale = null;
        for (Sale sale : sales){
            if (sale.getId() == id){
                returnSale = sale;
                break;
            }
        }
        return returnSale;
    }
}
