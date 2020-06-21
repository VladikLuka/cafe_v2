package by.javatr.cafe.entity;

import by.javatr.cafe.dao.annotation.Field;
import by.javatr.cafe.dao.annotation.Id;
import by.javatr.cafe.dao.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Table(table="dish")
public class Dish extends Entity<Dish> implements Serializable {

    private static final long serialVersionUID = -5205629040527211345L;

    @Id(name="dish_id")
    int id;
    @Field(name="dish_name")
    String name;
    @Field(name="dish_description")
    String description;
    @Field(name="dish_price")
    BigDecimal price;
    @Field(name="dish_isAvailable")
    boolean isAvailable;
    @Field(name="dish_weight")
    int weight;
    @Field(name="categories_category_id")
    int category_id;
    @Field(name="dish_picture_path")
    String picture_path;

    public Dish(int id) {
        this.id = id;
    }

    public Dish(){

    }

    public Dish(int id, String name, String description, BigDecimal price, boolean isAvailable, int weight, int category_id, String picture_path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.weight = weight;
        this.category_id = category_id;
        this.picture_path = picture_path;
    }

    public Dish(int id, String name, BigDecimal price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Dish(String name, String description, BigDecimal price, boolean isAvailable, int weight, int category_id, String picture_path) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.weight = weight;
        this.category_id = category_id;
        this.picture_path = picture_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id &&
                isAvailable == dish.isAvailable &&
                weight == dish.weight &&
                category_id == dish.category_id &&
                Objects.equals(name, dish.name) &&
                Objects.equals(description, dish.description) &&
                Objects.equals(price, dish.price) &&
                Objects.equals(picture_path, dish.picture_path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, isAvailable, weight, category_id, picture_path);
    }

    @Override
    public String toString() {
        return getClass().getName() + " @ {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", weight=" + weight +
                ", category_id=" + category_id +
                ", picture_path='" + picture_path + '\'' +
                '}';
    }
}
