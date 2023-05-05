package com.sol.loan.service;

import com.sol.loan.domain.Counsel;
import com.sol.loan.dto.CounselDTO.Request;
import com.sol.loan.dto.CounselDTO.Response;
import com.sol.loan.exception.BaseException;
import com.sol.loan.exception.ResultType;
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

    @Override
    public Response get(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(counsel, Response.class);
    }

    @Override
    public Response update(Long counselId, Request request) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        counsel.setName(request.getName());
        counsel.setEmail(request.getEmail());
        counsel.setCellPhone(request.getCellPhone());
        counsel.setMemo(request.getMemo());
        counsel.setAddress(request.getAddress());
        counsel.setAddressDetail(request.getAddressDetail());
        counsel.setZipCode(request.getZipCode());

        counselRepository.save(counsel);

        return modelMapper.map(counsel, Response.class);
    }
}
