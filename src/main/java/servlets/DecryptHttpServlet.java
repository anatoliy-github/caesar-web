package servlets;

import logic.Crypto;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;

@WebServlet(name = "Decrypt", value = "/decrypt")
@MultipartConfig
public class DecryptHttpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String message = req.getParameter("message");
        String keyStr = req.getParameter("key");
        String UPLOAD_DIR = (String) getServletContext().getAttribute("upload_dir");
        String RESULT_DIR = (String) getServletContext().getAttribute("result_dir");
        int key = 0;
        if(!keyStr.isBlank() || !keyStr.isBlank()) {
            key = Integer.parseInt(keyStr);
        }

        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;
        String resultPath = getServletContext().getRealPath("") + RESULT_DIR;
        File uploadDir = new File(uploadPath);
        File resultDir = new File(resultPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        if (!resultDir.exists()) resultDir.mkdir();

        Part filePart = req.getPart("file");

        if(!filePart.getSubmittedFileName().isEmpty()) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String pathToFile = uploadDir + File.separator + fileName;
            InputStream inputStream = filePart.getInputStream();
            File newFile = new File(pathToFile);

            try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                int read;
                byte[] bytes = new byte[65536];
                while((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }

            String resultFileName = "result-" + fileName;
            String pathToResultFile = resultDir + File.separator + resultFileName;

            try(FileReader fileReader = new FileReader(pathToFile);
                BufferedReader reader = new BufferedReader(fileReader);
                FileWriter fileWriter = new FileWriter(pathToResultFile);
                BufferedWriter writer = new BufferedWriter(fileWriter)) {
                while(reader.ready()) {
                    String str = reader.readLine();
                    writer.write(Crypto.decryptByKey(str, key));
                    writer.newLine();
                }
            }

            String downloadResultFile = getServletContext().getContextPath() + File.separator + RESULT_DIR + File.separator + resultFileName;
            session.setAttribute("downloadResultFile", downloadResultFile);
        }

        String encryptedMessage = Crypto.decryptByKey(message, key);
        session.setAttribute("message", message);
        session.setAttribute("key", keyStr);
        session.setAttribute("encrypted_message", encryptedMessage);

        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        session.invalidate();
    }
}
