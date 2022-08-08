package ui;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.ReservationService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    MainMenu mMenu= new MainMenu();

    public static void adminMenu() throws ParseException {

        displayAdminMenu();

        Scanner sc = new Scanner(System.in);

        boolean session = true;

        while (session) {

            int selection = sc.nextInt();

            switch (selection) {

                case 1:

                    if (AdminResource.getInstance().getAllCustomers().isEmpty()) {
                        System.out.println("No Customers found");
                    } else {
                        System.out.println(AdminResource.getInstance().getAllCustomers());
                    }
                    displayAdminMenu();


                    break;

                case 2:

                    if(AdminResource.getInstance().getAllRooms().isEmpty()) {
                        System.out.println("No rooms found");
                    } else {
                        System.out.println(AdminResource.getInstance().getAllRooms());
                    }
                    displayAdminMenu();
                    break;

                case 3:

                    AdminResource.getInstance().displayAllReservations();
                    displayAdminMenu();

                    break;

                case 4:
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("Enter a room number:");
                    String roomNumber = sc1.nextLine();
                    System.out.println("Enter the price");
                    double roomPrice = sc1.nextDouble();
                    System.out.println("Please enter a room option:SINGLE or DOUBLE");

                    RoomType inputRoomType = null;

                    boolean invalidInput;
                    do {
                        String input = sc1.next();
                        switch (input) {
                            case "1" -> {
                                inputRoomType = RoomType.SINGLE;
                                invalidInput = false;

                            }
                            case "2" -> {
                                inputRoomType = RoomType.DOUBLE;
                                invalidInput = false;

                            }
                            default -> {
                                System.out.println("Enter 1 for a single bed and 2 for a double bed.");
                                invalidInput = true;

                            }
                        }
                    } while (invalidInput);

                    ReservationService.getInstance().addRoom(new Room(roomNumber, roomPrice, inputRoomType));
                    List<IRoom> rooms = new ArrayList<>();
                    AdminResource.getInstance().addRoom(rooms);
                    System.out.println("Room created successfully.");
                    System.out.println(AdminResource.getInstance().getAllRooms());
                    displayAdminMenu();

                    break;

                case 5:
                    MainMenu mMenu= new MainMenu();
                    String[] args = {};
                    mMenu.main(args);


                    break;

                default:
                    System.out.println("Please enter a valid option\n");
                    break;

            }
        }

        //sc.close();

         }

         public static void displayAdminMenu() {

            System.out.println(
                    "\nPlease select an option from below:"
                            + "\n\n1.See all Customers"
                            + "\n2.See all Rooms"
                            + "\n3.See all Reservations"
                            + "\n4.Add a room"
                            + "\n5.Back to Main Menu");


        }

    }
