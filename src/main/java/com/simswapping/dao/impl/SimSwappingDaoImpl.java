package com.simswapping.dao.impl;

import com.simswapping.dao.SimSwappingDao;
import com.simswapping.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value = "SimSwappingDao")
public class SimSwappingDaoImpl implements SimSwappingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall jdbcCall;


    @Override
    public Integer registerAccount(BodyAccount bodyAccount) {
        Integer result = 0;
        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_register_account");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_nombre", Types.VARCHAR),
                new SqlParameter("in_apellido", Types.VARCHAR),
                new SqlParameter("in_dni", Types.VARCHAR),
                new SqlParameter("in_pin", Types.VARCHAR),
                new SqlParameter("in_latitude", Types.VARCHAR),
                new SqlParameter("in_longitude", Types.VARCHAR),
                new SqlParameter("in_cc", Types.VARCHAR),
                new SqlParameter("in_imei", Types.VARCHAR),
                new SqlOutParameter("resultado", Types.INTEGER));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_nombre", bodyAccount.getNombre());
        inParamMap.put("in_apellido", bodyAccount.getApellido());
        inParamMap.put("in_dni", bodyAccount.getDni());
        inParamMap.put("in_pin", bodyAccount.getPin());
        inParamMap.put("in_latitude", bodyAccount.getLatitude());
        inParamMap.put("in_longitude", bodyAccount.getLongitude());
        inParamMap.put("in_cc", bodyAccount.getCc());
        inParamMap.put("in_imei", bodyAccount.getImei());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        //jdbcCall.returningResultSet("resultado", cuentaDaoDefinition);
        //return  (Cuenta) jdbcCall.executeObject(List.class, in).get(0);

        result = jdbcCall.executeObject(Integer.class, in);
        return result;
    }

    @Override
    public List<Usuario> login(BodyLogin bodyLogin) throws Exception {
        //Integer result = 0;

        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_validate_login");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_acc", Types.VARCHAR),
                new SqlParameter("in_pin", Types.VARCHAR),
                new SqlParameter("in_imei", Types.VARCHAR));

        jdbcCall.returningResultSet("RESULT_SET",
                BeanPropertyRowMapper.newInstance(Usuario.class));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_acc", bodyLogin.getCc());
        inParamMap.put("in_pin", bodyLogin.getPin());
        inParamMap.put("in_imei", bodyLogin.getImei());

        Map<String, Object> result = jdbcCall.execute(inParamMap);

        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        List<Usuario> usuarios = (List<Usuario>) result.get("RESULT_SET");
        //result = jdbcCall.executeObject(Integer.class, in);

        return usuarios;
    }

    @Override
    public Integer createOperation(BodyOperation bodyOperation) throws Exception {

        Integer result = 0;
        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_create_operation");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_id_usuario", Types.VARCHAR),
                new SqlParameter("in_monto", Types.DECIMAL),
                new SqlParameter("in_cuenta_destino", Types.VARCHAR),
                new SqlParameter("in_cuenta_origen", Types.VARCHAR),
                new SqlOutParameter("resultado", Types.INTEGER));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_id_usuario", bodyOperation.getId_usuario());
        inParamMap.put("in_monto", bodyOperation.getMonto());
        inParamMap.put("in_cuenta_destino", bodyOperation.getCuenta_destino());
        inParamMap.put("in_cuenta_origen", bodyOperation.getCuenta_origen());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        //jdbcCall.returningResultSet("resultado", cuentaDaoDefinition);
        //return  (Cuenta) jdbcCall.executeObject(List.class, in).get(0);

        result = jdbcCall.executeObject(Integer.class, in);
        return result;

    }


    @Override
    public Usuario getDataUsuario(Integer idUsuario) throws Exception {
        try{
            jdbcCall = new SimpleJdbcCall(jdbcTemplate);
            jdbcCall.withSchemaName("sch_simulator");
            jdbcCall.withProcedureName("sp_list_usuario_by_id");
            jdbcCall.withoutProcedureColumnMetaDataAccess();
            jdbcCall.declareParameters(
                    new SqlParameter("in_id_usuario", Types.INTEGER));
            jdbcCall.returningResultSet("RESULT_SET", BeanPropertyRowMapper.newInstance(Usuario.class));
            Map<String, Object> inParamMap = new HashMap<String, Object>();
            inParamMap.put("in_id_usuario", idUsuario);

            Map<String, Object> result = jdbcCall.execute(inParamMap);

            SqlParameterSource in = new MapSqlParameterSource(inParamMap);
            List<Usuario> lstUsuarios = (List<Usuario>) result.get("RESULT_SET");
            if(lstUsuarios != null && lstUsuarios.size() > 0){
                return lstUsuarios.get(0);
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Cuenta getCuentaByUsuario(Integer idUsuario) throws Exception {
        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_list_cuenta_by_id");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_id_usuario", Types.INTEGER));
        jdbcCall.returningResultSet("RESULT_SET",
                BeanPropertyRowMapper.newInstance(Cuenta.class));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_id_usuario", idUsuario);

        Map<String, Object> result = jdbcCall.execute(inParamMap);

        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        List<Cuenta> lstCuenta = (List<Cuenta>) result.get("RESULT_SET");
        if(lstCuenta != null && lstCuenta.size() > 0){
            return lstCuenta.get(0);
        }else {
            return null;
        }

    }


    @Override
    public Integer updateUserLocation(Integer idUsuario, double latitud, double longitud) throws Exception {
        Integer result = 0;
        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_update_user_location");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_id_usuario", Types.INTEGER),
                new SqlParameter("in_latitud", Types.DOUBLE),
                new SqlParameter("in_longitud", Types.DOUBLE),
                new SqlOutParameter("resultado", Types.INTEGER));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_id_usuario", idUsuario);
        inParamMap.put("in_latitud", latitud);
        inParamMap.put("in_longitud", longitud);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        //jdbcCall.returningResultSet("resultado", cuentaDaoDefinition);
        //return  (Cuenta) jdbcCall.executeObject(List.class, in).get(0);

        result = jdbcCall.executeObject(Integer.class, in);
        return result;

    }

    @Override
    public List<Ubicaciones> getLocations(Integer idUsuario) throws Exception {
        //Integer result = 0;

        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_list_ubicaciones_by_usuario");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_id_usuario", Types.INTEGER));

        jdbcCall.returningResultSet("RESULT_SET",
                BeanPropertyRowMapper.newInstance(Ubicaciones.class));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_id_usuario", idUsuario);

        Map<String, Object> result = jdbcCall.execute(inParamMap);

        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        List<Ubicaciones> lstUbicaciones = (List<Ubicaciones>) result.get("RESULT_SET");

        return lstUbicaciones;
    }


    @Override
    public Integer registerLocation(Integer idUsuario, double latitud, double longitud) throws Exception {
        Integer result = 0;
        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_register_location");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_id_usuario", Types.INTEGER),
                new SqlParameter("in_latitud", Types.DOUBLE),
                new SqlParameter("in_longitud", Types.DOUBLE),
                new SqlOutParameter("resultado", Types.INTEGER));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_id_usuario", idUsuario);
        inParamMap.put("in_latitud", latitud);
        inParamMap.put("in_longitud", longitud);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        //jdbcCall.returningResultSet("resultado", cuentaDaoDefinition);
        //return  (Cuenta) jdbcCall.executeObject(List.class, in).get(0);

        result = jdbcCall.executeObject(Integer.class, in);
        return result;

    }


}
