package com.chensoul.lss.web.controller;

import com.chensoul.lss.service.RunAsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/runas")
class RunAsController {

  @Autowired
  private RunAsService runAsService;

  @RequestMapping
  @ResponseBody
  @Secured({"ROLE_USER", "RUN_AS_REPORTER"})
  public String tryRunAs() {
    return runAsService.getCurrentUser().getAuthorities().toString();
  }

}
