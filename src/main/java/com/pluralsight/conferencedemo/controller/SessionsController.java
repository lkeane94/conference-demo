package com.pluralsight.conferencedemo.controller;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")

public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

//GET ALL
    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
    }

    //GET ALL by ID
    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getOne(id);
    }
//CREATE NEW SESSION
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        return  sessionRepository.saveAndFlush(session);
        //save and flush writes to repository and the database
    }


  //DELETE A SESSION
  @RequestMapping(value = "{id}" , method = RequestMethod.DELETE)
  public void delete(@PathVariable Long id){
        //Also need to check for children records before deleting
      sessionRepository.deleteById(id);
  }

  @RequestMapping(value = "{id}" , method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session){
        //because this is put , all attributes needs to be passed in
      //add validation so that all attributes are passed in , else return a 400 bad payload
      Session existingSession = sessionRepository.getOne(id);
      BeanUtils.copyProperties(session, existingSession,"session_id");
      return sessionRepository.saveAndFlush(existingSession);

  }








}
