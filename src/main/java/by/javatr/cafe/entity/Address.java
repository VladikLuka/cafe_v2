package by.javatr.cafe.entity;

import by.javatr.cafe.dao.annotation.*;

import java.io.Serializable;
import java.util.Objects;

@Table(table="address")
public class Address extends Entity<Address> implements Serializable {

    private static final long serialVersionUID = 477027772878727562L;

    @Id(name = "address_id")
    private int id;
    @Field(name="address_city")
    private String city;
    @Field(name="address_street")
    private String street;
    @Field(name="address_house")
    private String house;
    @Field(name="address_flat")
    private String flat;
    @Join(fieldColumn = "address_user_id")
    @ManyToOne(joinName = "user_id", field = "id",type = User.class)
    private int userId;
    @Field(name = "address_isAvailable")
    private boolean available;


    public Address(int id, String city, String street, String house, String flat, int userId) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.userId = userId;
    }

    public Address(String city, String street, String house, String flat) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public Address(String city, String street, String house, String flat, int userId, boolean available) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.userId = userId;
        this.available = available;
    }


    public Address(){}

    public Address(int id) {
        this.id = id;
    }

    public Address(int i, String s, int i1) {
        this.id = i;
        this.city = s;
        this.userId = i1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                userId == address.userId &&
                available == address.available &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(house, address.house) &&
                Objects.equals(flat, address.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, house, flat, userId, available);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", user_id=" + userId +
                ", isAvailable=" + available +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}


