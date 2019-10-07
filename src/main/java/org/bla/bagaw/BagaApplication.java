package org.bla.bagaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

//@ComponentScan
@Controller
@SpringBootApplication
public class BagaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BagaApplication.class, args);
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/candle")
    public String candle(Model model) throws IOException {
        model.addAttribute("data", new Parser().getData());
        return "candle";
    }


}
