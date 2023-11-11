package com.simswapping.service;


import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseLogin;

public interface SimSwappingService {

    Integer registerAccount(BodyAccount bodyAccount);

    Integer login(BodyLogin bodyLogin);
}
