package com.epam.esm.servise.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.TagEntityException;
import com.epam.esm.exception.TagValidationException;
import com.epam.esm.repository.MostWidelyUsedTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static List<Tag> tags;

    @Mock
    private TagRepository tagRepository;
    @Mock
    private MostWidelyUsedTagRepository mostWidelyUsedTagRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TagValidator tagValidator;

    @InjectMocks
    private TagServiceImpl service;

    @BeforeAll
    public static void init() {
        tags = Arrays.asList(new Tag(1L, "first"), new Tag(2L, "second"));
    }

    @Test
    public void testGetAllShouldReturnAllTags() {
        //given
        Page<Tag> tagsPage = new PageImpl<>(tags);

        //when
        when(tagRepository.findAll(Pageable.unpaged())).thenReturn(tagsPage);

        //then
        assertEquals(tagsPage, service.findAll(Pageable.unpaged()));
        verify(tagRepository, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    public void testGetByIdShouldReturnTagWhenTagExists() {
        //when
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tags.get(0)));
        //then
        assertEquals(tags.get(0), service.findById(1L));

        verify(tagRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenTagNotExists() {
        //when
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

        verify(tagRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testShouldReturnSavedTagId() {
        //given
        TagDto tagDto = new TagDto(1L, "first");
        Tag tag = new Tag (0L, "first");

        // when
        when(tagValidator.isTagValid(tagDto)).thenReturn(true);
        when(modelMapper.map(tagDto, Tag.class)).thenReturn(tags.get(0));
        when(tagRepository.save(any(Tag.class))).thenReturn(tags.get(0));

        //then
        assertEquals(tag, service.save(tagDto));

        verify(tagValidator,times(1)).isTagValid(tagDto);
        verify(modelMapper, times(1)).map(tagDto, Tag.class);
        verify(tagRepository, times(1)).save(any(Tag.class));

    }

    @Test
    public void testShouldThrowExceptionWhenTagExists() {
        //given
        TagDto tagDto = new TagDto(1L, "first");

        // when
        when(tagValidator.isTagValid(tagDto)).thenReturn(true);
        when(modelMapper.map(tagDto, Tag.class)).thenReturn(tags.get(0));
        when(tagRepository.save(any(Tag.class))).thenThrow(new DataIntegrityViolationException(""));

        //then
        assertThrows(TagEntityException.class, () -> service.save(tagDto));

        verify(tagValidator,times(1)).isTagValid(tagDto);
        verify(modelMapper, times(1)).map(tagDto, Tag.class);
        verify(tagRepository, times(1)).save(any(Tag.class));
    }
    @Test
    public void testShouldThrowExceptionWhenTagNotValid() {
        //given
        TagDto tagDto = new TagDto(1L, "first");

        // when
        when(tagValidator.isTagValid(tagDto)).thenReturn(false);

        //then
        assertThrows(TagValidationException.class, () -> service.save(tagDto));

        verify(tagValidator,times(1)).isTagValid(tagDto);
        verify(modelMapper, times(0)).map(tagDto, Tag.class);
        verify(tagRepository, times(0)).save(any(Tag.class));
    }

    @Test
    public void testDeleteShouldDeleteTag() {
        //when
        doNothing().when(tagRepository).delete(any(Tag.class));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tags.get(0)));

        //then
        service.delete(anyLong());

        verify(tagRepository, times(1)).delete(any(Tag.class));
    }

    @Test
    public void testDeleteShouldThrowExceptionWhenTagNotExist() {
        //when
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class,()->service.delete(1L));

        verify(tagRepository, times(0)).delete(any(Tag.class));
    }

    @Test
    public void testGetMostWidelyUsedTagShouldReturnMostUsedTag() {
        // given
        MostWidelyUsedTag mostWidelyUsedTag = new MostWidelyUsedTag(1L, "first", new BigDecimal("100"));

        //when
        when(mostWidelyUsedTagRepository.findMostWidelyUsedTag(anyLong())).thenReturn(mostWidelyUsedTag);

        //then
        assertEquals(mostWidelyUsedTag, service.getMostWidelyUsedTag(1L));
    }
}
