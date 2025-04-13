package com.abc.controllers;

import com.abc.entities.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import com.abc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("provinces", provinceService.getAllProvinces());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam("avatar") MultipartFile avatar, Model model) {
        try {
            // Kiểm tra và lưu avatar
            if (!avatar.isEmpty() && (avatar.getContentType().equals("image/jpeg") || avatar.getContentType().equals("image/png")) && avatar.getSize() <= 200000) {
                String avatarPath = saveAvatar(avatar); // Lưu avatar vào thư mục cụ thể
                user.setAvatarPath(avatarPath);
            } else {
                model.addAttribute("error", "Avatar phải là file jpg/png và dung lượng không quá 200KB!");
                return "register";
            }

            // Đăng ký người dùng
            userService.registerUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    private String saveAvatar(MultipartFile avatar) {
        // Lưu ảnh vào thư mục trên server và trả về đường dẫn
        String fileName = avatar.getOriginalFilename();
        File dest = new File("src/main/webapp/resourse/images/" + fileName);
        try {
            avatar.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "src/main/webapp/resourse/images/" + fileName;
    }
}
