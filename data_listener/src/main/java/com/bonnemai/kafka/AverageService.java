package com.bonnemai.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AverageService {
    static Logger logger = LoggerFactory.getLogger(Listener.class);

    @RequestMapping(value = "/average", method = RequestMethod.GET)
    public Double average(@RequestParam(value="avg", defaultValue="3") String nb) {
        logger.info("Computing the Average");
        List<Double> theList=new ArrayList<>();
        Integer nbInt=Integer.parseInt(nb);
        Double sum=0d;

        if (nbInt > 0) {
            if (theList.size()>=nbInt) {
                for (int i = theList.size() - nbInt; i < theList.size(); i++) {
                    sum += theList.get(i);
                }
            } else {
                logger.warn("Not enough values in the list. Please come back later or use a smaller nb.");
//                throw new Exception("Not enough values in the list. Please come back later or use a smaller nb.");
            }
        } else {
                logger.warn("nb Should be positive");
//                throw new Exception("nb Should be positive");
        }

        return sum/nbInt;
    }


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        logger.info("Error");
        return "Error";
    }
}
