<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Caesar Crypto Code</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="https://unpkg.com/feather-icons"></script>
</head>
<body>

<header class="bg-black sticky-top py-3">
    <div class="container">
        <h1 class="h2 text-white text-center">App Caesar Code</h1>
    </div>
</header>
<div class="container-fluid px-3 py-5">
    <div class="row mb-3">
        <div class="col-12 col-lg-8 offset-lg-2">
            <h5 class="text-primary">1. Encryption/Decryption by key</h5>
            <form enctype="multipart/form-data" method="post">
                <div class="mb-1">
                    <label for="textarea" class="form-label">Insert your text:</label>
                    <textarea class="form-control" id="textarea" name="message" rows="2">${message}</textarea>
                </div>
                <div class="mb-1">
                    <label for="file" class="form-label">Or upload your text file:</label>
                    <input class="form-control" id="file" type="file" name="file" accept="application/msword,text/plain,.docx">
                </div>
                <div class="mb-1 d-lg-flex justify-content-lg-between align-items-lg-end flex-lg-row">
                    <div>
                        <label for="key" class="form-label">Enter key encryption/decryption (from 1 to ${length_alphabet}):</label>
                        <input type="number" class="form-control" id="key" name="key" value="${key}" min="0" max="${length_alphabet}" required>
                    </div>
                    <div class="mt-2">
                        <button class="btn btn-secondary" type="submit" formaction="./encrypt">Encrypt text</button>
                        <button class="btn btn-warning" type="submit" formaction="./decrypt">Decrypt text</button>
                    </div>
                </div>
            </form>
            <div class="mt-3">
                <p class="mb-2 text-danger lead">Result of encryption/decryption by key:</p>
                <div class="encrypt__result border border-1 rounded border-success p-3">
                    <p class="insert-encrypted-text">${encrypted_message}</p>
                    <% if(pageContext.getSession().getAttribute("downloadResultFile") != null) { %>
                    <p><i data-feather="arrow-down-circle"></i> <a href="${downloadResultFile}" class="link-danger" target="_blank" download> Download result file of encryption/decryption</a></p>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid px-3 py-5 bg-dark text-bg-dark">
    <div class="row">
        <div class="col-12 col-lg-8 offset-lg-2">
            <h5 class="text-primary">2. If you don't know key, you can try "Brute Force" method</h5>
            <form enctype="multipart/form-data" method="post">
                <div class="mb-1">
                    <label for="file_bf" class="form-label">Upload your text file:</label>
                    <input class="form-control" id="file_bf" type="file" name="file_bf" accept="application/msword,text/plain,.docx">
                </div>
                    <button class="btn btn-success mt-3" type="submit" formaction="./bruteforce">Decrypt</button>
            </form>
            <div class="mt-3">
                <p class="mb-2 text-danger lead">Result of decryption Brute force:</p>
                <div class="border border-1 rounded border-success p-3">
                    <% if(pageContext.getSession().getAttribute("downloadResultFileBF") != null) { %>
                    <p><i data-feather="arrow-down-circle"></i> <a href="${downloadResultFileBF}" class="link-danger" target="_blank" download> Download result zip file of decryption</a></p>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="py-3 bg-warning">
    <div class="container">
        <h1 class="h2 text-center">App Caesar Code</h1>
    </div>
</footer>
<div class="container-fluid bg-warning pt-2">
    <div class="row">
        <div class="col-12 col-lg-8 offset-lg-2 d-flex justify-content-center">
            <p class="mx-2 small">Container: <%= application.getServerInfo() %></p>
            <p class="mx-2 small">Servlet Version: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %></p>
            <p class="mx-2 small">JSP Version: <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %></p>
        </div>
    </div>
</div>
<script src="assets/js/jquery.js"></script>
<script>
    feather.replace();
</script>
</body>
</html>