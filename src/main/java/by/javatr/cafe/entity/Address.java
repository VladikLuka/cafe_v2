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
    private int user_id;


    public Address(int id, String city, String street, String house, String flat, int user_id) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.user_id = user_id;
    }

    public Address(String city, String street, String house, String flat) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public Address(String city, String street, String house, String flat, int user_id) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.user_id = user_id;
    }
    public Address(){}

    public Address(int id) {
        this.id = id;
    }

    public Address(int i, String s, int i1) {
        this.id = i;
        this.city = s;
        this.user_id = i1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                user_id == address.user_id &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(house, address.house) &&
                Objects.equals(flat, address.flat);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, house, flat, user_id);
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}


//package by.javatr.cafe.entity;
//
//import by.javatr.cafe.dao.annotation.Field;
//import by.javatr.cafe.dao.annotation.Id;
//import by.javatr.cafe.dao.annotation.Ignore;
//import by.javatr.cafe.dao.annotation.Table;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Table(table="address")
//public class Address extends Entity<Address> implements Serializable {
//
//    private static final long serialVersionUID = 477027772878727562L;
//
//    @Ignore(name = "address_id")
//    @Id(name = "address_id")
//    private int id;
//    @Field(name="city")
//    private String city;
//    @Field(name="street")
//    private String street;
//    @Field(name="house")
//    private String house;
//    @Field(name="flat")
//    private String flat;
//    @Field(name="user_id")
//    private int user_id;
//
//
//
//
//    public static class Builder{
//        @Ignore(name = "address_id")
//        @Id(name = "address_id")
//        private int id;
//        @Field(name="city")
//        private String city;
//        @Field(name="street")
//        private String street;
//        @Field(name="house")
//        private String house;
//        @Field(name="flat")
//        private String flat;
//        @Field(name="user_id")
//        private int user_id;
//
//        public Builder() {}
//
//        public Builder setId(int id) {
//            this.id = id;
//            return this;
//        }
//
//        public Builder setCity(String city) {
//            this.city = city;
//            return this;
//        }
//
//        public Builder setStreet(String street) {
//            this.street = street;
//            return this;
//        }
//
//        public Builder setHouse(String house) {
//            this.house = house;
//            return this;
//        }
//
//        public Builder setFlat(String flat) {
//            this.flat = flat;
//            return this;
//        }
//
//        public Builder setUser_id(int user_id) {
//            this.user_id = user_id;
//            return this;
//        }
//
//        public Address build(){
//            return new Address(this);
//        }
//    }
//
//    private Address(Builder builder){
//        this.id = builder.id;
//        this.city = builder.city;
//        this.street = builder.street;
//        this.house = builder.house;
//        this.flat = builder.flat;
//        this.user_id = builder.user_id;
//    }
//}
//
