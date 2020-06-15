package by.javatr.cafe;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.impl.AddAddress;


@Component
public class Test{
    @Autowired
    private AddAddress addAddress;

    @Autowired
    Test test;

    public AddAddress getAddAddress() {
        return addAddress;
    }

    public void setAddAddress(AddAddress addAddress) {
        this.addAddress = addAddress;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}


