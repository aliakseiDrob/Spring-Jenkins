package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class TagRepositoryTest {

    private static final List<Tag> TAG_LIST = Arrays.asList(new Tag(1L, "first"), new Tag(2L, "second"));
    private static final List<Tag> TAG_LIST_AFTER_DELETE_TAG = Collections.singletonList(new Tag(2L, "second"));
    private static final Tag TAG = new Tag(1L, "first");
    private static final Tag TAG_INSTANCE_FOR_SAVE = new Tag(3L, "third");
    private static final long TAG_ID = 1L;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    MostWidelyUsedTagRepository mostWidelyUsedTagRepository;

    @Test
    public void testGetAllShouldReturnListTags() {
        //then
        assertEquals(TAG_LIST, tagRepository.findAll());
    }

    @Test
    public void testGetByIdShouldReturnTag() {
        //given
        Optional<Tag> expectedTag = Optional.of(TAG);
        //then
        assertEquals(expectedTag, tagRepository.findById(1L));
    }

    @Test
    void testSaveShouldSaveTag() {
        assertEquals(TAG_INSTANCE_FOR_SAVE, tagRepository.save(TAG_INSTANCE_FOR_SAVE));
    }

    @Test
    void TestDeleteShouldDeleteTag() {
        //given
        Tag tag = tagRepository.findById(TAG_ID).get();
        //when
        tagRepository.delete(tag);
        //then
        assertEquals(TAG_LIST_AFTER_DELETE_TAG, tagRepository.findAll());
    }

    @Test
    public void testIfExist() {
        //then
        assertTrue(tagRepository.existsTagByName("first"));
    }
}
