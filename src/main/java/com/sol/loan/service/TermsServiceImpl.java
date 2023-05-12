package com.sol.loan.service;

import com.sol.loan.domain.Terms;
import com.sol.loan.dto.TermsDTO;
import com.sol.loan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sol.loan.dto.TermsDTO.*;

@Service
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Terms terms = modelMapper.map(request, Terms.class);
        Terms created = termsRepository.save(terms);

        return modelMapper.map(created, Response.class);
    }

    @Override
    public List<Response> getAll() {
        List<Terms> termsList = termsRepository.findAll();

        return termsList.stream().map(t -> modelMapper.map(t, Response.class)).collect(Collectors.toList());
    }
}
