package stack;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PersonalExpenseTracker {

	static Scanner sc = new Scanner(System.in);
	static ArrayList<Transaction> transactions = new ArrayList<>();
	static int transactionCounter = 1001;
	static double monthlyBudget = 0;
	enum Type {
	    INCOME, EXPENSE
	}

	enum Category {
	    FOOD, TRAVEL, SHOPPING, BILLS,
	    ENTERTAINMENT, HEALTH, EDUCATION,
	    SALARY, OTHER
	}

	static class Transaction {
	    int id;
	    String description;
	    double amount;
	    Type type;
	    Category category;
	    LocalDate date;

	    Transaction(int id, String description, double amount,
	                Type type, Category category, LocalDate date) {
	        this.id = id;
	        this.description = description;
	        this.amount = amount;
	        this.type = type;
	        this.category = category;
	        this.date = date;
	    }

	    void display() {
	        System.out.println("ID: " + id);
	        System.out.println("Description: " + description);
	        System.out.println("Amount: Rs." + amount);
	        System.out.println("Type: " + type);
	        System.out.println("Category: " + category);
	        System.out.println("Date: " + date);
	        System.out.println("----------------------------");
	    }
	}

	static void showMenu() {
	    System.out.println("\nPERSONAL EXPENSE TRACKER");
	    System.out.println("1. Add Income");
	    System.out.println("2. Add Expense");
	    System.out.println("3. View All Transactions");
	    System.out.println("4. View Balance");
	    System.out.println("5. Expense Summary");
	    System.out.println("6. Category-wise Spending");
	    System.out.println("7. Search Transaction");
	    System.out.println("8. Filter by Category");
	    System.out.println("9. Set Monthly Budget");
	    System.out.println("10. Monthly Report");
	    System.out.println("11. Highest Expense");
	    System.out.println("12. Sort Transactions");
	    System.out.println("13. Delete Transaction");
	    System.out.println("14. Financial Summary");
	    System.out.println("15. Exit");
	    System.out.print("Enter your choice: ");
	}

	static void addIncome() {
	    sc.nextLine();

	    System.out.print("Enter income description: ");
	    String description = sc.nextLine();

	    System.out.print("Enter income amount: ");
	    double amount = sc.nextDouble();

	    if (amount <= 0) {
	        System.out.println("Amount must be greater than zero.");
	        return;
	    }

	    Transaction transaction = new Transaction(
	            transactionCounter++,
	            description,
	            amount,
	            Type.INCOME,
	            Category.SALARY,
	            LocalDate.now()
	    );

	    transactions.add(transaction);

	    System.out.println("Income added successfully.");
	}

	static void addExpense() {
	    sc.nextLine();

	    System.out.print("Enter expense description: ");
	    String description = sc.nextLine();

	    System.out.print("Enter expense amount: ");
	    double amount = sc.nextDouble();

	    if (amount <= 0) {
	        System.out.println("Amount must be greater than zero.");
	        return;
	    }

	    Category category = selectCategory();
	    LocalDate date = selectDate();

	    Transaction transaction = new Transaction(
	            transactionCounter++,
	            description,
	            amount,
	            Type.EXPENSE,
	            category,
	            date
	    );

	    transactions.add(transaction);

	    System.out.println("Expense added successfully.");

	    checkBudget();
	}

	static Category selectCategory() {
	    System.out.println("\nSelect category");
	    System.out.println("1. Food");
	    System.out.println("2. Travel");
	    System.out.println("3. Shopping");
	    System.out.println("4. Bills");
	    System.out.println("5. Entertainment");
	    System.out.println("6. Health");
	    System.out.println("7. Education");
	    System.out.println("8. Other");
	    System.out.print("Enter choice: ");

	    int choice = sc.nextInt();

	    switch (choice) {
	        case 1:
	            return Category.FOOD;
	        case 2:
	            return Category.TRAVEL;
	        case 3:
	            return Category.SHOPPING;
	        case 4:
	            return Category.BILLS;
	        case 5:
	            return Category.ENTERTAINMENT;
	        case 6:
	            return Category.HEALTH;
	        case 7:
	            return Category.EDUCATION;
	        default:
	            return Category.OTHER;
	    }
	}

	static LocalDate selectDate() {
	    System.out.println("\nSelect date");
	    System.out.println("1. Today");
	    System.out.println("2. Enter date");
	    System.out.print("Enter choice: ");

	    int choice = sc.nextInt();

	    if (choice == 1) {
	        return LocalDate.now();
	    }

	    sc.nextLine();

	    while (true) {
	        System.out.print("Enter date (yyyy-mm-dd): ");
	        String dateInput = sc.nextLine();

	        try {
	            return LocalDate.parse(dateInput);
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid date. Try again.");
	        }
	    }
	}

	static void viewTransactions() {
	    if (transactions.isEmpty()) {
	        System.out.println("No transactions available.");
	        return;
	    }

	    System.out.println("\nALL TRANSACTIONS");

	    for (Transaction transaction : transactions) {
	        transaction.display();
	    }
	}

	static double getTotalIncome() {
	    return transactions.stream()
	            .filter(t -> t.type == Type.INCOME)
	            .mapToDouble(t -> t.amount)
	            .sum();
	}

	static double getTotalExpense() {
	    return transactions.stream()
	            .filter(t -> t.type == Type.EXPENSE)
	            .mapToDouble(t -> t.amount)
	            .sum();
	}

	static void viewBalance() {
	    double income = getTotalIncome();
	    double expense = getTotalExpense();
	    double balance = income - expense;

	    System.out.println("\nBALANCE");
	    System.out.println("Total Income: Rs." + income);
	    System.out.println("Total Expense: Rs." + expense);
	    System.out.println("Balance: Rs." + balance);
	}

	static void expenseSummary() {
	    double income = getTotalIncome();
	    double expense = getTotalExpense();

	    System.out.println("\nEXPENSE SUMMARY");
	    System.out.println("Total Income: Rs." + income);
	    System.out.println("Total Expense: Rs." + expense);

	    if (income > 0) {
	        double percentage = (expense / income) * 100;
	        System.out.println("Expense Percentage: " + percentage + "%");
	    }
	}

	static void categoryWiseSpending() {
	    System.out.println("\nCATEGORY-WISE SPENDING");

	    for (Category category : Category.values()) {
	        double total = transactions.stream()
	                .filter(t -> t.type == Type.EXPENSE)
	                .filter(t -> t.category == category)
	                .mapToDouble(t -> t.amount)
	                .sum();

	        if (total > 0) {
	            System.out.println(category + ": Rs." + total);
	        }
	    }
	}

	static void searchTransaction() {
	    sc.nextLine();

	    System.out.print("Enter description to search: ");
	    String keyword = sc.nextLine().toLowerCase();

	    ArrayList<Transaction> result = transactions.stream()
	            .filter(t -> t.description.toLowerCase().contains(keyword))
	            .collect(Collectors.toCollection(ArrayList::new));

	    if (result.isEmpty()) {
	        System.out.println("No transaction found.");
	        return;
	    }

	    for (Transaction transaction : result) {
	        transaction.display();
	    }
	}

	static void filterByCategory() {
	    Category category = selectCategory();

	    ArrayList<Transaction> result = transactions.stream()
	            .filter(t -> t.category == category)
	            .collect(Collectors.toCollection(ArrayList::new));

	    if (result.isEmpty()) {
	        System.out.println("No transactions found in this category.");
	        return;
	    }

	    for (Transaction transaction : result) {
	        transaction.display();
	    }
	}

	static void setMonthlyBudget() {
	    System.out.print("Enter monthly budget: ");
	    monthlyBudget = sc.nextDouble();

	    if (monthlyBudget <= 0) {
	        System.out.println("Budget must be greater than zero.");
	        monthlyBudget = 0;
	        return;
	    }

	    System.out.println("Monthly budget set successfully.");
	}

	static void checkBudget() {
	    if (monthlyBudget == 0) {
	        return;
	    }

	    LocalDate today = LocalDate.now();

	    double monthlyExpense = transactions.stream()
	            .filter(t -> t.type == Type.EXPENSE)
	            .filter(t -> t.date.getMonth() == today.getMonth())
	            .filter(t -> t.date.getYear() == today.getYear())
	            .mapToDouble(t -> t.amount)
	            .sum();

	    if (monthlyExpense > monthlyBudget) {
	        System.out.println("Warning: You have crossed your monthly budget.");
	    } else if (monthlyExpense >= monthlyBudget * 0.8) {
	        System.out.println("Warning: You have used more than 80% of your budget.");
	    }
	}

	static void monthlyReport() {
	    LocalDate today = LocalDate.now();

	    double income = transactions.stream()
	            .filter(t -> t.type == Type.INCOME)
	            .filter(t -> t.date.getMonth() == today.getMonth())
	            .filter(t -> t.date.getYear() == today.getYear())
	            .mapToDouble(t -> t.amount)
	            .sum();

	    double expense = transactions.stream()
	            .filter(t -> t.type == Type.EXPENSE)
	            .filter(t -> t.date.getMonth() == today.getMonth())
	            .filter(t -> t.date.getYear() == today.getYear())
	            .mapToDouble(t -> t.amount)
	            .sum();

	    System.out.println("\nMONTHLY REPORT");
	    System.out.println("Month: " + today.getMonth());
	    System.out.println("Income: Rs." + income);
	    System.out.println("Expense: Rs." + expense);
	    System.out.println("Savings: Rs." + (income - expense));

	    if (monthlyBudget > 0) {
	        System.out.println("Budget: Rs." + monthlyBudget);
	        System.out.println("Remaining Budget: Rs." + (monthlyBudget - expense));
	    }
	}

	static void highestExpense() {
	    Transaction highest = transactions.stream()
	            .filter(t -> t.type == Type.EXPENSE)
	            .max(Comparator.comparingDouble(t -> t.amount))
	            .orElse(null);

	    if (highest == null) {
	        System.out.println("No expenses available.");
	        return;
	    }

	    System.out.println("\nHIGHEST EXPENSE");
	    highest.display();
	}

	static void sortTransactions() {
	    System.out.println("\nSort by");
	    System.out.println("1. Amount");
	    System.out.println("2. Date");
	    System.out.print("Enter choice: ");

	    int choice = sc.nextInt();

	    if (choice == 1) {
	        transactions.sort(Comparator.comparingDouble(t -> t.amount));
	    } else if (choice == 2) {
	        transactions.sort(Comparator.comparing(t -> t.date));
	    } else {
	        System.out.println("Invalid choice.");
	        return;
	    }

	    System.out.println("Transactions sorted successfully.");

	    viewTransactions();
	}

	static void deleteTransaction() {
	    System.out.print("Enter transaction ID to delete: ");
	    int id = sc.nextInt();

	    Transaction found = null;

	    for (Transaction transaction : transactions) {
	        if (transaction.id == id) {
	            found = transaction;
	            break;
	        }
	    }

	    if (found == null) {
	        System.out.println("Transaction not found.");
	        return;
	    }

	    System.out.println("Transaction found:");
	    found.display();

	    System.out.print("Are you sure you want to delete it? (yes/no): ");
	    String choice = sc.next();

	    if (choice.equalsIgnoreCase("yes")) {
	        transactions.remove(found);
	        System.out.println("Transaction deleted successfully.");
	    } else {
	        System.out.println("Transaction was not deleted.");
	    }
	}

	static void financialSummary() {
	    long incomeCount = transactions.stream()
	            .filter(t -> t.type == Type.INCOME)
	            .count();

	    long expenseCount = transactions.stream()
	            .filter(t -> t.type == Type.EXPENSE)
	            .count();

	    double income = getTotalIncome();
	    double expense = getTotalExpense();
	    double balance = income - expense;

	    System.out.println("\nFINANCIAL SUMMARY");
	    System.out.println("Number of Income Transactions: " + incomeCount);
	    System.out.println("Number of Expense Transactions: " + expenseCount);
	    System.out.println("Total Income: Rs." + income);
	    System.out.println("Total Expense: Rs." + expense);
	    System.out.println("Balance: Rs." + balance);

	    if (income > 0) {
	        double savingsPercentage = (balance / income) * 100;
	        System.out.println("Savings Percentage: " + savingsPercentage + "%");
	    }
	}

	public static void main(String[] args) {

	    while (true) {
	        showMenu();

	        int choice = sc.nextInt();

	        switch (choice) {
	            case 1:
	                addIncome();
	                break;

	            case 2:
	                addExpense();
	                break;

	            case 3:
	                viewTransactions();
	                break;

	            case 4:
	                viewBalance();
	                break;

	            case 5:
	                expenseSummary();
	                break;

	            case 6:
	                categoryWiseSpending();
	                break;

	            case 7:
	                searchTransaction();
	                break;

	            case 8:
	                filterByCategory();
	                break;

	            case 9:
	                setMonthlyBudget();
	                break;

	            case 10:
	                monthlyReport();
	                break;

	            case 11:
	                highestExpense();
	                break;

	            case 12:
	                sortTransactions();
	                break;

	            case 13:
	                deleteTransaction();
	                break;

	            case 14:
	                financialSummary();
	                break;

	            case 15:
	                System.out.println("Thank you for using Personal Expense Tracker.");
	                sc.close();
	                return;

	            default:
	                System.out.println("Invalid choice. Try again.");
	        }
	    }
	}
}