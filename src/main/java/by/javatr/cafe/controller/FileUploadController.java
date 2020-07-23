package by.javatr.cafe.controller;

import by.javatr.cafe.aspectj.log.annotation.LogException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class FileUploadController extends HttpServlet {

    @Override
    @LogException
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());

        try {
            List<FileItem> multifiles = sf.parseRequest(req);

            for (FileItem item: multifiles){
                item.write(new File(req.getServletContext().getRealPath("./") + "\\static\\img\\meat\\" + item.getName()));
                resp.getWriter().print("\\static\\img\\meat\\" + item.getName());
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }

    }
}
