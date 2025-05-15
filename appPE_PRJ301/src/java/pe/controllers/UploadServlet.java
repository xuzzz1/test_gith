/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UploadServlet extends HttpServlet {
 private static final String UPLOAD_DIRECTORY = "C:/uploaded_images"; // Đường dẫn mới

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Part filePart = request.getPart("img"); // Lấy file từ form
    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

    // Đường dẫn đầy đủ để lưu file
    String filePath = UPLOAD_DIRECTORY + File.separator + fileName;
    
    // Ghi file vào thư mục đã tạo
    filePart.write(filePath);

    // Tạo đường dẫn để hiển thị ảnh trên trình duyệt
    String imageUrl = "file:///" + filePath.replace("\\", "/");

    // Lưu đường dẫn ảnh vào session để hiển thị
    request.getSession().setAttribute("imageUrl", imageUrl);
    response.sendRedirect("bookcreate.jsp");
}

}
