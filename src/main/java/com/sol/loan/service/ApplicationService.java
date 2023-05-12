package com.sol.loan.service;

import com.sol.loan.dto.ApplicationDTO.*;

public interface ApplicationService {

    Response create(Request request);

    Response get(Long applicationId);

    Response update(Long applicationId, Request request);

    void delete(Long applicationId);

    Boolean acceptTerms(Long applicationId, AcceptTerms dto);
}
