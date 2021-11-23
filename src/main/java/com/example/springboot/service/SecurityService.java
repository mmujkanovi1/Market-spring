package com.example.springboot.service;

import com.example.springboot.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

  private static final int TOKEN_SUBSTRING = 7;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;


  public boolean hasId(final HttpServletRequest request, final Long id) {
    if (jwtTokenUtil.getIdFromToken(getTokenFromRequest(request)).equals(id.toString())) {
      return true;
    }
    return false;
  }

  public Long getIdFromSecurityService(final HttpServletRequest request) {
    return Long.valueOf(jwtTokenUtil.getIdFromToken(getTokenFromRequest(request)));
  }

  public Long getIdFromToken(final String jwt){
    return Long.valueOf(jwtTokenUtil.getIdFromToken(jwt));
  }

  public boolean hasRole(final HttpServletRequest request, final String role) {
    if (jwtTokenUtil.getRoleFromToken(getTokenFromRequest(request)).equals(role)) {
      return true;
    }
    return false;
  }

  public boolean hasIdOrRole(final HttpServletRequest request, final String role, final Long id) {
    if (jwtTokenUtil.getIdFromToken(getTokenFromRequest(request)).equals(id.toString()) || jwtTokenUtil.getRoleFromToken(getTokenFromRequest(request)).equals(role)) {
      return true;
    }
    return false;

  }

  private String getTokenFromRequest(final HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    return token.substring(TOKEN_SUBSTRING);
  }

}
