package com.sol.loan.service;

import com.sol.loan.domain.Counsel;
import com.sol.loan.dto.CounselDTO.Request;
import com.sol.loan.dto.CounselDTO.Response;
import com.sol.loan.exception.BaseException;
import com.sol.loan.exception.ResultType;
import com.sol.loan.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @DisplayName("대출 상담 조회 기능 테스트 - 레코드 o")
    @Test
    void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.get(findId);

        assertThat(actual.getCounselId()).isSameAs(findId);
    }

    @DisplayName("대출 상담 조회 기능 테스트 - 레코드 x")
    @Test
    void Should_ThrowException_When_RequestNotExistCounselId() {
        Long findId = 2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));
    }

    @DisplayName("대출 상담 수정 기능 테스트 - 레코드 o")
    @Test
    void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("Member Kim")
                .build();

        Request request = Request.builder()
                .name("Member Kang")
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));
        when(counselRepository.save(any(Counsel.class))).thenReturn(entity);

        Response actual = counselService.update(findId, request);

        assertThat(actual.getCounselId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());
    }

    @DisplayName("대출 상담 수정 기능 테스트 - 레코드 x")
    @Test
    void Should_ThrowException_When_RequestUpdateExistCounselInfo() {
        Long findId = 2L;

        Request request = Request.builder()
                .name("Member Kang")
                .build();

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        Assertions.assertThrows(BaseException.class, () -> counselService.update(findId, request));
    }

    @Test
    void Should_DeleteCounselEntity_When_RequestDeleteExistCounselInfo() {
        Long targetId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.save(any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        counselService.delete(targetId);

        assertThat(entity.getIsDeleted()).isSameAs(true);
    }
}