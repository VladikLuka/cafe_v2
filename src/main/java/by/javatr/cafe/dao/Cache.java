package by.javatr.cafe.dao;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Cache {

    private static Cache INSTANCE;

    private final Map<Integer, ArrayList<Address>> mapAddress = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<Dish> dishList = new CopyOnWriteArrayList<>();
    private final Map<Integer, ArrayList<Order>> mapOrders = new ConcurrentHashMap<>();


    public static Cache getINSTANCE(){
        if (INSTANCE == null){
            return new Cache();
        }

        return INSTANCE;
    }

    public void setOrders(List<Order> orders) {
        for (Order order: orders) {
            if(mapOrders.containsKey(order.getUser_id())){
                mapOrders.get(order.getUser_id()).add(order);
            }else {
                mapOrders.put(order.getUser_id(), new ArrayList<>());
                mapOrders.get(order.getUser_id()).add(order);
            }
        }
    }

    public List<Order> getOrders(int id) {
        return mapOrders.get(id);
    }

    public Order addOrder(Order order){
        ArrayList<Order> orders = mapOrders.get(order.getUser_id());
        if(orders == null){
            orders = new ArrayList<Order>();
        }
        orders.add(order);
        return order;
    }

    public void setDishes(List<Dish> list){
        dishList.addAll(list);
    }

    public List<Dish> getDishes(){
        return dishList;
    }

    public void setAddresses(List<Address> list){

        for (Address address: list) {
            if(mapAddress.containsKey(address.getUser_id())){
                mapAddress.get(address.getUser_id()).add(address);
            }else {
                mapAddress.put(address.getUser_id(), new ArrayList<>());
                mapAddress.get(address.getUser_id()).add(address);
            }
        }
    }

    public List<Address> getAddress(int id){
        return mapAddress.get(id);
    }

    public Address createAddress(Address address){
        if(mapAddress.get(address.getUser_id()) == null){
            mapAddress.put(address.getUser_id(), new ArrayList<>());
            mapAddress.get(address.getUser_id()).add(address);
        }else {
            mapAddress.get(address.getUser_id()).add(address);
        }return address;
    }

//    public boolean deleteAddress(int address_id){
//
//        final Set<Integer> integers = mapAddress.keySet();
//
//        mapAddress.remove(address_id);
//
//
//        while(keys.hasMoreElements()){
//
//            final ArrayList<Address> addresses = mapAddress.get(keys.nextElement());
//
//            final boolean b = addresses.removeIf(next -> next.getId() == address_id);
//            if(b){
//                break;
//            }
//        }
//        return true;
//    }

    public boolean deleteAddress(Address address) {

        final ArrayList<Address> addresses = mapAddress.get(address.getUser_id());

        final boolean b = addresses.removeIf(next -> next.getId() == address.getId());

        return true;
    }

    private Cache(){}
}