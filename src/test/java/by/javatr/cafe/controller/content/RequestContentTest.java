//package by.javatr.cafe.controller.content;
//
//import by.javatr.cafe.controller.content.RequestContent;
//import by.javatr.cafe.entity.User;
//import org.junit.Test;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import static org.mockito.Mockito.*;
//
//public class RequestContentTest {
//
//    @Test
//    public void addAttributeToSessionTest(){
//
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpSession session = mock(HttpSession.class);
//
//        User user = new User();
//        RequestContent content = new RequestContent();
//
//
//        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute("user")).thenReturn(user);
//
//        content.addSessionAttr("user", user);
//        content.addAttrToSession(request);
//
//        verify(request).getSession();
//
//
//
//    }
//
//}
