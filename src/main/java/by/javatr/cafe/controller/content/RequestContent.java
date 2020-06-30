package by.javatr.cafe.controller.content;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class RequestContent{

    private Map<String, String[]> requestParameters;
    private Map<String, Object> requestAttributes;
    private Map<String, Object> sessionAttributes;
    String method;
    String URL;

    public RequestContent() {
        requestParameters = new HashMap<>();
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    /**
     *  Пересмотреть getParameterMap
     */
    public void setRequestParams(HttpServletRequest req){
        requestParameters = req.getParameterMap();
    }

    public void setRequestAttrs(HttpServletRequest req){
        Enumeration<String> attributeNames = req.getAttributeNames();

        while(attributeNames.hasMoreElements()){
            String s = attributeNames.nextElement();
            requestAttributes.put(s, req.getAttribute(s));
        }
    }

    public void setSessionAttrs(HttpServletRequest req){
        HttpSession session = req.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();

        while(attributeNames.hasMoreElements()){
            String s = attributeNames.nextElement();
            sessionAttributes.put(s,session.getAttribute(s));
        }
    }

    public void addAttrToSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        sessionAttributes.forEach(session::setAttribute);
    }

    public void addAttrToRequest(HttpServletRequest req){
        requestAttributes.forEach(req::setAttribute);
    }
    /******
     *
     */

    public String getRequestParam(String key){
        if (requestParameters.containsKey(key)) {
            return requestParameters.get(key)[0];
        }else return null;
    }

    public Object getSessionAttr(String key){
        return sessionAttributes.get(key);
    }

    public Object getRequestAttr(String key){
        return requestAttributes.get(key);
    }

    public void addSessionAttr(String key, Object value){
        sessionAttributes.put(key, value);
    }

    public void addRequestAttr(String key, Object value){
        requestAttributes.put(key, value);
    }

    public void setContent(HttpServletRequest req){
        setRequestAttrs(req);
        setRequestParams(req);
        setSessionAttrs(req);
        method = req.getMethod();
        URL = (String) req.getAttribute("URL");
    }

    public String getURL(){
        return URL;
    }

    public void invalidate(){
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        requestAttributes = new HashMap<>();
        // Объект сессии не удаляется
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(Map<String, Object> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public String getMethod() {
        return method;
    }
}
