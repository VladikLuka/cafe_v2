package by.javatr.cafe.controller.content;

import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestResult {

    Navigation nav;
    String page;
    String command;
    int status;
    Map<String, String> headers = new HashMap<>();


    public RequestResult(Navigation nav, String page, int status){
        this.nav = nav;
        this.page = page;
        this.status = status;
    }

    public RequestResult(String command, int status) {
        this.command = command;
        this.status = status;
    }


    @Override
    public String toString() {
        return "RequestResult{" +
                "nav=" + nav +
                ", page=" + page +
                ", command='" + command + '\'' +
                ", status=" + status +
                '}';
    }




    public RequestResult(int status) {
        this.status = status;
    }

    public void setResponseHeaders(HttpServletResponse response){
        for (String str:headers.keySet()) {
            response.setHeader(str, headers.get(str));
        }
    }

    public void setResponseData(HttpServletResponse response){
        setResponseHeaders(response);
        setResponseStatus(response);
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResponseStatus(HttpServletResponse response){
        response.setStatus(status);
    }

    public RequestResult(){}


    public RequestResult(Navigation nav, String page) {
        this.nav = nav;
        this.page = page;
    }

    public RequestResult(Navigation nav, String page, String command) {
        this.nav = nav;
        this.page = page;
        this.command = command;
    }



    public Set<String> getHeaders() {
        return headers.keySet();
    }

    public String getHeader(String key){
        return headers.get(key);
    }
    public void setHeaders(String head, String content) {
        headers.put(head, content);
    }

    public Navigation getNav() {
        return nav;
    }

    public void setNav(Navigation nav) {
        this.nav = nav;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
