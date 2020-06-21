package by.javatr.cafe.constant;

public class BD_Columns {


    // DISH //
    public static final String DISH_ID = "dish_id";
    public static final String DISH_NAME = "dish_name";
    public static final String DISH_DESCRIPTION = "dish_description";
    public static final String DISH_PRICE = "dish_price";
    public static final String DISH_IS_AVAILABLE = "dish_isAvailable";
    public static final String DISH_CATEGORY_ID = "categories_category_id";
    public static final String DISH_WEIGHT = "dish_weight";
    public static final String DISH_PICTURE_PATH = "dish_picture_path";


    //ADDRESS//
    public static final String ADDRESS_ID = "address_id";
    public static final String ADDRESS_CITY = "address_city";
    public static final String ADDRESS_STREET = "address_street";
    public static final String ADDRESS_HOUSE = "address_house";
    public static final String ADDRESS_FLAT = "address_flat";
    public static final String ADDRESS_USER_ID = "address_user_id";


    //CATEGORY//
    public static final String CATEGORY_ID = "categories_category_id";
    public static final String CATEGORY_NAME = "category_name";


    //ORDER//
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_STATUS = "order_status";
    public static final String ORDER_RECEIPT_TIME = "order_receipt_time";
    public static final String ORDER_DELIVERY_TIME = "order_delivery_time";
    public static final String ORDER_PAYMENT_METHOD = "order_payment_method";
    public static final String ORDER_RATING = "order_rating";
    public static final String ORDER_REVIEW = "order_review";
    public static final String USER_OWNER_ID = "users_ownerId";


    //ORDERS_DISHES//

    public static final String ORDERS_DISHES_QUANTITY = "orders_has_dishes_quantity";
    public static final String ORDERS_ORDER_ID = "orders_order_id";
    public static final String DISHES_DISH_ID = "dishes_dish_id";
    public static final String BRAINTREE_ORDER_ID = "braintree_order_id";

    //USER//
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_SURNAME = "user_surname";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_MONEY = "user_money";
    public static final String USER_LOYALTY = "user_loyaltyPoints";
    public static final String USER_ROLE_ID = "roles_role_id";
    public static final String USER_IS_BAN = "user_isBan";

    //ROLE//
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";

    private BD_Columns() {
    }
}

