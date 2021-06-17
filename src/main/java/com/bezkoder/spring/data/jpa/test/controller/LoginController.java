package com.bezkoder.spring.data.jpa.test.controller;

import com.bezkoder.spring.data.jpa.test.model.entity.App_Role;
import com.bezkoder.spring.data.jpa.test.model.entity.App_User;
import com.bezkoder.spring.data.jpa.test.repository.AppUserRepository;
import com.bezkoder.spring.data.jpa.test.utils.EncrytedPasswordUtils;
import com.bezkoder.spring.data.jpa.test.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
  EncrytedPasswordUtils encoder = new EncrytedPasswordUtils();
  @Autowired private AppUserRepository appUserRepository;

  @RequestMapping(
      value = {"/"},
      method = RequestMethod.GET)
  public String welcomePage(Model model) {
    model.addAttribute("title", "Welcome");
    model.addAttribute("message", "This is welcome page!");
    return "view/welcomePage";
  }

  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  public String adminPage(Model model, Principal principal) {
    //    User loginedUser = (User) ((Authentication) principal).getPrincipal();
    //    String userInfo = WebUtils.toString(loginedUser);
    //    model.addAttribute("userInfo", userInfo);
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
  public String userInfo(
      @RequestParam(required = false) String message, Model model, Principal principal) {
    //    String userName = principal.getName();
    //    User loginedUser = (User) ((Authentication) principal).getPrincipal();
    //    String userInfo = WebUtils.toString(loginedUser);
    //    model.addAttribute("userInfo", userInfo);
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
      App_Role role = new App_Role(2, "ROLE_USER");
      Set<App_Role> roles = new HashSet<>();
      roles.add(role);
      user.setUSER_ID(-1);
      user.setPassword(encoder.encrytePassword(user.getPassword()));
      user.setEnabled(true);
      user.setRoles(roles);
      appUserRepository.saveAndFlush(user);
      return "redirect:/accout/login";
    } catch (Exception e) {
      return "redirect:/accout/sign-up?exist=true";
    }
  }
}
