package com.simswapping.controller;

import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseAccount;
import com.simswapping.model.Usuario;
import com.simswapping.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.simswapping.service.SimSwappingService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("simswapping")
public class SimSwappingController {

    @Autowired
    private SimSwappingService simSwappingService;

    @ResponseBody
    @RequestMapping(value = "register_account", method = RequestMethod.POST)
    public Integer registerAccount(@RequestBody BodyAccount bodyAccount) throws IOException {
        return simSwappingService.registerAccount(bodyAccount);
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<ResponseAccount> login(@RequestBody BodyLogin bodyLogin) throws IOException {
        ResponseAccount responseAccount = new ResponseAccount();

        Usuario objUsuario = null;
        try {
            List<Usuario> lstUsuario = simSwappingService.login(bodyLogin);
            if(lstUsuario != null){
                if(lstUsuario.size() > 0){
                    objUsuario = lstUsuario.get(0);
                }

                if(objUsuario!=null) {
                    responseAccount.setUsuario(objUsuario);


                    if (Utils.isOnRadio(bodyLogin.getLatitude(), bodyLogin.getLongitude(), objUsuario.getLatitude(), objUsuario.getLatitude())) {
                        responseAccount.setSuccess(true); //Está dentro del radio
                    } else {
                        responseAccount.setSuccess(false);
                    }
                }
            }else {
                responseAccount.setSuccess(false);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(responseAccount, HttpStatus.OK);

    }

}
