package com.bezkoder.spring.data.jpa.test.config.security;

import com.bezkoder.spring.data.jpa.test.model.entity.App_Role;
import com.bezkoder.spring.data.jpa.test.model.entity.App_User;
import com.bezkoder.spring.data.jpa.test.repository.AppUserRepository;
import com.bezkoder.spring.data.jpa.test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private AppUserRepository appUserRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    App_User user = this.appUserRepository.findUserAccount(userName);

    if (user == null) {
      System.out.println("User not found! " + userName);
      throw new UsernameNotFoundException("User " + userName + " was not found in the database");
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    Set<App_Role> roles = user.getRoles();
    for (App_Role role : roles) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
    }

    return new org.springframework.security.core.userdetails.User(
            user.getUserName(), user.getPassword(), grantedAuthorities);


    // [ROLE_USER, ROLE_ADMIN,..]
//    List<Integer> roleId = this.userRoleRepository.getRoleByIdUser(user.getUserId());
//    List<String> roleNames = new ArrayList<>();
//    List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//    if (roleId != null) {
//      for (Integer role : roleId) {
//        // ROLE_USER, ROLE_ADMIN,..
//        roleNames.add(roleRepository.getRoleNames(role.longValue()));
//      }
//      for (String roleName : roleNames) {
//        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
//        grantList.add(authority);
//      }
//    }
//    UserDetails userDetails =
//        (UserDetails)
//            new org.springframework.security.core.userdetails.User(
//                user.getUserName(), user.getEncrytedPassword(), grantList);
//    return userDetails;
  }
}
