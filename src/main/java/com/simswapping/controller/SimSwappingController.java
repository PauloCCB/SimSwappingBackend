package com.simswapping.controller;

import com.simswapping.model.*;
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
    @RequestMapping(value = "createOperation", method = RequestMethod.POST)
    public ResponseEntity createOoperation(@RequestBody BodyOperation bodyOperation) throws IOException {
        try{

            //1. Validamos Huella digital

            //2. Validamos radio de Geolocalización
                //Obtenemos datos del usuario
            Usuario objUsuario =  simSwappingService.getDataUsuario(bodyOperation.getId_usuario());

            if (Utils.isOnRadio(bodyOperation.getLatitud(), bodyOperation.getLongitud(),
                    objUsuario.getLatitude(), objUsuario.getLatitude())) {

            }
            //3. Mensaje de texto para confirmar operación


            if(simSwappingService.createOperation(bodyOperation) == 1){
                return ResponseEntity.ok("Registro exitoso");
            }else {
                return ResponseEntity.ok("Error al crear la operación");
            }

        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }


    @ResponseBody
    @RequestMapping(value = "validateLogin", method = RequestMethod.POST)
    public ResponseEntity<ResponseAccount> login(@RequestBody BodyLogin bodyLogin) throws IOException {
        ResponseAccount responseAccount = new ResponseAccount();

        Usuario objUsuario = null;
        try {
            List<Usuario> lstUsuario = simSwappingService.login(bodyLogin);
            if(lstUsuario != null && lstUsuario.size() > 0){
                objUsuario = lstUsuario.get(0);

                if(objUsuario!=null) {
                    responseAccount.setUsuario(objUsuario);

                    if (Utils.isOnRadio(bodyLogin.getLatitude(), bodyLogin.getLongitude(), objUsuario.getLatitude(), objUsuario.getLatitude())) {
                        responseAccount.setSuccess(false);//Está dentro del radio y debería fallar
                        responseAccount.setMessage("Cuenta fuera de la ubicación permitida, su cuenta acaba de ser bloqueada");
                    } else {
                        responseAccount.setSuccess(true);
                        responseAccount.setMessage("Inicio de sesión con éxito.");
                    }
                }
            } else {
                responseAccount.setSuccess(false);
                responseAccount.setMessage("Usuario no válido");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(responseAccount, HttpStatus.OK);

    }


    @ResponseBody
    @RequestMapping(value = "registerAccount", method = RequestMethod.POST)
    public ResponseEntity<ResponseAccount> registerAccount(@RequestBody BodyAccount bodyAccount) throws IOException {
        ResponseAccount responseAccount = new ResponseAccount();
        try {
            if (simSwappingService.registerAccount(bodyAccount) == 1) {
                responseAccount.setMessage("Registro exitoso");
                //return ResponseEntity.ok("Registro exitoso");
            } else {
                responseAccount.setMessage("Ocurrió un inconveniente, por favor vuelva a intentarlo luego");
                //return ResponseEntity.ok("Ocurrió un inconveniente, por favor vuelva a intentarlo luego");
            }

        }catch (Exception e) {
            responseAccount.setMessage(e.getMessage());

        }
        return new ResponseEntity<>(responseAccount, HttpStatus.OK);
    }

}
