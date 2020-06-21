package by.javatr.cafe.util;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public final class Cache {

    private final Map<Integer, ArrayList<Address>> mapAddress = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<Dish> dishList = new CopyOnWriteArrayList<>();
    private final Map<Integer, ArrayList<Order>> mapOrders = new ConcurrentHashMap<>();
    private final Map<Integer, User> mapUser = new ConcurrentHashMap<>();

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
            ArrayList<Order> list = new ArrayList<>();
            mapOrders.put(order.getUser_id(), list);
            list.add(order);
            return order;
        }
        orders.add(order);
        return order;
    }

    public Order updateOrder(Order order){
        ArrayList<Order> orders = mapOrders.get(order.getUser_id());
        orders.remove(order);
        orders.add(order);
        return order;
    }

    public void deleteOrder(Order order){
        ArrayList<Order> orders = mapOrders.get(order.getUser_id());
        orders.remove(order);
    }

    public void setDishes(List<Dish> list){
        dishList.addAll(list);
    }

    public List<Dish> getDishes(){
        return dishList;
    }

    public Dish getDish(int id){

        for (Dish dish:dishList) {
            if(dish.getId() == id){
                return dish;
            }
        }

        return dishList.get(id);
    }

    public Dish updateDish(Dish dish){

        for (Dish dish1:dishList) {
            if (dish.getId() == dish1.getId()){
                dishList.remove(dish1);
                dishList.add(dish1);
                break;
            }
        }
        return dish;
    }

    public Dish addDish(Dish dish){
        dishList.add(dish);
        return dish;
    }

    public void deleteDish(Dish dish){
        dishList.removeIf(dish1 -> dish.getId() == dish1.getId());
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

    public Address addAddress(Address address){
        if(mapAddress.get(address.getUser_id()) == null){
            mapAddress.put(address.getUser_id(), new ArrayList<>());
            mapAddress.get(address.getUser_id()).add(address);
        }else {
            mapAddress.get(address.getUser_id()).add(address);
        }return address;
    }

    public void deleteAddress(Address address) {

        final ArrayList<Address> addresses = mapAddress.get(address.getUser_id());

        addresses.removeIf(next -> next.getId() == address.getId());
    }

    public Address updateAddress(Address address){
        final ArrayList<Address> addresses = mapAddress.get(address.getUser_id());

        for (Address address1 :addresses) {
            if(address.getId() == address1.getId()){
                addresses.remove(address1);
                addresses.add(address);
            }
        }
        return address;
    }

    public User getUser(int id){
        return mapUser.get(id);
    }

    public void setUsers(List<User> users){
        for (User user:users) {
            mapUser.put(user.getId(), user);
        }
    }

    public void addUser(User user){
            mapUser.put(user.getId(), user);
    }

    public void deleteUser(User user){
        mapUser.remove(user.getId());
    }

    public User updateUser(User user){
        mapUser.remove(user.getId());
        mapUser.put(user.getId(), user);
        return user;
    }

    public Map<Integer, ArrayList<Address>> getMapAddress() {
        return mapAddress;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public Map<Integer, ArrayList<Order>> getMapOrders() {
        return mapOrders;
    }

    public Map<Integer, User> getMapUser() {
        return mapUser;
    }

    public void injectAddressToUser(){
        final Set<Integer> keySet = mapUser.keySet();
        for (int i:keySet)  {
            User user = mapUser.get(i);
            user.setAddress(mapAddress.get(i));
            System.out.println(user);
        }
    }

    private Cache(){}
}