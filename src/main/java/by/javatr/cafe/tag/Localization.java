package by.javatr.cafe.tag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization extends SimpleTagSupport {

    private static String message;

    public void setMessage(String input){
        message = input;
    }

    @Override
    public void doTag() throws IOException {

        String locale = (String) getJspContext().getAttribute("locale", PageContext.SESSION_SCOPE);

        ResourceBundle bundle = ResourceBundle.getBundle("locale", new Locale(locale));

        String message = bundle.getString(Localization.message);
        getJspContext().getOut().print(message);
    }

}
