package com.bonnemai.viewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class AverageController {
    Logger logger=LoggerFactory.getLogger(AverageController.class);
    @GetMapping("/average")
    public String average(@RequestParam(name="nb", required=false, defaultValue="3") String nb,
                          Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Double result = null;
        try {
            result = restTemplate.getForObject(new URI("http://localhost:8090/average/nb="+nb), Double.class);
        } catch (URISyntaxException e) {
            logger.warn("Could not get Average from Average Service", e);
        }
        model.addAttribute("result", result);
        return "average";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }
}
