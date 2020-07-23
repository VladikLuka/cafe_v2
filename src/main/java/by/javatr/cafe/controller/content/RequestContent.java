package by.javatr.cafe.controller.content;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Contains useful information from request
 */
public class RequestContent{

    private Map<String, String[]> requestParameters;
    private Map<String, Object> requestAttributes;
    private Map<String, Object> sessionAttributes;
    String method;
    String url;

    public RequestContent() {
        requestParameters = new HashMap<>();
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    /**
     * set request params to requestParameters
     * @param req Request Object
     */
    public void setRequestParams(HttpServletRequest req){
        requestParameters = req.getParameterMap();
    }


    /**
     * set request attributes to requestAttributes
     * @param req Request Object
     */
    public void setRequestAttrs(HttpServletRequest req){
        Enumeration<String> attributeNames = req.getAttributeNames();

        while(attributeNames.hasMoreElements()){
            String s = attributeNames.nextElement();
            requestAttributes.put(s, req.getAttribute(s));
        }
    }

    /**
     * set session attributes to sessionAttributes
     * @param req Request Object
     */

    public void setSessionAttrs(HttpServletRequest req){
        HttpSession session = req.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();

        while(attributeNames.hasMoreElements()){
            String s = attributeNames.nextElement();
            sessionAttributes.put(s,session.getAttribute(s));
        }
    }


    /**
     * add attributes to session
     * @param req Request Object
     */
    public void addAttrToSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        sessionAttributes.forEach(session::setAttribute);
    }


    /**
     * add attributes to request
     * @param req Request Object
     */

    public void addAttrToRequest(HttpServletRequest req){
        requestAttributes.forEach(req::setAttribute);
    }


    /**
     * get parameter from requestParameters
     * @param key parameter name
     */
    public String getRequestParam(String key){
        if (requestParameters.containsKey(key)) {
            return requestParameters.get(key)[0];
        }else return null;
    }


    /**
     * get attribute from sessionAttributes
     * @param key attribute name
     */
    public Object getSessionAttr(String key){
        return sessionAttributes.get(key);
    }

    /**
     * get attribute from requestAttributes
     * @param key parameter name
     */
    public Object getRequestAttr(String key){
        return requestAttributes.get(key);
    }

    /**
     * add attribute to sessionAttributes
     * @param key parameter name
     */
    public void addSessionAttr(String key, Object value){
        sessionAttributes.put(key, value);
    }
    /**
     * add attribute to requestAttributes
     * @param key attribute name
     */
    public void addRequestAttr(String key, Object value){
        requestAttributes.put(key, value);
    }
    /**
     * add params to requestParameters
     * @param key attribute name
     */
    public void addRequestParams(String key, String[] params) {
        requestParameters.put(key, params);
    }

    /**
     * set information to request object
     * @param req request object
     */
    public void setContent(HttpServletRequest req){
        setRequestAttrs(req);
        setRequestParams(req);
        setSessionAttrs(req);
        method = req.getMethod();
        url = (String) req.getAttribute("URL");
    }

    /**
     * @return url
     */
    public String getUrl(){
        return url;
    }


    /**
     * clean session
     */
    public void invalidate(){
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        requestAttributes = new HashMap<>();
    }

    /**
     * @return requestParameters
     */
    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    /**
     * @param requestParameters set parameters
     */
    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    /**
     *
     * @return requestAttributes
     */
    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    /**
     *
     * @param requestAttributes set attributes
     */
    public void setRequestAttributes(Map<String, Object> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    /**
     * @return sessionAttributes
     */
    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    /**
     * @param sessionAttributes set attributes
     */
    public void setSessionAttributes(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    /**
     * @return method
     */
    public String getMethod() {
        return method;
    }
}
