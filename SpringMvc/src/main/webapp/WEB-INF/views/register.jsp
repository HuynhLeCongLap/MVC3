<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #7f00ff, #e100ff);
        }
        .register-container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 400px;
        }
    </style>
</head>
<body>
    <div class="register-container text-center">
        <h2 class="mb-4">Create Account</h2>
        <form action="register" method="post" enctype="multipart/form-data">
            
            <!-- Tên đăng nhập -->
            <div class="mb-3">
                <div class="input-group">
                    <span class="input-group-text">👤</span>
                    <input type="text" name="username" class="form-control" placeholder="Tên Đăng Nhập" required>
                </div>
            </div>

            <!-- Mật khẩu -->
            <div class="mb-3">
                <div class="input-group">
                    <span class="input-group-text">🔒</span>
                    <input type="password" name="password" class="form-control" placeholder="Mật Khẩu" required>
                </div>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <div class="input-group">
                    <span class="input-group-text">📧</span>
                    <input type="email" name="email" class="form-control" placeholder="Email" required>
                </div>
            </div>

            <!-- Ngày sinh -->
            <div class="mb-3">
                <div class="input-group">
                    <span class="input-group-text">🎂</span>
                    <input type="date" name="dateOfBirth" class="form-control" required>
                </div>
            </div>

            <!-- Nơi ở -->
            <div class="mb-3">
                <select name="provinceId" class="form-control" required>
                    <option value="">Chọn Nơi Ở</option>
                    <c:forEach var="province" items="${provinces}">
                        <option value="${province.id}">${province.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Avatar -->
            <div class="mb-3">
                <div class="input-group">
                    <input type="file" name="avatar" class="form-control" accept="image/jpeg, image/png" required>
                </div>
            </div>

            <!-- Nút đăng ký -->
            <button type="submit" class="btn btn-success w-100">Register</button>
        </form>

        <!-- Thông báo lỗi hoặc hướng dẫn -->
        <p class="mt-3">Already have an account? <a href="login">Login here</a></p>
        <p style="color:red;">${error}</p>
    </div>
</body>
</html>
