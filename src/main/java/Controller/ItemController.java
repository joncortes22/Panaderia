package Controller;

import Model.Admin;
import Model.AdminModelDB;
import Model.Item;
import Model.ItemModelDB;
import Program.Main;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemController {
    private static ItemModelDB imdb = new ItemModelDB();
    static ArrayList<Item> inventory = imdb.getAllStock();

    //Logic & Methods


    public static Item getItemSelected(String name){
        Item returnItem = null;
        for (Item item : inventory){
            if (item.getName().equals(name)){
                returnItem = new Item(item);
                break;
            }
        }
        return returnItem;
    }

    public static String[] getAllAvailableProductNames(){
        ArrayList<String> itemList = new ArrayList<String>();
        for (Item item : inventory){
            if (item.getAvailability()>0){
                itemList.add(item.getName());
            }
        }
        String[] responseArray = itemList.toArray(new String[0]);
        Arrays.sort(responseArray);
        return responseArray;
    }





    public static Item extractProductSelected(String name){
        ArrayList<Item> itemList = new ArrayList<Item>();
        Item itemSelected = null;
        for (Item item : inventory){
            if (item.getName().equals(name)){
                itemSelected = item;
            }
        }
        return itemSelected;
    }



    public static void setNewAvailability(ArrayList<Item> inventory,  String name, int count){
        for (Item item : inventory){
            if (item.getName().equals(name)){
                item.setAvailability(item.getAvailability()-count);
                break;
            }
        }

    }

    public static boolean validateNameExistance(String name){
        boolean found = false;
        for (Item item : inventory){
            if (item.getName().equalsIgnoreCase(name)){
                found = true;
                break;
            }
        }
        return found;
    }

    public static int getProductAvailability(String name){
        int availability = 0;
        for (Item item : inventory){
            if (item.getName().equals(name)){
                availability = item.getAvailability();
                break;
            }
        }
        return availability;
    }

    public static int getIdByName(String name){
        int id = 0;
        for (Item item : inventory){
            if (item.getName().equals(name)){
                id = item.getId();
                break;
            }
        }
        return id;
    }
}
