package org.example;

import org.example.model.Room;
import org.example.model.Guest;
import org.example.model.Invoice;
import org.example.model.Reservation;
import org.example.service.RoomService;
import org.example.service.GuestService;
import org.example.service.ReservationService;
import org.example.service.InvoiceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        RoomService roomService = new RoomService();
        GuestService guestService = new GuestService();
        ReservationService reservationService = new ReservationService();
        InvoiceService invoiceService = new InvoiceService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1: Read Room");
            System.out.println("2: Create Guest");
            System.out.println("3: Update Guest");
            System.out.println("4: Delete Guest");
            System.out.println("5: Create Reservation");
            System.out.println("6: Create Invoice");
            System.out.println("7: List All Rooms");
            System.out.println("8: List All Guests");
            System.out.println("9: List All Reservations");
            System.out.println("10: Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    readRoom(roomService, scanner);
                    break;
                case 2:
                    createGuest(guestService, scanner);
                    break;
                case 3:
                    updateGuest(guestService, scanner);
                    break;
                case 4:
                    deleteGuest(guestService, scanner);
                    break;
                case 5:
                    createReservation(reservationService, scanner);
                    break;
                case 6:
                    createInvoice(invoiceService, scanner);
                    break;
                case 7:
                    listAllRooms(roomService);
                    break;
                case 8:
                    listAllGuests(guestService);
                    break;
                case 9:
                    listAllReservations(reservationService);
                    break;
                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void readRoom(RoomService roomService, Scanner scanner) {
        System.out.println("Enter room ID:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Room room = roomService.getRoomById(id);
        System.out.println("Fetched Room: " + room);
    }

    private static void createGuest(GuestService guestService, Scanner scanner) {
        System.out.println("Enter guest name:");
        String name = scanner.nextLine();
        System.out.println("Enter guest email:");
        String email = scanner.nextLine();

        Guest guest = new Guest();
        guest.setName(name);
        guest.setEmail(email);

        guestService.saveGuest(guest);
        System.out.println("Guest created successfully: " + guest);
    }

    private static void updateGuest(GuestService guestService, Scanner scanner) {
        System.out.println("Enter guest ID:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Guest guest = guestService.getGuestById(id);
        if (guest != null) {
            System.out.println("Enter new guest email:");
            String email = scanner.nextLine();

            guest.setEmail(email);

            guestService.updateGuest(guest);
            System.out.println("Guest updated successfully: " + guest);
        } else {
            System.out.println("Guest not found.");
        }
    }

    private static void deleteGuest(GuestService guestService, Scanner scanner) {
        System.out.println("Enter guest ID:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        guestService.deleteGuest(id);
        System.out.println("Guest deleted successfully.");
    }

    private static void createReservation(ReservationService reservationService, Scanner scanner) {
        System.out.println("Enter room ID:");
        Long roomId = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter guest ID:");
        Long guestId = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter check-in date (yyyy-mm-dd):");
        LocalDate checkInDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter check-out date (yyyy-mm-dd):");
        LocalDate checkOutDate = LocalDate.parse(scanner.nextLine());

        Room room = new RoomService().getRoomById(roomId);
        Guest guest = new GuestService().getGuestById(guestId);
        if (room != null && guest != null) {
            Reservation reservation = new Reservation();
            reservation.setRoom(room);
            reservation.setGuest(guest);
            reservation.setCheckInDate(checkInDate);
            reservation.setCheckOutDate(checkOutDate);

            reservationService.saveReservation(reservation);
            System.out.println("Reservation created successfully: " + reservation);
        } else {
            System.out.println("Room or Guest not found.");
        }
    }

    private static void createInvoice(InvoiceService invoiceService, Scanner scanner) {
        System.out.println("Enter reservation ID:");
        Long reservationId = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter invoice date (yyyy-mm-dd):");
        LocalDate invoiceDate = LocalDate.parse(scanner.nextLine());

        Reservation reservation = new ReservationService().getReservationById(reservationId);
        if (reservation != null) {
            Invoice invoice = new Invoice();
            invoice.setReservation(reservation);
            invoice.setAmount(amount);
            invoice.setInvoiceDate(invoiceDate);

            invoiceService.saveInvoice(invoice);
            System.out.println("Invoice created successfully: " + invoice);
        } else {
            System.out.println("Reservation not found.");
        }
    }

    private static void listAllRooms(RoomService roomService) {
        List<Room> rooms = roomService.getAllRooms();
        System.out.println("Listing All Rooms:");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    private static void listAllGuests(GuestService guestService) {
        List<Guest> guests = guestService.getAllGuests();
        System.out.println("Listing All Guests:");
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }

    private static void listAllReservations(ReservationService reservationService) {
        List<Reservation> reservations = reservationService.getAllReservations();
        System.out.println("Listing All Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
