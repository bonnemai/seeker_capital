package com.bonnemai.viewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@Controller
public class AverageController {
    Logger logger=LoggerFactory.getLogger(AverageController.class);

    public static void main(String[] args) {
        SpringApplication.run(Viewer.class, args);
    }

    @GetMapping("/average")
    public String average(@RequestParam(name="nb", required=false, defaultValue="3") String nb,
                          Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Double result = null;
        try {
            String currentUrl = "http://localhost:8090/average";

            logger.info("Now calling URL: "+ currentUrl);
            Map<String, String> map=new HashMap<>();
            map.put("nb", nb);
//            result = restTemplate.getForObject(new URI(currentUrl), Double.class);
            result = restTemplate.getForObject(currentUrl, Double.class, map);
            if (result ==null){result=-1d;}
            logger.info("Got Results {}", result);
        } catch (Exception e) {
            logger.warn("Could not get Average from Average Service", e);
        }
        model.addAttribute("result", result);
        return "average";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }
}
