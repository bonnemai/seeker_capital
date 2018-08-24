package com.bonnemai.viewer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AverageController {

    @GetMapping("/average")
    public String average(@RequestParam(name="name", required=false, defaultValue="World") String name,
                          @RequestParam(name="nb", required=false, defaultValue="3") String nb,
                          @RequestParam(name="nbs", required=false, defaultValue="World") String nbs, Model model) {

        String result=nb;
        model.addAttribute("result", result);
        model.addAttribute("name", name);
        model.addAttribute("nb", nb);
        model.addAttribute("nbs", nbs);
        return "average";
    }
}
