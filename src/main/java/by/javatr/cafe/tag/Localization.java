package by.javatr.cafe.tag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization extends SimpleTagSupport {

    private String tagMessage;

    public void setMessage(String input){
        tagMessage = input;
    }

    public String getMessage() {
        return tagMessage;
    }

    @Override
    public void doTag() throws IOException {

        String locale = (String) getJspContext().getAttribute("locale", PageContext.SESSION_SCOPE);

        ResourceBundle bundle = ResourceBundle.getBundle("locale", new Locale(locale));

        if(bundle.containsKey(tagMessage)){
            String message = bundle.getString(getMessage());
            getJspContext().getOut().print(message);
        }else{
            final String[] split = tagMessage.split("\\.");
            getJspContext().getOut().print(split[split.length - 1]);
        }

    }

}
