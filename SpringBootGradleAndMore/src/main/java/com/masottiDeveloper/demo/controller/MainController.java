package com.masottiDeveloper.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController
{

    @GetMapping(path = "/hello", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sayHello()
    {
         //Get data from service layer into entityList.

        List<JSONObject> entities = new ArrayList<JSONObject>();
//        for (Entity n : entityList) {
            JSONObject entity = new JSONObject();
            entity.put("aa", "bbnnnn");
            entities.add(entity);
//        }
        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }
}
