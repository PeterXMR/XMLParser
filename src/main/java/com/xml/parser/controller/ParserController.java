package com.xml.parser.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.parser.service.ParseService;

@RestController
public class ParserController {

    private final ParseService parseService;

    public ParserController(ParseService parseService) {
        this.parseService = parseService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> parseData() {
        System.out.println(parseService.loadAndSaveMunicipalityDTO());
        return ResponseEntity.ok().body("Test was successfull.");
    }
}
