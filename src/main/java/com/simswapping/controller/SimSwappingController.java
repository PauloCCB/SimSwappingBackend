package com.simswapping.controller;

import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.simswapping.service.SimSwappingService;

import java.io.IOException;

@RestController
@RequestMapping("simswapping")
public class SimSwappingController {

    @Autowired
    private SimSwappingService simSwappingService;

    @ResponseBody
    @RequestMapping(value = "account", method = RequestMethod.POST)
    public Integer solicitudRestaurar(@RequestBody BodyAccount bodyAccount) throws IOException {
        return simSwappingService.registerAccount(bodyAccount);
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseAccount login(@RequestBody BodyLogin bodyLogin) throws IOException {
        ResponseAccount responseAccount = new ResponseAccount();

        simSwappingService.login(bodyLogin);
        return responseAccount;

    }



}
