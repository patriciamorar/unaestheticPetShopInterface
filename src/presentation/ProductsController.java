package presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductsController {
    //private MainView mainView;
    private ProductsView productsView;

    public ProductsController(ProductsView productsView) {
        this.productsView = productsView;
    }

    /**
     * Method that creates an action listener for the button that opens the Food Manager Window
     * @param foodButton
     */
    void createFoodButtonActionListener(JButton foodButton) {
        foodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FoodView();
                productsView.dispose();
            }
        });
    }


    /**
     * Method that creates an action listener for the button that opens the Accessories Manager Window
     * @param accessoriesButton
     */
    void createAccessoriesButtonActionListener(JButton accessoriesButton) {
        accessoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccessoriesView();
                productsView.dispose();
            }
        });
    }

    /**
     * Method that creates an action listener for the button that takes the user back to the main window of the application
     * @param goBackProductButton
     */
    public void createGoBackProductButton(JButton goBackProductButton) {
        goBackProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainView();
                productsView.dispose();
            }
        });
    }
}
