package presentation;

import javax.swing.*;

public class ProductsView extends JFrame{
    private JPanel mainProductPanel;
    private JButton foodButton;
    private JButton antiparasiticButton;
    private JButton accessoriesButton;
    private JButton goBackProductButton;
    private ProductsController productsController;


    public ProductsView() {
        super("Products Manager");
        setContentPane(mainProductPanel);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        productsController = new ProductsController(this);
       // productsTable.setVisible(false);

        productsController.createFoodButtonActionListener(foodButton);
        productsController. createAccessoriesButtonActionListener(accessoriesButton);
        productsController.createGoBackProductButton(goBackProductButton);
    }
}
