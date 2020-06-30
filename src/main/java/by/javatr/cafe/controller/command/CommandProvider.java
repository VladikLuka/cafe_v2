package by.javatr.cafe.controller.command;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.impl.*;
import by.javatr.cafe.controller.command.impl.admin.*;
import by.javatr.cafe.controller.command.impl.payment.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandProvider {

    private final Map<CommandName, Command> repository = new HashMap<>();

    BeanFactory factory = BeanFactory.getInstance();

    public CommandProvider(){
        repository.put(CommandName.SIGNUP,(SignUp) factory.getBean("signUp"));
        repository.put(CommandName.WRONG_REQUEST, (WrongRequest) factory.getBean("wrongRequest"));
        repository.put(CommandName.GET_PAGE, (GetPage)factory.getBean("getPage"));
        repository.put(CommandName.LOGIN, (LogIn)factory.getBean("logIn"));
        repository.put(CommandName.ADD_TO_CART, (AddToCart) factory.getBean("addToCart"));
        repository.put(CommandName.LOGOUT,(LogOut) factory.getBean("logOut"));
        repository.put(CommandName.GET_CART, (GetDishInCart) factory.getBean("getDishInCart"));
        repository.put(CommandName.CLEAN_CART,  (CleanCart) factory.getBean("cleanCart"));
        repository.put(CommandName.DELETE_FROM_CART, (DeleteDishFromCart) factory.getBean("deleteDishFromCart"));
        repository.put(CommandName.ADD_ADDRESS, (AddAddress)factory.getBean("addAddress"));
        repository.put(CommandName.DELETE_ADDRESS, (DeleteAddress) factory.getBean("deleteAddress"));
        repository.put(CommandName.PAY, (Deposit) factory.getBean("deposit"));
        repository.put(CommandName.GET_TOKEN, (ReturnToken) factory.getBean("returnToken"));
        repository.put(CommandName.CHANGE_PASS, (ChangePass) factory.getBean("changePass"));
        repository.put(CommandName.MAKE_ORDER, (MakeOrderCard) factory.getBean("makeOrderCard"));
        repository.put(CommandName.BALANCE_ORDER, (BalanceOrder) factory.getBean("balanceOrder"));
        repository.put(CommandName.SHOW_DISH, (ShowDish) factory.getBean("showDish"));
        repository.put(CommandName.HIDE_DISH, (HideDish) factory.getBean("hideDish"));
        repository.put(CommandName.EXPECT_ORDER, (CloseOrder) factory.getBean("expectOrder"));
        repository.put(CommandName.FEEDBACK, (Feedback) factory.getBean("feedback"));
        repository.put(CommandName.CANCEL_ORDER, (CancelOrder) factory.getBean("cancelOrder"));
        repository.put(CommandName.CLOSE_ORDER, (CloseOrder) factory.getBean("closeOrder"));
        repository.put(CommandName.SHOW_USER_INFO, (ShowUserInfo) factory.getBean("showUserInfo"));
        repository.put(CommandName.ADD_MONEY, (AddMoney) factory.getBean("addMoney"));
        repository.put(CommandName.SUBTRACT_MONEY, (SubtractMoney) factory.getBean("subtractMoney"));
        repository.put(CommandName.ADD_POINTS, (AddPoints) factory.getBean("addPoints"));
        repository.put(CommandName.SUBTRACT_POINTS, (SubtractPoints) factory.getBean("subtractPoints"));
        repository.put(CommandName.VIOLATE_ORDER, (ViolateOrder) factory.getBean("violateOrder"));
        repository.put(CommandName.BAN_USER, (BanUser) factory.getBean("banUser"));
        repository.put(CommandName.UNBAN_USER, (UnbanUser) factory.getBean("unbanUser"));
        repository.put(CommandName.CASH_ORDER, (CashOrder) factory.getBean("cashOrder"));
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
