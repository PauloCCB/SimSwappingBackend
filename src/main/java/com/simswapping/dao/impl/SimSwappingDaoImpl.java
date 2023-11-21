package com.simswapping.dao.impl;

import com.simswapping.dao.SimSwappingDao;
import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseLogin;
import com.simswapping.model.Usuario;
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
                new SqlParameter("in_cc", Types.VARCHAR),
                new SqlParameter("in_latitude", Types.VARCHAR),
                new SqlParameter("in_longitude", Types.VARCHAR),
                new SqlParameter("in_imei", Types.VARCHAR),
                new SqlOutParameter("resultado", Types.INTEGER));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_nombre", bodyAccount.getNombre());
        inParamMap.put("in_apellido", bodyAccount.getApellido());
        inParamMap.put("in_cc", bodyAccount.getCc());
        inParamMap.put("in_latitude", bodyAccount.getLatitude());
        inParamMap.put("in_longitude", bodyAccount.getLongitude());
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

        jdbcCall.returningResultSet("RESULT_SET", BeanPropertyRowMapper.newInstance(Usuario.class));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_acc", bodyLogin.getAcc());
        inParamMap.put("in_pin", bodyLogin.getPin());
        inParamMap.put("in_imei", bodyLogin.getImei());

        Map<String, Object> result = jdbcCall.execute(inParamMap);

        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        List<Usuario> usuarios = (List<Usuario>) result.get("RESULT_SET");
        //result = jdbcCall.executeObject(Integer.class, in);

        return usuarios;
    }


}
