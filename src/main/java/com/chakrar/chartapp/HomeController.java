package com.chakrar.chartapp;

import com.chakrar.chartapp.model.ThermoSensor;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(value="/", method= RequestMethod.GET)
    String index() {
        return "index";
    }
	
	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }	
	
	@RequestMapping("/showchart")
	public String showchart(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "showchart";
    }
	
	@RequestMapping("/showlinechart")
	public String showLineChart(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "showlinechart";
    }


    @PostMapping("/sensors/thermo")
    public ResponseEntity<String> addClient(@RequestBody ThermoSensor sensor) {

        this.simpMessagingTemplate.convertAndSend("/topic/airquality/temperature", ""+sensor.getValue() );
        this.simpMessagingTemplate.convertAndSend("/topic/airquality/windspeed", ""+sensor.getValue() );
        return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.TEXT_PLAIN).body("success");

    }
}
