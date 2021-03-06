package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseMenu;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VendingMachineCLI {

    //<editor-fold desc="*DATA MEMBERS">
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String EXIT_MENU_OPTION = "Exit";
    private static final String SALES_REPORT = "Sales Report";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, EXIT_MENU_OPTION, SALES_REPORT};
    Inventory inventory = new Inventory();
    private String salesFilePath = "log\\sales.log";
    private String vendingMachineCSV = "vendingmachine.csv";
    private Path logPath = Paths.get("log");
    SalesLog salesLog = new SalesLog(salesFilePath);
    private Menu menu;
    PurchaseMenu purchaseMenu;
    GenerateLogTime generateLogTime = new GenerateLogTime();

    //</editor-fold>

    public VendingMachineCLI(Menu menu) {
        generateLogTime.logFolder(logPath);
        this.menu = menu;
        inventory.createInventory(vendingMachineCSV);
    }


    public void run() {
        mainLoop:
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            switch (choice) {
                case MAIN_MENU_OPTION_DISPLAY_ITEMS:
                    // display vending machine items
                    inventory.displayAvailableProducts();

                    break;
                case MAIN_MENU_OPTION_PURCHASE:
                    // do purchase
                    purchaseMenu = new PurchaseMenu(menu, inventory, salesFilePath);
                    purchaseMenu.run();

                    break;
                case EXIT_MENU_OPTION:
                    break mainLoop;
                case SALES_REPORT:
                    Path salesLogCheck = Paths.get(salesFilePath);
                    if (salesLogCheck.toFile().isFile()) {
                        salesLog.generateFileName();
                    } else {
                        System.out.println("Sales Log is Empty!");
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}