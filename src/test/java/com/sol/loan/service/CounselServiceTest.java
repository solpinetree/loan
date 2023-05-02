package com.sol.loan.service;

import com.sol.loan.domain.Counsel;
import com.sol.loan.dto.CounselDTO.Request;
import com.sol.loan.dto.CounselDTO.Response;
import com.sol.loan.repository.CounselRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("비즈니스 로직 테스트 - 대출 상담 기능")
@ExtendWith(MockitoExtension.class)
class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper; // util 성이기 때문에 @Spy로

    @DisplayName("대출 상담 등록 기능 테스트")
    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
        Counsel entity = Counsel.builder()
            .name("Member 1")
            .cellPhone("010-1111-1111")
            .email("abx@def.h")
            .memo("대출을 희망합니다.")
            .zipCode("12345")
            .address("경기도 수원시 떙떙동")
            .addressDetail("111동 111호")
            .build();

        Request request = Request.builder()
            .name("Member 1")
            .cellPhone("010-1111-1111")
            .email("abx@def.h")
            .memo("대출을 희망합니다.")
            .zipCode("12345")
            .address("경기도 수원시 떙떙동")
            .addressDetail("111동 111호")
            .build();
        when(counselRepository.save(any(Counsel.class))).thenReturn(entity);

        Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }
}