package com.techelevator;

import com.techelevator.inventory.InputDetails;
import com.techelevator.inventory.RestockMachine;
import com.techelevator.menu_process.FeedMoney;
import com.techelevator.menu_process.PurchaseItem;
import com.techelevator.menu_process.RunningBalance;
import com.techelevator.util.SalesReport;
import com.techelevator.util.VendoLog;
import com.techelevator.view.Menu;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = " ";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private static final int MAX_QUANTITY = 5;

	private Menu menu;
	private Menu purchaseMenu;

	private RestockMachine restock;

	public VendingMachineCLI(Menu menu, Menu purchaseMenu) {

		this.menu = menu;
		this.purchaseMenu = purchaseMenu;
	}

	public void run() {

		restock = new RestockMachine("vendingmachine.csv");
		Map<String, Product> inventory = restock.buildStock();
		PurchaseItem purchase = new PurchaseItem();
		FeedMoney feedMoney = new FeedMoney();
		Scanner userInput = new Scanner(System.in);

		//for log date formatting
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("MMddyyyy"));

		//for currency formatting
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		String oldBalStr;
		String currBalStr;
		boolean resetTran = false;

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				displayItemsToConsole(inventory);
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				while (true) {
					String purchaseChoice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						//do feed money
						int amtEntered = 0;
						while (true) {
							try {
								System.out.print("Enter amount: ");
								if (feedMoney.isValidMoney(userInput.nextBigDecimal())) {
									oldBalStr = currencyFormat.format(RunningBalance.getOldBalance());
									currBalStr = currencyFormat.format(RunningBalance.getCurrBalance());
									if (!resetTran) {
										VendoLog.log("FEED MONEY", currBalStr, currBalStr, formattedDate);
										resetTran = false;
									} else {
										VendoLog.log("FEED MONEY", oldBalStr, currBalStr, formattedDate);
									}
								}
								break;
							} catch (NumberFormatException e) {
								System.out.println("\n*** Please enter a valid amount.");
							}
						}
					} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						//do purchase
						displayItemsToConsole(inventory);
						System.out.print("\nEnter item code to buy: ");
						purchase.setCodeBought(userInput.next());
						purchase.updateInventoryAndBalance(inventory);
					} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						//finish and display change
						RunningBalance.giveChange();
						oldBalStr = currencyFormat.format(RunningBalance.getOldBalance());
						currBalStr = currencyFormat.format(RunningBalance.getCurrBalance());
						VendoLog.log("GIVE CHANGE", oldBalStr, currBalStr, formattedDate);
						resetTran = true;
						break;
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				break;
			} else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				SalesReport salesReport = new SalesReport("logs", formattedDate);
				salesReport.generateSalesReport(inventory);
			}
		}
	}

	public void displayItemsToConsole(Map<String, Product> displayItems) {

		System.out.println();

		//sort map by keys
		TreeMap<String, Product> sortedMap = new TreeMap<>(displayItems);
		Iterator itr = sortedMap.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();
			Product inventory = displayItems.get(key);

			System.out.print(key + " ");
			System.out.print(inventory.getName() + " ");
			if (inventory.getQuantity() == 0) {
				System.out.print("$" + inventory.getPrice());
				System.out.println("\t***SOLD OUT");
			} else {
				System.out.println("$" + inventory.getPrice());
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out, " ");
		Menu purchaseMenu = new Menu(System.in, System.out, "purchase");
		VendingMachineCLI cli = new VendingMachineCLI(menu, purchaseMenu);
		cli.run();
	}
}
