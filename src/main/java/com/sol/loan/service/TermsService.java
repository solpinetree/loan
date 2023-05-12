package com.sol.loan.service;


import com.sol.loan.dto.TermsDTO.Request;
import com.sol.loan.dto.TermsDTO.Response;

import java.util.List;

public interface TermsService {

    Response create(Request request);

    List<Response> getAll();
}
