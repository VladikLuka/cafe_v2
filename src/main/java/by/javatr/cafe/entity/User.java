package by.javatr.cafe.entity;

import by.javatr.cafe.dao.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(table = "user")
public class User extends Entity<User> implements Serializable {

    private static final long serialVersionUID = 7789895012127236697L;

    @Id(name = "user_id")
    @Field(name = "user_id")
    private int id;
    @Field(name="user_name")
    private String name;
    @Field(name="user_surname")
    private String surname;
    @Field(name="user_phone")
    private String phone;
    @Field(name="user_email")
    private String mail;
    @Field(name="user_password")
    private String password;
    @Field(name="user_money")
    private BigDecimal money = new BigDecimal(0);
    @Field(name="user_loyaltyPoints")
    private int loyaltyPoint = 0;
    @Field(name="user_isCredit")
    private boolean isCredit;
    @Field(name = "user_isBan")
    private boolean isBan;    @Join(fieldColumn =  "roles_role_id")
//    @ManyToOne(joinName = "role_id",field = ,type = Role.class)
    private Role role = Role.GUEST;
    @Join(fieldColumn = "user_id")
    @OneToMany(joinName = "address_user_id", field = "",type = Address.class)
    private List<Address> address = new ArrayList<>();

    public User(){

    }

    public User(int id, String name, String surname, String phone, String mail, String password, BigDecimal money, int loyaltyPoint, Role role, boolean isBan, boolean isCredit) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.money = money;
        this.loyaltyPoint = loyaltyPoint;
        this.role = role;
        this.isBan = isBan;
        this.isCredit = isCredit;
    }

    public User(String email, String password, String phone){
        this.mail = email;
        this.password = password;
        this.phone = phone;
    }

    public User(int id, String name, String surname, String phone, String mail, String password, BigDecimal money, int loyaltyPoint, Role role, boolean isBan, List<Address> address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.money = money;
        this.loyaltyPoint = loyaltyPoint;
        this.role = role;
        this.isBan = isBan;
        this.address = address;
    }

    public User(int id, String name, String surname, String phone, String mail, String password, BigDecimal money, int loyaltyPoint, List<Address> address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.money = money;
        this.loyaltyPoint = loyaltyPoint;
        this.address = address;
    }

    public User(String name, String surname, String phone, String mail, String password, List<Address> address ){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
        this.mail = mail;
        money = new BigDecimal(0);
        loyaltyPoint = 0;
        role = Role.USER;
        this.address = address;
    }

    public User(int id, String name, String surname, String phone, String mail, String password, List<Address> address ){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
        this.mail = mail;
        money = new BigDecimal(0);
        loyaltyPoint = 0;
        role = Role.USER;
        this.address = address;
    }

    public User(int id, String name, String surname, String phone, String mail, String password, BigDecimal money, int loyaltyPoint, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.money = money;
        this.loyaltyPoint = loyaltyPoint;
        this.role = role;
    }


    public User(String name, String surname, String phone, String mail, String password, BigDecimal money, int loyaltyPoint, Role role, List<Address> address ){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
        this.mail = mail;
        this.money = money;
        this.loyaltyPoint = loyaltyPoint;
        this.role = role;
        this.address = address;
    }

    public User(String name, String surname, String email, String password, String phone) {
        this.name = name;
        this.surname = surname;
        this.mail = email;
        this.password = password;
        this.phone = phone;
    }

    public User(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                loyaltyPoint == user.loyaltyPoint &&
                isBan == user.isBan &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(password, user.password) &&
                Objects.equals(money, user.money) &&
                role == user.role &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phone, mail, password, money, loyaltyPoint, role, isBan, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                ", loyalty_point=" + loyaltyPoint +
                ", role=" + role +
                ", isBan=" + isBan +
                ", address=" + address +
                '}';
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getLoyaltyPoint() {
        return loyaltyPoint;
    }

    public void setLoyaltyPoint(int loyalty_point) {
        this.loyaltyPoint = loyalty_point;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean hasCredit) {
        this.isCredit = hasCredit;
    }
}