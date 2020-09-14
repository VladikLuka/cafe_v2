package by.javatr.cafe.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024,
        maxFileSize         = 1024 * 1024 * 10,
        maxRequestSize      = 1024 * 1024 * 15
)
public class FileUploadController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String uploadFilePath = req.getServletContext().getRealPath("");

        File fileSaveDir = new File(uploadFilePath);
        if(!fileSaveDir.exists()){
            fileSaveDir.mkdirs();
        }

        for (Part part: req.getParts()) {
            if(part.getSubmittedFileName() != null){
                part.write(uploadFilePath + File.separator + "\\static\\img\\upload\\" + part.getSubmittedFileName());
                resp.getWriter().print("\\static\\img\\upload\\" + part.getSubmittedFileName());
            }
        }
    }
}
