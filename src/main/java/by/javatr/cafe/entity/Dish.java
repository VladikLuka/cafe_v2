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
    int categoryId;
    @Field(name="dish_picture_path")
    String picturePath;

    public Dish(int id) {
        this.id = id;
    }

    public Dish(){

    }

    public Dish(int id, String name, String description, BigDecimal price, boolean isAvailable, int weight, int categoryId, String picturePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.weight = weight;
        this.categoryId = categoryId;
        this.picturePath = picturePath;
    }

    public Dish(int id, String name, BigDecimal price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Dish(String name, String description, BigDecimal price, boolean isAvailable, int weight, int categoryId, String picturePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.weight = weight;
        this.categoryId = categoryId;
        this.picturePath = picturePath;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id &&
                isAvailable == dish.isAvailable &&
                weight == dish.weight &&
                categoryId == dish.categoryId &&
                Objects.equals(name, dish.name) &&
                Objects.equals(description, dish.description) &&
                Objects.equals(price, dish.price) &&
                Objects.equals(picturePath, dish.picturePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, isAvailable, weight, categoryId, picturePath);
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
                ", category_id=" + categoryId +
                ", picture_path='" + picturePath + '\'' +
                '}';
    }
}
