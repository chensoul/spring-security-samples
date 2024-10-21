package com.chensoul.security.web.controller;

import com.chensoul.security.persistence.UserRepository;
import com.chensoul.security.web.model.User;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VerificationCodeController {

  public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
  public static String APP_NAME = "LearnSpringSecurity";

  @Autowired
  private UserRepository userRepository;

  @RequestMapping(value = "/code", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, String> getQRUrl(@RequestParam("username") final String username) throws UnsupportedEncodingException {
    final Map<String, String> result = new HashMap<String, String>();
    final User user = userRepository.findByUsername(username);
    if (user == null) {
      result.put("url", "");
    } else {
      result.put("url", generateQRUrl(user.getSecret(), user.getUsername()));
    }
    return result;
  }

  private String generateQRUrl(String secret, String username) throws UnsupportedEncodingException {
    return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, username, secret, APP_NAME), "UTF-8");
  }
}
