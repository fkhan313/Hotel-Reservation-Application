package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource singleObject = new AdminResource();
    private AdminResource(){

    }
    public static AdminResource getInstance(){
        return singleObject;
    }

    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    //check if the addRoom method is correct
    public void addRoom(List<IRoom> rooms) {
        for (IRoom r:rooms)
        ReservationService.getInstance().addRoom(r);
    }

    public Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return CustomerService.getInstance().getAllCustomers();
    }

    public void displayAllReservations(){
        ReservationService.getInstance().printAllReservation();
    }



}
