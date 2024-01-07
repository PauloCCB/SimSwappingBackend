package com.simswapping.controller;

import com.simswapping.model.*;
import com.simswapping.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.simswapping.service.SimSwappingService;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("simswapping")
public class SimSwappingController {

    @Autowired
    private SimSwappingService simSwappingService;


    @ResponseBody
    @RequestMapping(value = "createOperation", method = RequestMethod.POST)
    public ResponseEntity<ResponseOperation> createOoperation(@RequestBody BodyOperation bodyOperation) throws IOException {
        ResponseOperation responseOperation = new ResponseOperation();
        try{

            //1. Obtenemos datos del usuario
            Usuario objUsuario =  simSwappingService.getDataUsuario(bodyOperation.getId_usuario());
            //2. Validamos radio de Geolocalización
            if(objUsuario != null) {
                if (Utils.isOnRadio(bodyOperation.getLatitud(), bodyOperation.getLongitud(),
                        objUsuario.getLatitude(), objUsuario.getLatitude())) {

                    //3. Mensaje de texto para confirmar operación

                    if (simSwappingService.createOperation(bodyOperation) == 1) {
                        responseOperation.setSuccess(true);
                        responseOperation.setMessage("Registro exitoso");
                    } else {
                        responseOperation.setSuccess(false);
                        responseOperation.setMessage("Error al crear la operación");
                    }
                } else {
                    responseOperation.setSuccess(false);
                    responseOperation.setMessage("Fuera del rango permitido -DatosBD:" + "Lat:"+objUsuario.getLatitude() + "Long:"+objUsuario.getLongitude() + " - BodyOperation: Lat:"+ bodyOperation.getLatitud() + " Long:"+bodyOperation.getLongitud());
                }
            }else {
                responseOperation.setSuccess(false);
                responseOperation.setMessage("Usuario no encontrado Id:"+bodyOperation.getId_usuario().toString());
            }

        } catch (Exception e){
            e.printStackTrace();
            responseOperation.setSuccess(false);
            responseOperation.setMessage(e.getMessage());
            return new ResponseEntity<>(responseOperation, HttpStatus.OK);

        }
        return new ResponseEntity<>(responseOperation, HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "validateLogin", method = RequestMethod.POST)
    public ResponseEntity<ResponseAccount> login(@RequestBody BodyLogin bodyLogin) throws IOException {
        ResponseAccount responseAccount = new ResponseAccount();

        Usuario objUsuario = null;
        Cuenta objCuenta = null;
        try {
            List<Usuario> lstUsuario = simSwappingService.login(bodyLogin);
            if(lstUsuario != null && lstUsuario.size() > 0){
                objUsuario = lstUsuario.get(0);
                objCuenta = simSwappingService.getCuentaByUsuario(objUsuario.getId_usuario());
                if(objCuenta!= null) {
                    responseAccount.setCuenta(objCuenta);
                }
                if(objUsuario!=null) {
                    responseAccount.setUsuario(objUsuario);

                    if (Utils.isOnRadio(bodyLogin.getLatitude(), bodyLogin.getLongitude(), objUsuario.getLatitude(), objUsuario.getLatitude())) {
                        responseAccount.setSuccess(false);//Está dentro del radio y debería fallar
                        responseAccount.setMessage("Cuenta fuera de la ubicación permitida, su cuenta acaba de ser bloqueada");
                    } else {

                        //Actualizamos la nueva ubicación
                        Integer resultado = simSwappingService.updateUserLocation(objUsuario.getId_usuario(), objUsuario.getLatitude(),objUsuario.getLongitude());
                        if(resultado == 1) {
                            responseAccount.setSuccess(true);
                            responseAccount.setMessage("Inicio de sesión con éxito.");
                        }else {
                            responseAccount.setSuccess(false);
                            responseAccount.setMessage("Error al actualizar ubicación.");
                        }
                    }
                }
            } else {
                responseAccount.setSuccess(false);
                responseAccount.setMessage("Usuario no válido");
            }

        } catch (Exception e) {
            responseAccount.setSuccess(false);
            responseAccount.setMessage(e.getMessage()+ " " + e.getCause());
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

    @ResponseBody
    @RequestMapping(value = "getLocations", method = RequestMethod.GET)
    public ResponseEntity<ResponseUbicaciones> obtenerUbicaciones(@RequestParam("idUsuario") Integer idUsuario) throws IOException {
        ResponseUbicaciones responseUbicaciones = new ResponseUbicaciones();
        try{
            List<Ubicaciones> lstUbicaciones = simSwappingService.getLocations(idUsuario);
            responseUbicaciones.setLstUbicaciones(lstUbicaciones);
            responseUbicaciones.setMessage("Exito");
                //return ResponseEntity.ok("Ocurrió un inconveniente, por favor vuelva a intentarlo luego");

        }catch (Exception e) {
            responseUbicaciones.setMessage(e.getMessage());

        }
        return new ResponseEntity<>(responseUbicaciones, HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "registerLocation", method = RequestMethod.POST)
        public ResponseEntity<ResponseUbicaciones> registerLocation(@RequestBody Ubicaciones bodyUbicacion) throws IOException {
        ResponseUbicaciones responseUbicaciones = new ResponseUbicaciones();

        Integer resultado = 0;
        try {
            resultado = simSwappingService.registerLocation(
                    bodyUbicacion.getId_usuario(),
                    Double.parseDouble(bodyUbicacion.getLatitud()),
                    Double.parseDouble(bodyUbicacion.getLongitud()));

            if (resultado == 1) {
                responseUbicaciones.setSuccess(true);
                responseUbicaciones.setMessage("Registro de ubicación con éxito.");
            } else if (resultado == 0){
                responseUbicaciones.setSuccess(true);
                responseUbicaciones.setMessage("Ubicación eliminada.");
            }else {
                responseUbicaciones.setSuccess(false);
                responseUbicaciones.setMessage("Ubicación errada.");
            }
        } catch (Exception e) {
            responseUbicaciones.setSuccess(false);
            responseUbicaciones.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseUbicaciones, HttpStatus.OK);
    }
}
