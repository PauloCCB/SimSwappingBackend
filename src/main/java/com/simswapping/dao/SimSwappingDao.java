package com.simswapping.dao;

import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseLogin;

public interface SimSwappingDao {

    Integer registerAccount(BodyAccount bodyAccount);

    Integer login(BodyLogin bodyLogin);
}
