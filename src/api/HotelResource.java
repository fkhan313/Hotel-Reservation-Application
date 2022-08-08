package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource singleObject= new HotelResource();

    private HotelResource(){

    }

    public static HotelResource getInstance() {
        return singleObject;
    }

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }

    public void createACustomer(String email,String firstName,String lastName) {
        CustomerService.getInstance().addCustomer(email,firstName,lastName);

    }

    public IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return ReservationService.getInstance().reserveARoom(CustomerService.getInstance().getCustomer(customerEmail),room,checkInDate,checkOutDate );
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return ReservationService.getInstance().getCustomersReservation(CustomerService.getInstance().getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.getInstance().findRooms(checkIn,checkOut);
    }
    public Collection<IRoom> recommendedRooms (Date checkIn, Date checkOut) {
        return ReservationService.getInstance().findRecommendedRooms(checkIn,checkOut);
    }
    public Date recommendedDates (Date date) {
        return ReservationService.getInstance().recommendedDates(date);
    }
    public Collection<IRoom>isRoomBooked(Date checkInDate, Date checkOutDate) {
        return ReservationService.getInstance().isRoomBooked(checkInDate,checkOutDate);
    }

    public Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms();

    }



}
