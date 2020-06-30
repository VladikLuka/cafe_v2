package by.javatr.cafe.entity;

import by.javatr.cafe.dao.annotation.Field;

import java.io.*;


/**
 *  ordinal used to control access
 */
public enum Role implements Serializable {

    ADMIN(1),
    USER(2),
    GUEST(3);

    private static final long serialVersionUID = 7789895012113235497L;

//    public static Role getRoleByID(int id){
//        if(id - 1 == ADMIN.ordinal()){
//            return ADMIN;
//        }
//        else if (id - 1 == USER.ordinal()){
//            return USER;
//        }else return GUEST;
//    }
//
//    public static int getRoleID(Role role){
//
//        switch (role){
//            case ADMIN: return 1;
//            case USER:  return 2;
//            default: return 3;
//        }
//    }

    public static Role getRoleByName(final String name){

        if(name.equals(ADMIN.name())){
            return ADMIN;
        }else if(name.equals(USER.name())){
            return USER;
        }else return GUEST;

    }

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
