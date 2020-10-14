package com.sachin.montyhall.service.impl;

import com.sachin.montyhall.resources.interfaces.PlayApi;
import com.sachin.montyhall.service.interfaces.IMontyHallProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayApiImpl implements PlayApi {
    
    @Autowired
    IMontyHallProblemService service;

    @Override
    public ResponseEntity<String> solveMontyHallProblem(Integer iterations, Boolean switched) {
        String returnString = service.getSolutionResults(iterations, switched);
        return new ResponseEntity<>(returnString, HttpStatus.OK);
    }
}
