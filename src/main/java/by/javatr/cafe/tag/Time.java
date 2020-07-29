package by.javatr.cafe.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time extends SimpleTagSupport {

    Logger logger = LogManager.getLogger(Time.class);

    private String tagTime;

    public void setTime(String time) {
        this.tagTime = time;
    }

    @Override
    public void doTag() {

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            final Date parse = instance.parse(tagTime);

            final long credit_time = parse.getTime();

            final long now = Calendar.getInstance().getTime().getTime();

            long differ = credit_time - now;


            long days = differ / 86_400_000;

            getJspContext().getOut().print(days);

        } catch (ParseException | IOException e) {
            logger.error(e);
        }
    }
}
