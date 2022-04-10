package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseMenu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String EXIT_MENU_OPTION = "Exit";
	private static final String SALES_REPORT = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, EXIT_MENU_OPTION, SALES_REPORT };
	Inventory inventory = new Inventory();
	private Menu menu;
	SalesLog saleLog = new SalesLog();
	PurchaseMenu purchaseMenu;


	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		inventory.createInventory("capstone\\vendingmachine.csv");
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				inventory.displayAvailableProducts();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				purchaseMenu = new PurchaseMenu(menu, inventory);
				purchaseMenu.run();

			} else if (choice.equals(EXIT_MENU_OPTION)) {
				break;
			} else if (choice.equals(SALES_REPORT)) {

				try (BufferedReader br = new BufferedReader
						(new FileReader("sales.log"))) {
					String line;
					while ((line = br.readLine()) != null) {

						System.out.println(line);

					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
