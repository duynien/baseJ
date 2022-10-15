package com.bezkoder.spring.data.jpa.test.controller;

import com.bezkoder.spring.data.jpa.test.model.dto.ERole;
import com.bezkoder.spring.data.jpa.test.model.entity.App_Role;
import com.bezkoder.spring.data.jpa.test.model.entity.App_User;
import com.bezkoder.spring.data.jpa.test.model.entity.ForgetPassword;
import com.bezkoder.spring.data.jpa.test.model.entity.NewPassword;
import com.bezkoder.spring.data.jpa.test.repository.AppUserRepository;
import com.bezkoder.spring.data.jpa.test.utils.EncrytedPasswordUtils;
import com.bezkoder.spring.data.jpa.test.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class LoginController {
  EncrytedPasswordUtils encoder = new EncrytedPasswordUtils();
  @Autowired private AppUserRepository appUserRepository;
  @Autowired JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String emailFrom;
  @RequestMapping(
      value = {"/"},
      method = RequestMethod.GET)
  public String welcomePage(Model model) {
    model.addAttribute("title", "Welcome");
    model.addAttribute("message", "This is welcome page!");
    return "view/welcomePage";
  }

  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  public String adminPage(Model model, @RequestParam(required = false) String type) {
    if (type == null) {
      List<App_User> employees = appUserRepository.getListEmployee();
      model.addAttribute("account", employees);
      model.addAttribute("title", "Employee");
    }
    if (type != null && !type.isEmpty()) {
      if (type.equals("employee")) {
        model.addAttribute("account", appUserRepository.getListEmployee());
        model.addAttribute("title", "Employee");
      } else if (type.equals("user")) {
        model.addAttribute("account", appUserRepository.getListUser());
        model.addAttribute("title", "User");
      }
    }
    return "view/adminPage";
  }

  @RequestMapping(value = "/accout/login")
  public String login(@RequestParam(required = false) String message, Model model) {
    if (message != null && !message.isEmpty()) {
      if (message.equals("timeout")) {
        model.addAttribute("message", "Time out");
      }
      if (message.equals("max_session")) {
        model.addAttribute("message", "This accout has been login from another device!");
      }
      if (message.equals("logout")) {
        model.addAttribute("message", "Logout!");
      }
      if (message.equals("error")) {
        model.addAttribute("message", "Login Failed!");
      }
    }
    return "view/loginPage";
  }

  @RequestMapping(value = "/user/userInfo", method = RequestMethod.GET)
  public String userInfo() {
    return "view/userInfoPage";
  }

  @RequestMapping(value = "/403", method = RequestMethod.GET)
  public String accessDenied(Model model, Principal principal) {
    if (principal != null) {
      User loginedUser = (User) ((Authentication) principal).getPrincipal();
      String userInfo = WebUtils.toString(loginedUser);
      model.addAttribute("userInfo", userInfo);
      String message =
          "Hi "
              + principal.getName() //
              + "<br> You do not have permission to access this page!";
      model.addAttribute("message", message);
    }
    return "/extra/403Page";
  }

  @GetMapping("/accout/sign-up")
  String signUp(Model model, @RequestParam(required = false) String exist) {
    if (exist != null && exist.equals("true")) {
      model.addAttribute("exist", "Đã tồn tại");
    }
    return "view/sign-up";
  }

  @PostMapping("/accout/sign-up")
  String signUp(Model model, @ModelAttribute App_User user) {
    try {
      App_Role role = new App_Role(1, ERole.ROLE_EMPLOYEE, null);
      Set<App_Role> roles = new HashSet<>();
      roles.add(role);
      System.out.println(user);
      user.setUSER_ID(-1);
      user.setPassword(encoder.encrytePassword(user.getPassword()));
      user.setEnabled(true);
      user.setRoles(roles);
      appUserRepository.saveAndFlush(user);
      return "redirect:/accout/login";
    } catch (Exception e) {
      System.out.println(e);
      return "redirect:/accout/sign-up?exist=true";
    }
  }

  @PostMapping("/accout/employee")
  String handleCreateEmployee(Model model, @ModelAttribute App_User user) {
    try {
      App_Role role = new App_Role(3, ERole.ROLE_EMPLOYEE, null);
      Set<App_Role> roles = new HashSet<>();
      roles.add(role);
      System.out.println(user);
      user.setUSER_ID(-1);
      user.setPassword(encoder.encrytePassword(user.getPassword()));
      user.setEnabled(true);
      user.setRoles(roles);
      appUserRepository.saveAndFlush(user);
      return "redirect:/admin";
    } catch (Exception e) {
      System.out.println(e);
      return "redirect:/accout/employee?exist=true";
    }
  }

  @RequestMapping(
          value = {"/log-out"},
          method = RequestMethod.GET)
  public String logOut(Model model) {
    return "view/welcomePage";
  }

  @RequestMapping(
          value = {"/forget-password"},
          method = RequestMethod.GET)
  public String forgetPassword(Model model) {
    return "view/forgetPassword";
  }

  @GetMapping("/accout/change-password")
  String getChangePassword(Model model, @RequestParam(required = false) String message) {
    if (message != null) {
      model.addAttribute("message", "Failed!");
    }
    return "view/changePassword";
  }

  @GetMapping("/accout/employee")
  String createEmployee(Model model, @RequestParam(required = false) String message) {
    if (message != null) {
      model.addAttribute("message", "Failed!");
    }
    return "view/createEmployee";
  }

  @PostMapping("/accout/change-password")
  String changePassword(Model model, @ModelAttribute NewPassword newPassword, Principal principal) {
    try {

      if (!newPassword.getNewPassword1().equals(newPassword.getNewPassword2())){
        return "redirect:/accout/change-password?message=failed";
      }
      App_User user = appUserRepository.findUserAccount(principal.getName());
      user.setPassword(encoder.encrytePassword(newPassword.getNewPassword1()));
      appUserRepository.saveAndFlush(user);
      return "redirect:/view/userInfoPage";
    } catch (Exception e) {
      System.out.println(e);
      return "view/changePassword";
    }
  }

  @PostMapping("/accout/forget-password")
  String handleForgetPassword(Model model, @ModelAttribute ForgetPassword email) {
    try {
      String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                      StringBuilder::appendCodePoint, StringBuilder::append)
              .toString();
      App_User user = appUserRepository.findUserByEmail(email.getEmail());
      user.setPassword(encoder.encrytePassword(password));
      appUserRepository.saveAndFlush(user);

      String from = emailFrom;
      String to = email.getEmail();
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(from);
      message.setTo(to);
      message.setSubject("This is new password");
      message.setText(password);
      mailSender.send(message);
      model.addAttribute("message", "Password sent to email");
      return "view/loginPage";

    } catch (Exception e) {
      System.out.println(e);
      return "view/forgetPassword";
    }
  }

  @GetMapping("/account/delete/{id}")
  String createEmployee(Model model, @PathVariable int id) {
    appUserRepository.deleteById(id);
    return "redirect:/admin";
  }
}
