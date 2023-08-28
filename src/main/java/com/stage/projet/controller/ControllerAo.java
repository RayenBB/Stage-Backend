package com.stage.projet.controller;

import com.stage.projet.models.Appeldoffre;
import com.stage.projet.services.ServiceAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/ao")
@CrossOrigin("*")

public class ControllerAo {
    @Autowired
    private ServiceAO serviceAO;
   
    
    @GetMapping("/all")
    public ResponseEntity<List<Appeldoffre>> getAllAppeldoffres () {
        List<Appeldoffre> Appeldoffres = serviceAO.findAllAppeldoffres();
        return new ResponseEntity<>(Appeldoffres, HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Appeldoffre> getAppeldoffreById (@PathVariable("id") Integer id) {
        Appeldoffre Appeldoffre = serviceAO.findAppeldoffreById(id);
        return new ResponseEntity<>(Appeldoffre, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<Appeldoffre> addAppeldoffre(@RequestBody Appeldoffre Appeldoffre) {
        Appeldoffre newAppeldoffre = serviceAO.addAppeldoffre(Appeldoffre);
        return new ResponseEntity<>(newAppeldoffre, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Appeldoffre> updateAppeldoffre(@RequestBody Appeldoffre Appeldoffre) {
        Appeldoffre updateAppeldoffre = serviceAO.updateAppeldoffre(Appeldoffre);
        return new ResponseEntity<>(updateAppeldoffre, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppeldoffre(@PathVariable("id") Integer id) {
        serviceAO.deleteAppeldoffre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
