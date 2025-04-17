<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.abc.entities.User" %>

<html>
<head>
    <title>Tìm kiếm người dùng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        form {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: inline-block;
        }
        input {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
        hr {
            margin: 20px auto;
            width: 50%;
        }
        ul {
            list-style: none;
            padding: 0;
        }
        li {
            background: #fff;
            padding: 10px;
            margin: 5px auto;
            width: 50%;
            border-radius: 5px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }
        img {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h2>Tìm người dùng theo số follower/following</h2>

    <form action="<%= request.getContextPath() %>/users/search" method="get">
        <label>Min Followers:</label>
       <input type="number" name="minFollowers" value="<%= request.getAttribute("minFollowers") %>" required />
        <label>Min Following:</label>
       <input type="number" name="minFollowing" value="<%= request.getAttribute("minFollowing") %>" required />
        <br><br>
        <button type="submit">Tìm kiếm</button>
    </form>

    <hr>

    <%
        List<User> users = (List<User>) request.getAttribute("users"); // Lấy danh sách users từ model (request)
        if (users != null && !users.isEmpty()) {
    %>
        <h3>Kết quả:</h3>
        <ul>
            <%
                for (User user : users) {
            %>
                <li><strong><%= user.getUsername() %></strong></li>
            <%
                }
            %>
        </ul>
    <%
        } else {
    %>
        <h3>Không tìm thấy người dùng nào.</h3>
        <img src="${pageContext.request.contextPath}/resources/images/not-found.png"
            alt="Avatar" class="rounded-circle" width="300" alt="Not Found" width="300"/>
    <%
        }
    %>
</body>
</html>

	
