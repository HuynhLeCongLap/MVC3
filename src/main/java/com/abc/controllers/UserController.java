package com.abc.controllers;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abc.entities.Post;
import com.abc.entities.User;
import com.abc.services.PostService;

import jakarta.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.PostMapping;
import java.time.Period;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.abc.services.LocationService;
import org.springframework.web.bind.annotation.RequestParam;
import com.abc.services.UserService;
import java.time.ZoneId;









@Controller
public class UserController {
    
    private PostService postService;
    @Autowired
    private UserService userService;

    
    @Autowired
    private LocationService locationService;
    
    @Autowired
    public UserController(PostService postService) {
		this.postService = postService;
	}
	@GetMapping("/profile")
	public String profileUser(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		if(user == null)
			return "redirect:/login";
		
		List<Post> posts = new ArrayList<Post>();
		
		posts = postService.getPostById(user.getId());
		
		
		model.addAttribute("user",user);
		model.addAttribute("posts",posts);
		
		return "profile";
		
	}
	@GetMapping("/profile/edit")
	public String showEditProfileForm(Model model, HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user == null) return "redirect:/login";

	    model.addAttribute("user", user);
	    model.addAttribute("locations", locationService.getAllLocations()); // service dùng để load danh sách nơi ở
	    return "edit-profile";
	}
	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute("user") User user,
	                            @RequestParam("avatarFile") MultipartFile avatarFile,
	                            BindingResult result,
	                            HttpServletRequest request,
	                            Model model,
	                            HttpSession session) {
	    // Kiểm tra login
	    User currentUser = (User) session.getAttribute("user");
	    if (currentUser == null) return "redirect:/login";

	    // Kiểm tra tuổi
	    if (!isValidAge(user.getBirthday())) {
	        result.rejectValue("birthday", "error.user", "Bạn phải trên 15 tuổi.");
	    }

	    // Kiểm tra email đã tồn tại (nếu khác với email hiện tại)
	    if (!user.getEmail().equals(currentUser.getEmail()) && userService.isEmailExists(user.getEmail())) {
	        result.rejectValue("email", "error.user", "Email đã tồn tại.");
	    }

	    // Xử lý avatar
	    if (!avatarFile.isEmpty()) {
	        String contentType = avatarFile.getContentType();
	        if ((contentType.equals("image/jpeg") || contentType.equals("image/png")) && avatarFile.getSize() <= 200 * 1024) {
	            String uploadDir = request.getServletContext().getRealPath("/uploads/");
	            String fileName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
	            File file = new File(uploadDir, fileName);
	            try {
	                avatarFile.transferTo(file);
	                user.setAvatar("/uploads/" + fileName);
	            } catch (IOException e) {
	                result.rejectValue("avatar", "error.user", "Lỗi khi tải ảnh.");
	            }
	        } else {
	            result.rejectValue("avatar", "error.user", "Chỉ chấp nhận ảnh JPG/PNG ≤ 200KB.");
	        }
	    } else {
	        user.setAvatar(currentUser.getAvatar()); // Giữ nguyên nếu không thay đổi
	    }

	    if (result.hasErrors()) {
	        model.addAttribute("locations", locationService.getAllLocations());
	        return "edit-profile";
	    }

	    user.setId(currentUser.getId()); // Giữ lại ID cũ
	    userService.updateUserProfile(user); // cập nhật qua DAO
	    session.setAttribute("user", user); // cập nhật lại session

	    return "redirect:/profile";
	}

	private boolean isValidAge(Date birthday) {
	    LocalDate birthDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    return Period.between(birthDate, LocalDate.now()).getYears() >= 15;
	}

}
