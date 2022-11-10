package presentation;

import javax.swing.*;

public class MainView extends JFrame{
    private JPanel mainPanel;
    private JButton manageProductsButton;
    private JButton petsDetailsButton;
    private JButton exitButton;
    private MainController mainController;

    public MainView() {
        super("PetShop Menu");
        setContentPane(mainPanel);
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        mainController = new MainController(this);
        mainController.createProductsButtonActionListener(manageProductsButton);
        mainController.createPetsButtonActionListener(petsDetailsButton);
        mainController.createExitButtonActionListener(exitButton);
    }
}
