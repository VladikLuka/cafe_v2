package by.javatr.cafe.entity;

import java.io.*;


/**
 *  ordinal used to control access for pages
 */
public enum Role implements Serializable {

    ADMIN, USER, GUEST;

    public static Role getRoleByID(int id){
        if(id - 1 == ADMIN.ordinal()){
            return ADMIN;
        }
        else if (id - 1 == USER.ordinal()){
            return USER;
        }else return GUEST;
    }

    public static int getRoleID(Role role){

        switch (role){
            case ADMIN: return 1;
            case USER:  return 2;
            default: return 3;
        }
    }

    public static Role getRoleByName(final String name){

        if(name.equals(ADMIN.name())){
            return ADMIN;
        }else if(name.equals(USER.name())){
            return USER;
        }else return GUEST;

    }
}
