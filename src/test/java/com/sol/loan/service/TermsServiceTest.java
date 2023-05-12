package com.sol.loan.service;

import com.sol.loan.domain.Terms;
import com.sol.loan.repository.TermsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sol.loan.dto.TermsDTO.Request;
import static com.sol.loan.dto.TermsDTO.Response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TermsServiceTest {

    @InjectMocks
    TermsServiceImpl termsService;

    @Mock
    private TermsRepository termsRepository;

    @Spy
    private ModelMapper modelMapper;


    @DisplayName("이용 약관 등록 기능 테스트")
    @Test
    void Should_ReturnResponseOfNewTermsEntity_When_RequestTerm() {
        Terms entity = Terms.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsd")
                .build();

        Request request = Request.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsd")
                .build();

        when(termsRepository.save(ArgumentMatchers.any(Terms.class))).thenReturn(entity);

        Response actual = termsService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getTermsDetailUrl()).isSameAs(entity.getTermsDetailUrl());
    }

    @DisplayName("이용 약관 조회 기능 테스트")
    @Test
    void Should_ReturnAllResponseOfExistTermsEntities_When_RequestTermsList() {
        Terms entityA = Terms.builder()
                .name("대출 이용 약관 1")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
                .build();

        Terms entityB = Terms.builder()
                .name("대출 이용 약관 2")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
                .build();

        List<Terms> list = new ArrayList<>(Arrays.asList(entityA, entityB));

        when(termsRepository.findAll()).thenReturn(list);

        List<Response> actual = termsService.getAll();

        assertThat(actual.size()).isSameAs(list.size());
    }
}