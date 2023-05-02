package com.sol.loan.service;

import com.sol.loan.domain.Counsel;
import com.sol.loan.dto.CounselDTO;
import com.sol.loan.dto.CounselDTO.Request;
import com.sol.loan.dto.CounselDTO.Response;
import com.sol.loan.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService {

    private final CounselRepository counselRepository;

    private final ModelMapper modelMapper; // converting 용이하게 해줌

    @Override
    public Response create(Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());

        Counsel created = counselRepository.save(counsel);

        return modelMapper.map(created, Response.class);
    }
}