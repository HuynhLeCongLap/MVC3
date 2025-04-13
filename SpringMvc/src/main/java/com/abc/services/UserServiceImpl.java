package com.abc.services;
import com.abc.dao.UserDAO;
import com.abc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.Duration;

@Service  // Đảm bảo rằng lớp này được Spring quản lý
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    // Kiểm tra người dùng có đủ 15 tuổi không
    private boolean isUserAgeValid(User user) {
        LocalDateTime currentDate = LocalDateTime.now();  // Thời gian hiện tại
        LocalDateTime birthDate = user.getDateOfBirth().atZone(ZoneOffset.UTC).toLocalDateTime(); // Ngày sinh của người dùng

        // Tính toán sự khác biệt giữa ngày hiện tại và ngày sinh
        Duration duration = Duration.between(birthDate, currentDate);
        long ageInYears = duration.toDays() / 365;  // Chuyển đổi từ ngày sang năm
        
        return ageInYears >= 15;
    }

    // Phương thức đăng ký người dùng
    @Transactional
    @Override
    public boolean registerUser(User user) {
        // Kiểm tra email đã tồn tại chưa
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        // Kiểm tra độ tuổi
        if (!isUserAgeValid(user)) {
            throw new IllegalArgumentException("Người dùng phải trên 15 tuổi!");
        }

        // Thực hiện lưu vào CSDL
        try {
            userDAO.save(user);
            return true;
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException("Dữ liệu không hợp lệ!");
        }
    }

    // Phương thức tìm người dùng theo tên đăng nhập
    @Override
    public User getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
}
