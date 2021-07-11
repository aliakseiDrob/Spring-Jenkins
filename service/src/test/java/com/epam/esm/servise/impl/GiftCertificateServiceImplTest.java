package com.epam.esm.servise.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateValidationException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.validator.CertificateValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String EMPTY_STRING = "";
    private static final String PART_NAME_OR_DESCRIPTION = "partNameOrDescription";
    private static List<GiftCertificate> certificates;
    private static List<GiftCertificateDto> certificatesDto;
    private static Set<Tag> tagsSet;
    private static Set<TagDto> tagsDtoSet;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateMapper mapper;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CertificateValidator certificateValidator;
    @InjectMocks
    GiftCertificateServiceImpl service;

    @BeforeAll
    public static void init() {
        initCertificatesList();
        initCertificatesDtoList();
        initTagsSet();
        initTagsDtoSet();
    }

    private static void initTagsSet() {
        tagsSet = new HashSet<>();
        tagsSet.add(new Tag(1L, "first"));
        tagsSet.add(new Tag(2L, "second"));
    }

    private static void initTagsDtoSet() {
        tagsDtoSet = new HashSet<>();
        tagsDtoSet.add(new TagDto(1L, "first"));
        tagsDtoSet.add(new TagDto(2L, "second"));
    }

    private static void initCertificatesList() {
        certificates = Arrays.asList(new GiftCertificate(1L, "first", "for men",
                        new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), Collections.emptySet()),
                new GiftCertificate(2L, "second", "for men",
                        new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), Collections.emptySet()));
    }

    private static void initCertificatesDtoList() {
        certificatesDto = Arrays.asList(new GiftCertificateDto(1L, "first", "for men",
                        new BigDecimal("128.01"), 11, Collections.emptySet()),
                new GiftCertificateDto(2L, "second", "for men",
                        new BigDecimal("128.01"), 11, Collections.emptySet()));
    }

    @Test
    void testFindAllShouldReturnSublistCertificates() {
        //given
        Page<GiftCertificate> certificatePage = new PageImpl<>(certificates);

        //when
        when(giftCertificateRepository.findAllAvailable(Pageable.unpaged())).thenReturn(certificatePage);

        //then
        assertEquals(certificatePage, service.findAll(Pageable.unpaged()));
        verify(giftCertificateRepository, times(1)).findAllAvailable(Pageable.unpaged());
    }

    @Test
    void testGetByIdShouldReturnCertificateWhenCertificateExists() {
        //when
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(certificates.get(0)));
        //then
        assertEquals(certificates.get(0), service.findById(1L));

        verify(giftCertificateRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetByIdShouldTrowExceptionWhenCertificateNotExists() {
        //when
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

        verify(giftCertificateRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindShouldThrowExceptionWhenParametersNotPassed() {

        //then
        assertThrows(PageNotFoundException.class, () -> service.search(new HashSet<>(), EMPTY_STRING, Pageable.unpaged()));

        verifyNoInteractions(giftCertificateRepository);
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(certificateValidator);
    }

    @Test
    void testFindShouldReturnPageCertificatesWhenTagNamesPassed() {
        //given
        Page<GiftCertificate> certificatePage = new PageImpl<>(certificates);
        HashSet<String> setNames = new HashSet<String>(Collections.singleton("name"));

        //when
        when(giftCertificateRepository.findAllAvailableByTagName(setNames, 1L, Pageable.unpaged())).thenReturn(certificatePage);

        //then
        assertEquals(certificatePage, service.search(setNames, EMPTY_STRING, Pageable.unpaged()));

        verify(giftCertificateRepository, times(1)).findAllAvailableByTagName(anySet(), anyLong(), any(Pageable.class));
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(certificateValidator);

    }

    @Test
    void testFindShouldReturnPageCertificatesWhenPartNameOrDescriptionPassed() {
        //given
        Page<GiftCertificate> certificatePage = new PageImpl<>(certificates);

        //when
        when(giftCertificateRepository.findAllAvailableByNameOrDescription(PART_NAME_OR_DESCRIPTION, Pageable.unpaged()))
                .thenReturn(certificatePage);

        //then
        assertEquals(certificatePage, service.search(new HashSet<>(), PART_NAME_OR_DESCRIPTION, Pageable.unpaged()));

        verify(giftCertificateRepository, times(1))
                .findAllAvailableByNameOrDescription(anyString(), any(Pageable.class));
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(certificateValidator);
    }

    @Test
    void testFindShouldReturnPageCertificatesWhenTagNameAndPartNameOrDescriptionPassed() {
        //given
        Page<GiftCertificate> certificatePage = new PageImpl<>(certificates);
        Set<String> setNames = new HashSet<>(Collections.singleton("name"));

        //when
        when(giftCertificateRepository.findByTagAndPartNameOrDescription(setNames, 1L,
                PART_NAME_OR_DESCRIPTION, Pageable.unpaged())).thenReturn(certificatePage);

        //then
        assertEquals(certificatePage, service.search(setNames, PART_NAME_OR_DESCRIPTION, Pageable.unpaged()));

        verify(giftCertificateRepository, times(1))
                .findByTagAndPartNameOrDescription(anySet(), anyLong(), anyString(), any(Pageable.class));
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(certificateValidator);
    }

    @Test
    void testSaveShouldReturnSavedCertificate() {
        //given
        GiftCertificateDto certificateDto = new GiftCertificateDto(1L, "first", "for men",
                new BigDecimal("128.01"), 11, tagsDtoSet);
        GiftCertificate certificateBeforeSave = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now(), Collections.emptySet());
        GiftCertificate certificateWithTags = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now(), tagsSet);
        GiftCertificate expectedCertificate = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now(), tagsSet);
        Tag firstTag = new Tag(1L, "first");
        Tag secondTag = new Tag(2L, "second");
        TagDto firstTagDto = new TagDto(1L, "first");
        TagDto secondTagDto = new TagDto(2L, "second");

        //when
        doNothing().when(certificateValidator).validateCertificateForSave(any(GiftCertificateDto.class));
        when(modelMapper.map(firstTagDto, Tag.class)).thenReturn(firstTag);
        when(modelMapper.map(secondTagDto, Tag.class)).thenReturn(secondTag);
        when(tagRepository.existsTagByName(anyString())).thenReturn(true);
        when(tagRepository.findByName("first")).thenReturn(Optional.of(firstTag));
        when(tagRepository.findByName("second")).thenReturn(Optional.of(secondTag));
        when(modelMapper.map(certificateDto, GiftCertificate.class)).thenReturn(certificateBeforeSave);
        when(giftCertificateRepository.save(certificateWithTags)).thenReturn(certificateWithTags);

        //then
        assertEquals(expectedCertificate, service.save(certificateDto));
        assertEquals(certificateBeforeSave, certificateWithTags);

        verify(certificateValidator, times(1)).validateCertificateForSave(any(GiftCertificateDto.class));
        verify(modelMapper, times(1)).map(firstTagDto, Tag.class);
        verify(modelMapper, times(1)).map(secondTagDto, Tag.class);
        verify(modelMapper, times(1)).map(certificateDto, GiftCertificate.class);
        verify(giftCertificateRepository, times(1))
                .save(any(GiftCertificate.class));
    }

    @Test
    void testSaveShouldThrowExceptionIfCertificateNotValid() {
        //when
        doThrow(CertificateValidationException.class).when(certificateValidator).validateCertificateForSave(certificatesDto.get(0));

        //then
        assertThrows(CertificateValidationException.class, () -> service.save(certificatesDto.get(0)));

        verify(certificateValidator, times(1)).validateCertificateForSave(any());
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(giftCertificateRepository);
    }

    @Test
    void testUpdateShouldReturnUpdatedCertificate() {
        //given
        GiftCertificateDto certificateDtoForUpdate = new GiftCertificateDto(1L, "updated", "updated",
                new BigDecimal("128.01"), 11, tagsDtoSet);
        GiftCertificate certificateFromBd = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1,
                LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), Collections.emptySet());
        GiftCertificate updatedCertificate = new GiftCertificate(1L, "updated", "updated",
                new BigDecimal("128.01"), 11, 1,
                LocalDateTime.now(), LocalDateTime.now(), tagsSet);
        //when
        doNothing().when(certificateValidator).validateCertificateForUpdate(certificateDtoForUpdate);
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(certificateFromBd));
        when(modelMapper.map(new TagDto(1L, "first"), Tag.class)).thenReturn(new Tag(1L, "first"));
        when(modelMapper.map(new TagDto(2L, "second"), Tag.class)).thenReturn(new Tag(2L, "second"));
        when(mapper.updateGiftCertificateFromDto(certificateDtoForUpdate, certificateFromBd)).thenReturn(updatedCertificate);
        when(giftCertificateRepository.save(any(GiftCertificate.class))).thenReturn(updatedCertificate);

        //then
        assertEquals(updatedCertificate, service.update(certificateDtoForUpdate));

    }

    @Test
    void testUpdateShouldThrowExceptionIfCertificateNotExists() {
        //when
        doNothing().when(certificateValidator).validateCertificateForUpdate(certificatesDto.get(0));
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(certificatesDto.get(0)));

        verify(certificateValidator, times(1)).validateCertificateForUpdate(any());
        verify(giftCertificateRepository, times(1)).findById(anyLong());
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testUpdateShouldThrowExceptionIfCertificateNotValid() {
        //when
        doThrow(CertificateValidationException.class).when(certificateValidator).validateCertificateForUpdate(certificatesDto.get(0));

        //then
        assertThrows(CertificateValidationException.class, () -> service.update(certificatesDto.get(0)));

        verify(certificateValidator, times(1)).validateCertificateForUpdate(any());
        verifyNoInteractions(giftCertificateRepository);
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testDeleteShouldThrowExceptionWhenCertificateNotExist() {
        //when
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.delete(anyLong()));

        verify(giftCertificateRepository, times(0)).save(any(GiftCertificate.class));
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(certificateValidator);

    }

    @Test
    void testDeleteShouldChangeIsAvailableFieldCertificate() {
        //given
        GiftCertificate certificateBeforeDeleting = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1,
                null, LocalDateTime.now(), Collections.emptySet());
        GiftCertificate certificateAfterDeleting = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 0,
                null, LocalDateTime.now(), Collections.emptySet());

        //when
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(certificateBeforeDeleting));

        //then
        service.delete(1L);
        assertEquals(certificateAfterDeleting, certificateBeforeDeleting);

        verify(giftCertificateRepository, times(1)).save(any(GiftCertificate.class));
        verifyNoInteractions(tagRepository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(certificateValidator);
    }
}
