package presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Method that creates an action listener for the button that opens the Products Manager Window
     * @param manageProductsButton
     */
    void createProductsButtonActionListener(JButton manageProductsButton) {
        manageProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductsView();
                mainView.dispose();
            }
        });
    }

    /**
     * Method that creates an action listener for the button that opens the Pets Manager Window
     * @param petsDetailsButton
     */
    void createPetsButtonActionListener(JButton petsDetailsButton) {
        petsDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PetsView();
                mainView.dispose();
            }
        });
    }

    /**
     * Method that creates an action listener for the button that exits the application
     * @param exitButton
     */
    void createExitButtonActionListener(JButton exitButton) {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
