package com.techelevator;

import com.techelevator.inventory.InputDetails;
import com.techelevator.inventory.RestockMachine;
import com.techelevator.menu_process.FeedMoney;
import com.techelevator.menu_process.PurchaseItem;
import com.techelevator.menu_process.RunningBalance;
import com.techelevator.util.SalesReport;
import com.techelevator.util.VendoLog;
import com.techelevator.view.MakeChange;
import com.techelevator.view.Menu;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.setOut;

public class VendingMachineCLI {

	private static final String[] MAIN = {"Main Menu"};

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
	private Menu mainMenu;

	private RestockMachine restock;
	private MakeChange change;
	private BigDecimal balance = new BigDecimal("0.00");

	private PurchaseItem purchase = new PurchaseItem();
	private FeedMoney feedMoney = new FeedMoney();

	//for log date formatting
	private LocalDate today = LocalDate.now();
	private String formattedDate = today.format(DateTimeFormatter.ofPattern("MMddyyyy"));

	//for currency formatting
	private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	private String oldBalStr;
	private String currBalStr;
	boolean resetTran = false;

	public VendingMachineCLI(Menu menu, Menu purchaseMenu, MakeChange change, Menu mainMenu) {

		this.menu = menu;
		this.purchaseMenu = purchaseMenu;
		this.change = change;
		this.mainMenu = mainMenu;
	}

	public void run() {

		Scanner userInput = new Scanner(System.in);

		//restock vending machine
		restockVendo();
		Map<String, Product> inventory = restock.buildStock();

		welcome();
		mainMenu.getChoiceFromOptions(MAIN);

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
						callFeedMoney();
					} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						//do purchase
						displayItemsToConsole(inventory);
						System.out.print("\nEnter item code to buy: ");
						purchase.setCodeBought(userInput.next());
						purchase.updateInventoryAndBalance(inventory);
					} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						//finish and display change
						processChange();
						finalizeTran();
						resetTran = true;
						break;
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("\nThank You For Your Purchase!");
				break;
			} else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				SalesReport salesReport = new SalesReport("logs", formattedDate);
				salesReport.generateSalesReport(inventory);
			}
		}
	}

	public void displayItemsToConsole(Map<String, Product> displayItems) {

		System.out.println();

		//print header
		System.out.printf("%-6s %-20s %-30s %n", "CODE", "DESCRIPTION", "PRICE");
		System.out.println("=================================");
		//sort map by keys
		TreeMap<String, Product> sortedMap = new TreeMap<>(displayItems);
		Iterator itr = sortedMap.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();
			Product inventory = displayItems.get(key);

			System.out.printf("%-6s %-21s", key, inventory.getName());
			if (inventory.getQuantity() == 0) {
				System.out.print("$" + inventory.getPrice());
				System.out.println("\t***SOLD OUT");
			} else {
				System.out.println("$" + inventory.getPrice());
			}
		}
		//line below
		System.out.println("=================================");
	}

	private void restockVendo() {

		restock = new RestockMachine("vendingmachine.csv");
		if (restock.getItems().isEmpty()) {
			System.out.println("*** Sorry no items found.");
			exit(1);
		}
	}

	private void callFeedMoney() {

		Scanner userInput = new Scanner(System.in);

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
		} catch (InputMismatchException e) {
			System.out.println("\n*** Please enter a valid amount.");
		}
	}

	private void processChange() {

		List<Integer> listChange = change.makeChange(RunningBalance.getCurrBalance());
		List<String> denominations = change.retrieveCurrencyDenominations();
		System.out.println("\nPlease Take Your Change Below >>> \nChange: ");
		for (int i = 0; i < listChange.size(); i++) {
			System.out.print(listChange.get(i));
			System.out.print(denominations.get(i) + "\n");
		}

		listChange.clear();
	}

	private void finalizeTran() {

		RunningBalance.giveChange();
		oldBalStr = currencyFormat.format(RunningBalance.getOldBalance());
		currBalStr = currencyFormat.format(RunningBalance.getCurrBalance());

		VendoLog.log("GIVE CHANGE", oldBalStr, currBalStr, formattedDate);
	}

	public void welcome(){

		System.out.println("*******************************");
		System.out.println("Welcome to the Vendo-Matic 800");
		System.out.println("*******************************");
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out, " ");
		Menu purchaseMenu = new Menu(System.in, System.out, "purchase");
		MakeChange change = new MakeChange();
		Menu mainMenu = new Menu(System.in, System.out, "mainMenu");
		VendingMachineCLI cli = new VendingMachineCLI(menu, purchaseMenu, change, mainMenu);
		cli.run();
	}
}
