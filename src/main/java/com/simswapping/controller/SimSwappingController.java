package com.simswapping.controller;

import com.simswapping.model.BodyAccount;
import com.simswapping.model.ResponseAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.simswapping.service.RegisterService;

import java.io.IOException;

@RestController
@RequestMapping("simswapping")
public class SimSwappingController {

    @Autowired
    private RegisterService registerService;

    @ResponseBody
    @RequestMapping(value = "account", method = RequestMethod.POST)
    public ResponseAccount solicitudRestaurar(@RequestBody BodyAccount bodyAccount) throws IOException {
        ResponseAccount responseAccount = new ResponseAccount();
        Integer result = registerService.registerAccount(bodyAccount);
        responseAccount.setSuccess(result == 0);
        return responseAccount;

    }
}
