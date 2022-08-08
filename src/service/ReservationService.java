package service;

import model.*;

import java.util.*;

public class ReservationService {


    private static ReservationService singleObject= new ReservationService();
    private static final int daysToAdd = 7;
    private Map<String, IRoom> rooms = new HashMap<>();
     private Map<String, Collection<Reservation>> reservations = new HashMap<>();


    private ReservationService(){

    }

    public static ReservationService getInstance(){
        return singleObject;
    }


    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(),room);

    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

            Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);
            Collection<Reservation> cReservations = getCustomersReservation(customer);
            if (cReservations == null)  {
                cReservations = new ArrayList<>();
            }
            cReservations.add(reservation);
            reservations.put(customer.getEmail(), cReservations);
            return reservation;


    }


    public Collection<IRoom> findRooms( Date checkInDate,  Date checkOutDate) {

            Collection<Reservation> allReservations = getAllReservations();
            Collection<IRoom>roomsAlreadyBooked=isRoomBooked(checkInDate,checkOutDate);
            Collection<IRoom>availableRooms =getAllRooms();
            availableRooms.removeAll(roomsAlreadyBooked);
            return availableRooms;

    }

    public Collection<IRoom> findRecommendedRooms( Date checkInDate, Date checkOutDate) {
        return findRooms(recommendedDates(checkInDate), recommendedDates(checkOutDate));
    }

    public Date recommendedDates(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysToAdd);

        return calendar.getTime();
    }

    public Collection<IRoom>isRoomBooked(Date checkInDate, Date checkOutDate) {

        Collection<Reservation> allReservations = getAllReservations();
        Collection<IRoom> bookedRooms = new ArrayList<>();
        Collection<IRoom>availableRooms =getAllRooms();

        for (Reservation reservation : allReservations) {
            if (checkReservationDates(reservation, checkInDate, checkOutDate)) {
                bookedRooms.add(reservation.getRoom());
            }
        }
        return bookedRooms;

    }

    //Example of Default access modifier
      boolean checkReservationDates( Reservation reservation,  Date checkInDate, Date checkOutDate){

        return checkInDate.before(reservation.getCheckOutDate())
                && checkOutDate.after(reservation.getCheckInDate());
    }



    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation(){

      final Collection<Reservation> reservations = getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }


    }
    //private access modifier

    private Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new ArrayList<>();

        for(Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }

        return allReservations;
    }
    public Collection<IRoom> getAllRooms() {
        Collection<IRoom> allRooms = new ArrayList<>();

        for (IRoom iRooms : rooms.values()) {

            allRooms.add(iRooms);
        }
        return allRooms;

    }





}
