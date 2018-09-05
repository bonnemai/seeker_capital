package com.bonnemai.viewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@Controller
public class Viewer {
    Logger logger=LoggerFactory.getLogger(Viewer.class);

    public static void main(String[] args) {
        SpringApplication.run(Viewer.class, args);
    }

    @GetMapping("/average")
    public String average(@RequestParam(name="nb", required=false, defaultValue="3") String nb,
                          Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Double result = null;
        try {
            String currentUrl = "http://localhost:8090/average?nb="+nb;

            logger.info("Now calling URL: "+ currentUrl);
            Map<String, String> map=new HashMap<>();
            result = restTemplate.getForObject(currentUrl, Double.class, map);
            if (result ==null){result=-1d;}
            logger.info("Got Results {}", result);
        } catch (Exception e) {
            logger.warn("Could not get Average from Average Service", e);
        }
        model.addAttribute("result", result);
        model.addAttribute("nb", nb);
        return "average";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        logger.info("Index Page");
        return "Hi There";
    }
}
