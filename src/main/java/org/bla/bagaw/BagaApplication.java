package org.bla.bagaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        //model.addAttribute("data", new Parser().getData("APPL"));
        return "candle";
    }

    @ResponseBody
    @GetMapping(path = "/chart/{symbol}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getChartData(@PathVariable String symbol) throws IOException {
        System.out.println("Parsing data...");
        return new Parser(symbol).getCandleData();
    }

}
