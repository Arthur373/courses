package am.aca.courses.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = {"","/"})
public class RootController {
  @RequestMapping(method = RequestMethod.GET)
  public String swaggerUi() {
    return "redirect:/swagger-ui.html";
  }
}