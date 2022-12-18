package servlets;

import logic.Crypto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@WebServlet(name = "BruteForce", value = "/bruteforce")
@MultipartConfig
public class DecryptBruteforceServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        String lengthAlphabetString = (String) getServletContext().getAttribute("length_alphabet");
        String UPLOAD_DIR = (String) getServletContext().getAttribute("upload_dir");
        String RESULT_DIR = (String) getServletContext().getAttribute("result_dir");
        int lengthAlphabet = Integer.parseInt(lengthAlphabetString);

        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;
        String resultPath = getServletContext().getRealPath("") + RESULT_DIR;
        File uploadDir = new File(uploadPath);
        File resultDir = new File(resultPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        if (!resultDir.exists()) resultDir.mkdir();

        Part filePart = req.getPart("file_bf");

        if(!filePart.getSubmittedFileName().isEmpty()) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String pathToFile = uploadDir + File.separator + fileName;

            //Создадим архив для упаковки всех файлов в него
            ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(resultPath + File.separator + "allKeyVariants.zip"));

            //Перебор всех ключей
            for (int key = 1; key <= lengthAlphabet; key++) {
                InputStream inputStream = filePart.getInputStream();
                File newFile = new File(pathToFile);
                try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                    int read;
                    byte[] bytes = new byte[65536];
                    while((read = inputStream.read(bytes)) != -1) {
                        LOGGER.debug("key_" + key + " - " + read);
                        outputStream.write(bytes, 0, read);
                    }
                }

                String resultFileName = "result-key-" +key + "_" + fileName;
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

                ZipEntry zipEntry = new ZipEntry(resultFileName);
                zipFile.putNextEntry(zipEntry);
            }
            String resultArchiveFile = getServletContext().getContextPath() + File.separator + RESULT_DIR + File.separator + "allKeyVariants.zip";
            session.setAttribute("downloadResultFileBF", resultArchiveFile);
        }
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        session.invalidate();


    }
}
