package View;

import Controller.AdminController;
import Controller.ItemController;
import Controller.SaleController;
import Model.Item;
import Model.ItemModelDB;
import Model.Sale;
import Model.SaleModelDB;
import Program.Main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import java.util.Map;
public class MyFrame extends JFrame {

    static ArrayList<HashMap<String, String>> cart = new ArrayList<>();

    public MyFrame(String name, int width, int height, String winCall) throws IOException {
        /*
         * Este método se utiliza para generar ventanas, sus componentes dependerán de la elección de winCall
         * */
        setTitle(name);

        setSize(width, height);

        setResizable(false);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        switch (winCall){
            case "buy":
                winBuy();
                break;
            case "login":
                winLoginOptions();
                break;
            case "finishPurchase":
                winFinishPurchase();
                break;
            case "allProducts":
                winAllProducts();
                break;
            case "adminOptions":
                winAdminOptions();
                break;
            case "showSales":
                winShowSales();
                break;
        }

        setVisible(true);
    }

    private void winBuy() throws IOException {
        /*
         * Este método declara y añade los objetos del menú principal
         * */
        

        AtomicInteger cartCount = new AtomicInteger(0);

        BufferedImage originalImage = ImageIO.read(new File("img/shoppingcart.png"));
        Image resizedImage = originalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(resizedImage);
        JLabel lblCartImage = new JLabel(imageIcon);
        lblCartImage.setBounds(380, 20, 30, 30);

        JLabel lblCartCount = new JLabel(String.valueOf(cartCount));
        lblCartCount.setBounds(425, 20, 30, 30);

        JLabel lblTitle = new JLabel("Shop");
        lblTitle.setBounds(170, 20, 200, 50);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(60, 170, 200, 50);

        SpinnerNumberModel numberModel = new SpinnerNumberModel(1, 1, 1, 1);
        JSpinner spnQuantity = new JSpinner(numberModel);
        spnQuantity.setBounds(170, 180, 200, 30);

        JLabel lblProduct = new JLabel("Product:");
        lblProduct.setBounds(60, 120, 200, 50);

        JComboBox<String> cmbProducts;
        cmbProducts = new JComboBox<>(ItemController.getAllAvailableProductNames());
        cmbProducts.setBounds(170, 130, 200, 30);

        cmbProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) cmbProducts.getSelectedItem();

                spnQuantity.setValue(1);
                numberModel.setMaximum(ItemController.getProductAvailability(selectedProduct));
            }
        });

        JButton btnAddProduct = new JButton("Add to Cart");
        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                HashMap<String, String> itemToCart = new HashMap<String, String>();

                if (cart.size() == 0){
                    itemToCart.put("name", ItemController.extractProductSelected((String) cmbProducts.getSelectedItem()).getName());
                    itemToCart.put("unitPrice", String.valueOf(ItemController.extractProductSelected((String) cmbProducts.getSelectedItem()).getUnitPrice()));
                    itemToCart.put("unitBought", String.valueOf(spnQuantity.getValue()));

                    cart.add(itemToCart);
                } else {
                    boolean productFound = false;
                    for (HashMap<String, String> item : cart){
                        String str1 = item.get("name");
                        String str2 = ItemController.extractProductSelected((String) cmbProducts.getSelectedItem()).getName();
                        if (item.get("name").equals(ItemController.extractProductSelected((String) cmbProducts.getSelectedItem()).getName())){
                            int itemBought = Integer.parseInt(item.get("unitBought"));
                            itemBought = itemBought + Integer.parseInt(String.valueOf(spnQuantity.getValue()));
                            item.put("unitBought", String.valueOf(itemBought));
                            productFound = true;
                            break;
                        }
                    }
                    if (!productFound){
                        itemToCart.put("name", ItemController.extractProductSelected((String) cmbProducts.getSelectedItem()).getName());
                        itemToCart.put("unitPrice", String.valueOf(ItemController.extractProductSelected((String) cmbProducts.getSelectedItem()).getUnitPrice()));
                        itemToCart.put("unitBought", String.valueOf(spnQuantity.getValue()));

                        cart.add(itemToCart);
                    }
                }


                cartCount.incrementAndGet();
                lblCartCount.setText(String.valueOf(cartCount));
                //TODO
                //ItemController.setNewAvailability(Main.inventory, (String) cmbProducts.getSelectedItem(), (Integer) spnQuantity.getValue());
                cmbProducts.removeAllItems();
                for (String productName : ItemController.getAllAvailableProductNames()) {
                    cmbProducts.addItem(productName);
                }
                spnQuantity.setValue(1);
                revalidate();
                repaint();

            }
        });
        btnAddProduct.setBounds(60, 230, 100, 40);

        JButton btnFinishPurchase = new JButton("Finish");
        btnFinishPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cart.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Cart is Empty",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        setVisible(true);
                    } else{
                        dispose();
                        MyFrame finishPurchase = new MyFrame("Finish Purchase", 440, 410, "finishPurchase");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnFinishPurchase.setBounds(170, 230, 100, 40);

        add(lblCartImage);
        add(lblTitle);add(lblProduct);add(lblQuantity);add(lblCartCount);
        add(cmbProducts);add(spnQuantity);
        add(btnAddProduct);add(btnFinishPurchase);
    }

    private void winFinishPurchase() throws IOException {
         /** Este método declara y añade los objetos del menú principal
         * */


        JLabel lblTitle = new JLabel("Shopping Cart");
        lblTitle.setBounds(170, 15, 200, 50);

        String[] listData = new String[cart.size()];
        int counter = 0, purchaseTotal = 0;
        for (HashMap<String, String> item : cart){
            int itemTotal = Integer.parseInt(item.get("unitPrice")) * Integer.parseInt(item.get("unitBought"));
            purchaseTotal = purchaseTotal + itemTotal;
            listData[counter] = item.get("name") + " x" + item.get("unitBought") + " = $" + String.valueOf(itemTotal);
            counter++;
        }

        // Step 3: Create the JList with the data
        JList<String> cartList = new JList<>(listData);

        // Step 4: Add the JList to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(cartList);

        scrollPane.setBounds(60, 70, 300, 120);


        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(60, 185, 200, 50);

        JLabel lblTaxRate = new JLabel("Tax Rate:");
        lblTaxRate.setBounds(60, 215, 200, 50);

        JLabel lblTotalAndTaxRate = new JLabel("Total + tax:");
        lblTotalAndTaxRate.setBounds(60, 245, 200, 50);

        JLabel lblTotalValue = new JLabel("$" + String.valueOf(purchaseTotal));
        lblTotalValue.setBounds(310, 185, 200, 50);

        JLabel lblTaxRateValue = new JLabel("13%");
        lblTaxRateValue.setBounds(310, 215, 200, 50);

        String totalPlusTax = String.valueOf((purchaseTotal*0.13)+purchaseTotal);
        JLabel lblTotalAndTaxRateValue = new JLabel("$" + totalPlusTax);
        lblTotalAndTaxRateValue.setBounds(310, 245, 200, 50);



        JButton btnBuy = new JButton("Buy");
        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String concatProd = "";
                int purchaseTotal = 0;
                for (HashMap<String, String> item : cart){
                    int itemTotal = Integer.parseInt(item.get("unitPrice")) * Integer.parseInt(item.get("unitBought"));
                    purchaseTotal = purchaseTotal + itemTotal;
                    concatProd = concatProd + (item.get("name") + " x" + item.get("unitBought") + ";");
                }
                SaleModelDB smdb = new SaleModelDB();
                String date = String.valueOf(LocalDate.now());
                smdb.RegisterSale(date, concatProd, purchaseTotal);


                HashMap<Integer, Integer> prodUpdate = new HashMap<>();
                for (HashMap<String, String> item : cart){
                    prodUpdate.put(ItemController.getIdByName(item.get("name")), ItemController.getProductAvailability(item.get("name"))-Integer.parseInt(item.get("unitBought")));
                }
                ItemModelDB imdb = new ItemModelDB();
                for (Map.Entry<Integer, Integer> item : prodUpdate.entrySet()){
                    imdb.modifyProductAvailability(item.getKey(), item.getValue());
                }

                dispose();
                JOptionPane.showMessageDialog(null, "Order Completed");
                try {
                    MyFrame adminOptions = new MyFrame("Login", 480, 300, "adminOptions");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btnBuy.setBounds(60, 295, 100, 40);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                revalidate();
                repaint();

            }
        });
        btnCancel.setBounds(180, 295, 100, 40);



        add(lblTitle);add(scrollPane);
        add(lblTotal);add(lblTaxRate);add(lblTotalAndTaxRate);
        add(lblTotalValue);add(lblTaxRateValue);add(lblTotalAndTaxRateValue);
        add(btnBuy);add(btnCancel);
    }

    private void winLoginOptions() {
        /*
         * Este método declara y añade los objetos del menú principal
         * */
        JLabel lblTitle = new JLabel("Login");
        lblTitle.setBounds(215, 20, 200, 50);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(60, 70, 200, 50);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(150, 80, 200, 30);

        /*txtUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                e.consume();  // Ignore the key event
            }
            }
        });*/

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(60, 120, 200, 50);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 130, 200, 30);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 180, 200, 30);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] passArray = txtPassword.getPassword();
                String password = new String(passArray);
                if (AdminController.login(txtUser.getText(), password)){
                    try {
                        dispose();
                        MyFrame adminOptions = new MyFrame("Login", 480, 300, "adminOptions");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                    "User is not registered",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                }

            }
        });



        // Add components to the JFrame
        add(lblTitle);add(lblUser);add(lblPassword);
        add(txtUser);add(txtPassword);
        add(btnLogin);
    }

    private void winAllProducts() throws IOException {
        /*
         * Este método declara y añade los objetos del menú principal
         * */


        JLabel lblTitle = new JLabel("Shop");
        lblTitle.setBounds(170, 20, 200, 50);

        JLabel lblQuantity = new JLabel("Available:");
        lblQuantity.setBounds(60, 170, 200, 50);

        JLabel lblQuantityCount = new JLabel("");
        lblQuantityCount.setBounds(170, 180, 200, 30);

        JLabel lblUnitCost = new JLabel("Unit Cost:");
        lblUnitCost.setBounds(60, 220, 200, 50);

        JLabel lblUnitCostCount = new JLabel("");
        lblUnitCostCount.setBounds(170, 230, 200, 30);

        JLabel lblProduct = new JLabel("Product:");
        lblProduct.setBounds(60, 120, 200, 50);

        JComboBox<String> cmbProducts;
        cmbProducts = new JComboBox<>(ItemController.getAllAvailableProductNames());
        cmbProducts.setBounds(170, 130, 200, 30);

        cmbProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) cmbProducts.getSelectedItem();
                if (selectedProduct != null){
                    Item selectedItem = new Item(ItemController.getItemSelected(selectedProduct));
                    lblQuantityCount.setText(String.valueOf(selectedItem.getAvailability()));

                    lblUnitCostCount.setText("$" + String.valueOf(selectedItem.getUnitPrice()));
                }
            }
        });

        JButton btnFinishPurchase = new JButton("Sign Out");
        btnFinishPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    MyFrame login = new MyFrame("Login", 480, 300, "login");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnFinishPurchase.setBounds(170, 280, 100, 40);

        add(lblTitle);add(lblProduct);add(lblQuantity);add(cmbProducts);add(lblQuantityCount);
        add(lblUnitCost);add(lblUnitCostCount);
        add(btnFinishPurchase);
    }

    private void winAdminOptions() {
        /*
         * Este método declara y añade los objetos del menú principal
         * */
        JLabel lblTitle = new JLabel("Options");
        lblTitle.setBounds(190, 20, 200, 50);

        JButton btnAllProducts = new JButton("All Products");
        btnAllProducts.setBounds(120, 80, 200, 30);

        btnAllProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    MyFrame guestView = new MyFrame("Guest View", 480, 400, "allProducts");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        JButton btnOrder = new JButton("New Order");
        btnOrder.setBounds(120, 130, 200, 30);

        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    MyFrame buyItem = new MyFrame("Buy Item", 480, 400, "buy");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        JButton btnSales = new JButton("Sales History");
        btnSales.setBounds(120, 180, 200, 30);

        btnSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    MyFrame finishPurchase = new MyFrame("Show Sales", 440, 410, "showSales");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });




        // Add components to the JFrame
        add(lblTitle);
        add(btnAllProducts);add(btnOrder);add(btnSales);
    }

    private void winShowSales() throws IOException {
        /** Este método declara y añade los objetos del menú principal
         * */


        JLabel lblTitle = new JLabel("Shopping Cart");
        lblTitle.setBounds(170, 15, 200, 50);

        JLabel lblId = new JLabel("Sale ID:");
        lblId.setBounds(60, 70, 200, 30);

        JTextField txtId = new JTextField();
        txtId.setBounds(120, 70, 240, 30);

        txtId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                e.consume();  // Ignore the key event
            }
            }
        });

        // Step 3: Create the JList with the data
        JTextArea textArea = new JTextArea();

        // Step 4: Add the JList to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setBounds(60, 110, 300, 120);


        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(60, 230, 200, 50);

        JLabel lblTotalNum = new JLabel("$");
        lblTotalNum.setBounds(280, 230, 200, 50);



        JButton btnBuy = new JButton("Search");
        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sale sale = SaleController.getSale(Integer.parseInt(txtId.getText()));
                if (sale!=null){
                    String[] itemArray = sale.getItems().split(";");
                    String newText = "";
                    for (String item : itemArray){
                        newText = newText + item + "\n";
                    }
                    textArea.setText(newText);
                    lblTotalNum.setText("$" + sale.getTotal());
                } else {
                    JOptionPane.showMessageDialog(null,
                    "Sale does not exist",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        btnBuy.setBounds(60, 295, 100, 40);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    MyFrame adminOptions = new MyFrame("Login", 480, 300, "adminOptions");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnCancel.setBounds(180, 295, 100, 40);



        add(lblTitle);add(scrollPane);
        add(lblTotal);add(lblId);add(txtId);
        add(lblTotalNum);
        add(btnBuy);add(btnCancel);
    }

}