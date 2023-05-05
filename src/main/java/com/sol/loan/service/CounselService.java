package com.sol.loan.service;

import com.sol.loan.dto.CounselDTO.Request;
import com.sol.loan.dto.CounselDTO.Response;

public interface CounselService {

    Response create(Request request);

    Response get(Long counselId);

    Response update(Long counselId, Request request);
}
