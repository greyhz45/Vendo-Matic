package com.techelevator.view;

import com.techelevator.menu_process.RunningBalance;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private String menuType;

	public Menu(InputStream input, OutputStream output, String type) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
		this.menuType = type;
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.print(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			if (this.menuType.equals(" ") && i == options.length - 1) {
				break;
			}
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		if (this.menuType.equalsIgnoreCase("purchase"))
				out.printf("\nCurrent Money Provided: $%5.2f \n", RunningBalance.getCurrBalance());

			out.print(System.lineSeparator() + "Please choose an option >>> ");
			out.flush();
	}

}
