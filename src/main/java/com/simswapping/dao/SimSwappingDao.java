package com.simswapping.dao;

import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseLogin;
import com.simswapping.model.Usuario;

import java.util.List;

public interface SimSwappingDao {

    Integer registerAccount(BodyAccount bodyAccount);

    List<Usuario> login(BodyLogin bodyLogin) throws Exception;
}
