package com.example.farajaplatform.service;

import com.example.farajaplatform.model.Person;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    public Person mapData(String data){
        JsonMapper mapper = new JsonMapper();
        try{
            return mapper.readValue(data, Person.class);
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

}
