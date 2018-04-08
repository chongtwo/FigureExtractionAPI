package com.springboot.two.controller;

import com.springboot.two.service.FigureExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class FigureExtractController {

    @GetMapping(value = "/nlp/{sentence}")
    public ArrayList<ArrayList<String>> process(@PathVariable("sentence") String aLongSentence){
        FigureExtraction figureExtraction = new FigureExtraction();
        ArrayList<String> longSentenceList = new ArrayList<String>();
        longSentenceList.add(aLongSentence);
        ArrayList<ArrayList<String>> allList = figureExtraction.go(longSentenceList);
        return allList;
    }
}
