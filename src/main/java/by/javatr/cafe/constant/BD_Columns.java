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
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_STREET = "street";
    public static final String ADDRESS_HOUSE = "house";
    public static final String ADDRESS_FLAT = "flat";
    public static final String USER_ID = "user_id";


    //CATEGORY//
    public static final String CATEGORY_ID = "categories_category_id";
    public static final String CATEGORY_NAME = "category_name";


    //ORDER//
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_STATUS = "order_status";
    public static final String ORDER_RECEIPT_TIME = "order_receipt_time";
    public static final String ORDER_PAYMENT_METHOD = "order_payment_method";
    public static final String ORDER_RATING = "order_rating";
    public static final String ORDER_REVIEW = "order_review";
    public static final String USER_OWNER_ID = "users_ownerId";


    //ORDERS_DISHES//

    public static final String ORDERS_DISHES_QUANTITY = "orders_has_dishes_quantity";
    public static final String ORDERS_ORDER_ID = "orders_order_id";
    public static final String DISHES_DISH_ID = "dishes_dish_id";
    public static final String BRAINTREE_ORDER_ID = "braintree_order_id";
}

