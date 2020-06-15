package by.javatr.cafe.controller.command;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.controller.command.impl.*;
import by.javatr.cafe.controller.command.impl.payment.BalanceOrder;
import by.javatr.cafe.controller.command.impl.payment.Deposit;
import by.javatr.cafe.controller.command.impl.payment.MakeOrderCard;
import by.javatr.cafe.controller.command.impl.payment.returnToken;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private final Map<CommandName, Command> repository = new HashMap<>();

    BeanFactory factory = BeanFactory.getInstance();

    public CommandProvider(){
        repository.put(CommandName.SIGNUP,(SignUp) factory.getBean("signUp"));
        repository.put(CommandName.WRONG_REQUEST, (WrongRequest) factory.getBean("wrongRequest"));
        repository.put(CommandName.GET_PAGE, (Get_Page)factory.getBean("get_Page"));
        repository.put(CommandName.LOGIN, (LogIn)factory.getBean("logIn"));
        repository.put(CommandName.ADD_TO_CART, (AddToCart) factory.getBean("addToCart"));
        repository.put(CommandName.LOGOUT,(LogOut) factory.getBean("logOut"));
        repository.put(CommandName.GET_CART, (GetDishInCart) factory.getBean("getDishInCart"));
        repository.put(CommandName.CLEAN_CART,  (CleanCart) factory.getBean("cleanCart"));
        repository.put(CommandName.DELETE_FROM_CART, (DeleteDishFromCart) factory.getBean("deleteDishFromCart"));
        repository.put(CommandName.ADD_ADDRESS, (AddAddress)factory.getBean("addAddress"));
        repository.put(CommandName.DELETE_ADDRESS, (DeleteAddress) factory.getBean("deleteAddress"));
        repository.put(CommandName.PAY, (Deposit) factory.getBean("deposit"));
        repository.put(CommandName.GET_TOKEN, (returnToken) factory.getBean("returnToken"));
        repository.put(CommandName.CHANGE_PASS, (ChangePass) factory.getBean("changePass"));
        repository.put(CommandName.MAKE_ORDER, (MakeOrderCard) factory.getBean("makeOrderCard"));
        repository.put(CommandName.BALANCE_ORDER, (BalanceOrder) factory.getBean("balanceOrder"));
    }

    public Command getCommand(String name){

        CommandName commandName = null;
        Command command = null;
        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        }catch (Exception e){
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }

}
