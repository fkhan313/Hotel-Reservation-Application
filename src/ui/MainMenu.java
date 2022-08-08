package ui;

import api.HotelResource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final String dateFormat = "MM/dd/yyyy";
    public static void main(String[] args) throws ParseException {

        displayMainMenu();

        Scanner sc = new Scanner(System.in);

        while (true) {

            int selection = sc.nextInt();

            switch (selection) {

                case 1:
                    findAndBook();
                    break;

                case 2:
                    Scanner sc2 = new Scanner(System.in);
                    System.out.println("Please enter your email address to see your reservations");
                    String customerEmail = sc2.nextLine();

                    try {
                        System.out.println(HotelResource.getInstance().getCustomersReservations(customerEmail));
                    } catch (Exception e) {
                        System.out.println("No reservations found");

                    }

                    break;

                case 3:

                    Scanner sc3 = new Scanner(System.in);

                    System.out.println("Please enter your email address");

                    String email = sc3.nextLine();

                    System.out.println("Enter your first name:");
                    String firstName = sc3.nextLine();

                    System.out.println("Enter your last name:");
                    String lastName = sc3.nextLine();
                    HotelResource.getInstance().createACustomer(email,firstName,lastName);
                    System.out.println("Account created successfully!");
                    System.out.println(HotelResource.getInstance().getCustomer(email));
                    displayMainMenu();


                    break;

                case 4:
                    //AdminMenu aMenu= new AdminMenu();
                    AdminMenu.adminMenu();

                    break;

                case 5:

                    System.out.println("Program Ended!! Thanks for using the Hotel Reservation Service");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please enter a valid option\n");
                    break;

            }
        }

        //sc.close();


    }

    private static Date validDate(final Scanner scanner) {
        try {
            return new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Date format invalid");
            findAndBook();
        }

        return null;
    }



    public static void findAndBook() {
        Scanner sc1 = new Scanner(System.in);

        if(HotelResource.getInstance().getAllRooms().isEmpty()) {
            System.out.println("No Rooms exist. Please create a room first");
            displayMainMenu();
        } else {
        System.out.println("Please enter a valid Checkin date in mm/dd/yyyy format. Ex:08/10/2022");
        Date checkIndate = validDate(sc1);
        System.out.println("Please enter a valid Checkout date in mm/dd/yyyy format. Ex:08/12/2022");
        Date checkOutdate=validDate(sc1);
        HotelResource.getInstance().findARoom(checkIndate,checkOutdate);

        if(HotelResource.getInstance().findARoom(checkIndate,checkOutdate).isEmpty()) {
            Date checkInPlus7=HotelResource.getInstance().recommendedDates(checkIndate);
            Date checkOutPlus7= HotelResource.getInstance().recommendedDates(checkOutdate);
            System.out.println("No rooms found for the selected dates. Here are a list of recommended rooms for checkin date:" +
                    checkInPlus7 +" and checkout date:" +checkOutPlus7);
            System.out.println(HotelResource.getInstance().recommendedRooms(checkInPlus7,checkOutPlus7));
            System.out.println("Please enter your room number");
            String roomNumber= sc1.nextLine();
            System.out.println("Please enter your email address");
            String email= sc1.nextLine();
            if(HotelResource.getInstance().isRoomBooked(checkInPlus7,checkOutPlus7).contains(HotelResource.getInstance().getRoom(roomNumber))){
                System.out.println("Sorry,this room is not available. Please go back to the main menu and try again!");
                displayMainMenu();
            } else {
                HotelResource.getInstance().bookARoom(email,HotelResource.getInstance().getRoom(roomNumber),checkInPlus7,checkOutPlus7);
                System.out.println("Room reserved successfully");
                displayMainMenu();

            }


        } else {
            System.out.println("Rooms found. Please select a room from the list below:");
            System.out.println(HotelResource.getInstance().findARoom(checkIndate,checkOutdate));
            System.out.println("Please enter your room number");
            String roomNumber= sc1.nextLine();
            System.out.println("Please enter your email address");
            String email= sc1.nextLine();
            if(HotelResource.getInstance().isRoomBooked(checkIndate,checkOutdate).contains(HotelResource.getInstance().getRoom(roomNumber))) {
                System.out.println("Sorry,this room is not available. Please go back to the main menu and try again!");
                displayMainMenu();
            } else {
                HotelResource.getInstance().bookARoom(email,HotelResource.getInstance().getRoom(roomNumber),checkIndate,checkOutdate);
                System.out.println("Room reserved successfully");
                displayMainMenu();

            }



        }
    } }
    public static void displayMainMenu() {

        System.out.println(
                "\nPlease select an option from below:"
                        + "\n\n1.Find and reserve a room"
                        + "\n2.See my reservations"
                        +"\n3.Create an account"
                        +"\n4.Admin"
                        +"\n5.Exit");


    }





}
