package com.simswapping.dao.impl;

import com.simswapping.dao.SimSwappingDao;
import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

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
                new SqlOutParameter("in_latitude", Types.VARCHAR),
                new SqlOutParameter("in_longitude", Types.VARCHAR),
                new SqlOutParameter("in_imei", Types.VARCHAR));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_nombre", bodyAccount.getNombre());
        inParamMap.put("in_apellido", bodyAccount.getApellido());
        inParamMap.put("in_cc", bodyAccount.getCc());
        inParamMap.put("in_latitude", bodyAccount.getLatitude());
        inParamMap.put("in_longitude", bodyAccount.getLongitude());
        inParamMap.put("in_imei", bodyAccount.getImei());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        result = jdbcCall.executeObject(Integer.class, in);
        return result;
    }

    @Override
    public Integer login(BodyLogin bodyLogin) {
        Integer result = 0;
        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        jdbcCall.withSchemaName("sch_simulator");
        jdbcCall.withProcedureName("sp_validate_login");
        jdbcCall.withoutProcedureColumnMetaDataAccess();
        jdbcCall.declareParameters(
                new SqlParameter("in_acc", Types.VARCHAR),
                new SqlParameter("in_pin", Types.VARCHAR),
                new SqlParameter("in_latitude", Types.VARCHAR),
                new SqlOutParameter("in_longitude", Types.VARCHAR));
        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_acc", bodyLogin.getAcc());
        inParamMap.put("in_pin", bodyLogin.getPin());
        inParamMap.put("in_latitude", bodyLogin.getLatitude());
        inParamMap.put("in_longitude", bodyLogin.getLongitude());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        result = jdbcCall.executeObject(Integer.class, in);
        return result;
    }
}
