package stack;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieTicketBooking {

	static Scanner sc = new Scanner(System.in);

	static String[] movies = {
	    "Avengers",
	    "Interstellar",
	    "Inception",
	    "Spider-Man",
	    "Batman"
	};

	static String[] showTimes = {
	    "10:00 AM",
	    "01:30 PM",
	    "05:00 PM",
	    "08:30 PM"
	};

	static boolean[][] seats = new boolean[3][5];

	static double ticketPrice = 200;

	static ArrayList<Booking> bookings = new ArrayList<>();
	static int bookingCounter = 1001;

	static void displaySeats() {
	    System.out.println("\n========== SEAT LAYOUT ==========");
	    System.out.println("              SCREEN");
	    System.out.println("---------------------------------");
	    System.out.println("       1     2     3     4     5");

	    for (int i = 0; i < seats.length; i++) {
	        System.out.print("Row " + (i + 1) + "  ");

	        for (int j = 0; j < seats[i].length; j++) {
	            if (seats[i][j]) {
	                System.out.print("[X]   ");
	            } else {
	                System.out.print("[O]   ");
	            }
	        }

	        System.out.println();
	    }

	    System.out.println("---------------------------------");
	    System.out.println("O = Available");
	    System.out.println("X = Booked");
	}

	static void showMovies() {
	    System.out.println("\n========== MOVIES ==========");

	    for (int i = 0; i < movies.length; i++) {
	        System.out.println((i + 1) + ". " + movies[i]);
	    }

	    System.out.println("=============================");
	}

	static int selectMovie() {
	    showMovies();

	    System.out.print("\nSelect movie: ");
	    int movieChoice = sc.nextInt();

	    if (movieChoice < 1 || movieChoice > movies.length) {
	        System.out.println("\nInvalid movie selection.");
	        return -1;
	    }

	    return movieChoice - 1;
	}

	static LocalDate selectDate() {
	    while (true) {
	        System.out.print("\nEnter booking date (yyyy-mm-dd): ");
	        String dateInput = sc.next();

	        try {
	            LocalDate date = LocalDate.parse(dateInput);
	            LocalDate today = LocalDate.now();

	            if (date.isBefore(today)) {
	                System.out.println("You cannot book a past date.");
	                continue;
	            }

	            return date;

	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid date format.");
	            System.out.println("Please use yyyy-mm-dd.");
	        }
	    }
	}

	static int selectShowTime() {
	    System.out.println("\n========== SHOW TIMES ==========");

	    for (int i = 0; i < showTimes.length; i++) {
	        System.out.println((i + 1) + ". " + showTimes[i]);
	    }

	    System.out.println("================================");
	    System.out.print("\nSelect show time: ");

	    int choice = sc.nextInt();

	    if (choice < 1 || choice > showTimes.length) {
	        System.out.println("\nInvalid show time.");
	        return -1;
	    }

	    return choice - 1;
	}

	static double calculatePrice(LocalDate date) {
	    DayOfWeek day = date.getDayOfWeek();
	    double price = ticketPrice;

	    System.out.println("\n========== PRICE DETAILS ==========");
	    System.out.println("Booking Date : " + date);
	    System.out.println("Day          : " + day);

	    if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
	        price = ticketPrice + 50;
	        System.out.println("Weekend pricing applied.");
	    } else {
	        System.out.println("Weekday pricing applied.");
	    }

	    System.out.println("Price / Ticket : Rs." + price);
	    System.out.println("===================================");

	    return price;
	}

	static boolean selectSeats(int numberOfTickets, int[] selectedRows, int[] selectedSeats) {
	    displaySeats();

	    for (int i = 0; i < numberOfTickets; i++) {
	        boolean seatSelected = false;

	        while (!seatSelected) {
	            System.out.println("\nSelect seat " + (i + 1));

	            System.out.print("Enter row number (1-3): ");
	            int row = sc.nextInt();

	            System.out.print("Enter seat number (1-5): ");
	            int seat = sc.nextInt();

	            if (row < 1 || row > 3 || seat < 1 || seat > 5) {
	                System.out.println("\nInvalid row or seat.");
	                System.out.println("Please select again.");
	                continue;
	            }

	            row = row - 1;
	            seat = seat - 1;

	            if (seats[row][seat]) {
	                System.out.println("\nSorry! This seat is already booked.");
	                System.out.println("Please select another seat.");
	                continue;
	            }

	            boolean duplicate = false;

	            for (int j = 0; j < i; j++) {
	                if (selectedRows[j] == row && selectedSeats[j] == seat) {
	                    duplicate = true;
	                    break;
	                }
	            }

	            if (duplicate) {
	                System.out.println("\nYou have already selected this seat.");
	                System.out.println("Please select another seat.");
	                continue;
	            }

	            selectedRows[i] = row;
	            selectedSeats[i] = seat;
	            seatSelected = true;
	        }
	    }

	    return true;
	}

	static double applyCoupon(double totalPrice) {
	    System.out.print("\nDo you have a coupon? (yes/no): ");
	    String answer = sc.next();

	    double discount = 0;

	    if (answer.equalsIgnoreCase("yes")) {
	        System.out.print("Enter coupon code: ");
	        String coupon = sc.next();

	        if (coupon.equalsIgnoreCase("MOVIE10")) {
	            discount = totalPrice * 0.10;

	            System.out.println("\nCoupon applied successfully!");
	            System.out.println("10% discount applied.");
	        } else {
	            System.out.println("\nInvalid coupon code.");
	        }
	    }

	    return discount;
	}

	static void displaySummary(
	        String movie,
	        LocalDate date,
	        String showTime,
	        int numberOfTickets,
	        int[] selectedRows,
	        int[] selectedSeats,
	        double pricePerTicket,
	        double totalPrice,
	        double discount,
	        double finalPrice) {

	    System.out.println("\n========================================");
	    System.out.println("          BOOKING SUMMARY");
	    System.out.println("========================================");
	    System.out.println("Movie             : " + movie);
	    System.out.println("Date              : " + date);
	    System.out.println("Day               : " + date.getDayOfWeek());
	    System.out.println("Show Time         : " + showTime);
	    System.out.println("Number of Tickets : " + numberOfTickets);
	    System.out.println("Price / Ticket    : Rs." + pricePerTicket);

	    System.out.println("\nSelected Seats:");

	    for (int i = 0; i < numberOfTickets; i++) {
	        System.out.println("Ticket " + (i + 1) + " -> Row "
	                + (selectedRows[i] + 1) + ", Seat "
	                + (selectedSeats[i] + 1));
	    }

	    System.out.println("----------------------------------------");
	    System.out.println("Subtotal          : Rs." + totalPrice);
	    System.out.println("Discount          : Rs." + discount);
	    System.out.println("----------------------------------------");
	    System.out.println("TOTAL             : Rs." + finalPrice);
	    System.out.println("========================================");
	}

	static boolean makePayment(double amount) {
	    while (true) {
	        System.out.println("\n========== PAYMENT ==========");
	        System.out.println("Amount to Pay: Rs." + amount);

	        System.out.println("\nSelect Payment Mode:");
	        System.out.println("1. UPI");
	        System.out.println("2. Card");
	        System.out.println("3. Cash");
	        System.out.println("4. Cancel Payment");

	        System.out.print("\nEnter your choice: ");
	        int choice = sc.nextInt();

	        switch (choice) {

	            case 1:
	                System.out.print("\nEnter UPI ID: ");
	                String upi = sc.next();

	                System.out.print("Enter 4-digit UPI PIN: ");
	                int upiPin = sc.nextInt();

	                if (upiPin >= 1000 && upiPin <= 9999) {
	                    System.out.println("\nProcessing UPI payment...");
	                    System.out.println("UPI payment successful.");
	                    return true;
	                } else {
	                    System.out.println("\nInvalid UPI PIN.");
	                    System.out.println("Please try again.");
	                }
	                break;

	            case 2:
	                System.out.print("\nEnter last 4 digits of card: ");
	                int cardNumber = sc.nextInt();

	                System.out.print("Enter 4-digit Card PIN: ");
	                int cardPin = sc.nextInt();

	                if (cardNumber >= 1000 && cardNumber <= 9999
	                        && cardPin >= 1000 && cardPin <= 9999) {
	                    System.out.println("\nProcessing card payment...");
	                    System.out.println("Card payment successful.");
	                    return true;
	                } else {
	                    System.out.println("\nInvalid card details or PIN.");
	                    System.out.println("Please try again.");
	                }
	                break;

	            case 3:
	                System.out.println("\nCash payment selected.");
	                System.out.println("Payment received successfully.");
	                return true;

	            case 4:
	                System.out.println("\nPayment cancelled.");
	                return false;

	            default:
	                System.out.println("\nInvalid payment option.");
	        }
	    }
	}

	static void bookTicket() {
	    System.out.println("\n========================================");
	    System.out.println("            BOOK MOVIE TICKET");
	    System.out.println("========================================");

	    int movieIndex = selectMovie();

	    if (movieIndex == -1) {
	        return;
	    }

	    LocalDate date = selectDate();

	    int showIndex = selectShowTime();

	    if (showIndex == -1) {
	        return;
	    }

	    System.out.print("\nEnter number of tickets: ");
	    int numberOfTickets = sc.nextInt();

	    if (numberOfTickets < 1 || numberOfTickets > 15) {
	        System.out.println("\nYou can book between 1 and 15 tickets.");
	        return;
	    }

	    int[] selectedRows = new int[numberOfTickets];
	    int[] selectedSeats = new int[numberOfTickets];

	    selectSeats(numberOfTickets, selectedRows, selectedSeats);

	    double pricePerTicket = calculatePrice(date);
	    double totalPrice = pricePerTicket * numberOfTickets;

	    double discount = applyCoupon(totalPrice);
	    double finalPrice = totalPrice - discount;

	    displaySummary(
	            movies[movieIndex],
	            date,
	            showTimes[showIndex],
	            numberOfTickets,
	            selectedRows,
	            selectedSeats,
	            pricePerTicket,
	            totalPrice,
	            discount,
	            finalPrice
	    );

	    System.out.print("\nConfirm booking? (yes/no): ");
	    String confirm = sc.next();

	    if (!confirm.equalsIgnoreCase("yes")) {
	        System.out.println("\nBooking cancelled.");
	        return;
	    }

	    boolean paymentSuccessful = makePayment(finalPrice);

	    if (!paymentSuccessful) {
	        System.out.println("\nBooking was not completed.");
	        System.out.println("Your selected seats are still available.");
	        return;
	    }

	    for (int i = 0; i < numberOfTickets; i++) {
	        seats[selectedRows[i]][selectedSeats[i]] = true;
	    }

	    String bookingId = "MTB" + bookingCounter++;

	    Booking booking = new Booking(
	            bookingId,
	            movies[movieIndex],
	            date,
	            showTimes[showIndex],
	            numberOfTickets,
	            selectedRows,
	            selectedSeats,
	            finalPrice
	    );

	    bookings.add(booking);

	    System.out.println("\n========================================");
	    System.out.println("          BOOKING CONFIRMED");
	    System.out.println("========================================");
	    System.out.println("Booking ID : " + bookingId);
	    System.out.println("Movie      : " + movies[movieIndex]);
	    System.out.println("Date       : " + date);
	    System.out.println("Show Time  : " + showTimes[showIndex]);
	    System.out.println("Tickets    : " + numberOfTickets);
	    System.out.println("Amount Paid: Rs." + finalPrice);
	    System.out.println("----------------------------------------");
	    System.out.println("Booked Seats:");

	    for (int i = 0; i < numberOfTickets; i++) {
	        System.out.println("Row " + (selectedRows[i] + 1)
	                + " - Seat " + (selectedSeats[i] + 1));
	    }

	    System.out.println("========================================");
	    System.out.println("Thank you for booking with us!");
	}

	static void showBookings() {
	    if (bookings.isEmpty()) {
	        System.out.println("\nYou have no bookings.");
	        return;
	    }

	    System.out.println("\n========================================");
	    System.out.println("             MY BOOKINGS");
	    System.out.println("========================================");

	    for (Booking booking : bookings) {
	        booking.displayBooking();
	        System.out.println("----------------------------------------");
	    }
	}

	static void cancelBooking() {
	    if (bookings.isEmpty()) {
	        System.out.println("\nThere are no bookings to cancel.");
	        return;
	    }

	    showBookings();

	    System.out.print("\nEnter Booking ID to cancel: ");
	    String bookingId = sc.next();

	    Booking selectedBooking = null;

	    for (Booking booking : bookings) {
	        if (booking.bookingId.equalsIgnoreCase(bookingId)) {
	            selectedBooking = booking;
	            break;
	        }
	    }

	    if (selectedBooking == null) {
	        System.out.println("\nBooking ID not found.");
	        return;
	    }

	    System.out.println("\nBooking found!");
	    selectedBooking.displayBooking();

	    System.out.print("\nAre you sure you want to cancel? (yes/no): ");
	    String confirm = sc.next();

	    if (!confirm.equalsIgnoreCase("yes")) {
	        System.out.println("\nCancellation stopped.");
	        return;
	    }

	    for (int i = 0; i < selectedBooking.numberOfTickets; i++) {
	        int row = selectedBooking.selectedRows[i];
	        int seat = selectedBooking.selectedSeats[i];

	        seats[row][seat] = false;
	    }

	    bookings.remove(selectedBooking);

	    System.out.println("\nTicket cancelled successfully.");
	    System.out.println("Your seats are now available again.");
	}

	static void showRating() {
	    System.out.println("\n========== MOVIE RATINGS ==========");
	    System.out.println("Avengers      : 4.5 / 5");
	    System.out.println("Interstellar  : 4.8 / 5");
	    System.out.println("Inception     : 4.7 / 5");
	    System.out.println("Spider-Man    : 4.4 / 5");
	    System.out.println("Batman        : 4.3 / 5");
	    System.out.println("===================================");
	}

	static class Booking {
	    String bookingId;
	    String movie;
	    LocalDate date;
	    String showTime;
	    int numberOfTickets;
	    int[] selectedRows;
	    int[] selectedSeats;
	    double amount;

	    Booking(
	            String bookingId,
	            String movie,
	            LocalDate date,
	            String showTime,
	            int numberOfTickets,
	            int[] selectedRows,
	            int[] selectedSeats,
	            double amount) {

	        this.bookingId = bookingId;
	        this.movie = movie;
	        this.date = date;
	        this.showTime = showTime;
	        this.numberOfTickets = numberOfTickets;
	        this.selectedRows = selectedRows;
	        this.selectedSeats = selectedSeats;
	        this.amount = amount;
	    }

	    void displayBooking() {
	        System.out.println("Booking ID : " + bookingId);
	        System.out.println("Movie      : " + movie);
	        System.out.println("Date       : " + date);
	        System.out.println("Show Time  : " + showTime);
	        System.out.println("Tickets    : " + numberOfTickets);
	        System.out.println("Amount     : Rs." + amount);
	        System.out.println("Seats      :");

	        for (int i = 0; i < numberOfTickets; i++) {
	            System.out.println("  Row " + (selectedRows[i] + 1)
	                    + " - Seat " + (selectedSeats[i] + 1));
	        }
	    }
	}

	public static void main(String[] args) {
	    int choice;

	    do {
	        System.out.println("\n========================================");
	        System.out.println("        MOVIE TICKET BOOKING");
	        System.out.println("========================================");
	        System.out.println("1. Browse Movies");
	        System.out.println("2. Show Available Seats");
	        System.out.println("3. Book Tickets");
	        System.out.println("4. My Bookings");
	        System.out.println("5. Cancel Booking");
	        System.out.println("6. Movie Ratings");
	        System.out.println("7. Exit");
	        System.out.println("========================================");

	        System.out.print("\nEnter your choice: ");
	        choice = sc.nextInt();

	        switch (choice) {
	            case 1:
	                showMovies();
	                break;

	            case 2:
	                displaySeats();
	                break;

	            case 3:
	                bookTicket();
	                break;

	            case 4:
	                showBookings();
	                break;

	            case 5:
	                cancelBooking();
	                break;

	            case 6:
	                showRating();
	                break;

	            case 7:
	                System.out.println("\nThank you for using the Movie Ticket Booking System!");
	                break;

	            default:
	                System.out.println("\nInvalid choice.");
	                System.out.println("Please try again.");
	        }

	    } while (choice != 7);

	    sc.close();
	}
}
