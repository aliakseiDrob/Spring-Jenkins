package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class GiftCertificateRepositoryImpl {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final GiftCertificate CERTIFICATE_FOR_SAVE = new GiftCertificate(4L, "fourth", "for men",
            new BigDecimal("49.20"), 6, 1,
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER),
            LocalDateTime.parse("2021-03-29 20:11:10", DATE_TIME_FORMATTER), null);
    private static final long CERTIFICATE_ID = 1L;
    private static final Set<String> SET_TAGS_NAMES = Collections.singleton("first");
    private static final String NAME_OR_DESCRIPTION = "en";

    private static List<GiftCertificate> expectedCertificateListGetAll;
    private static List<GiftCertificate> expectedCertificateListFindByTagOrDescriptionOrName;


    private static void initCertificateListGetAll() {
        expectedCertificateListGetAll = Arrays.asList(
                new GiftCertificate(1L, "first", "for men", new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), null),
                new GiftCertificate(2L, "second", "children", new BigDecimal("250.20"), 7, 1,
                        LocalDateTime.parse("2021-03-06 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-11 20:11:10", DATE_TIME_FORMATTER), null),
                new GiftCertificate(3L, "third", "everybody", new BigDecimal("48.50"), 3, 1,
                        LocalDateTime.parse("2021-03-26 19:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-28 20:11:10", DATE_TIME_FORMATTER), null),
                new GiftCertificate(4L, "first", "children", new BigDecimal("48.50"), 3, 1,
                        LocalDateTime.parse("2021-03-20 19:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-28 20:11:10", DATE_TIME_FORMATTER), null));
    }

    private static void initCertificateListFindByTagOrDescriptionOrName() {
        expectedCertificateListFindByTagOrDescriptionOrName = Arrays.asList(
                new GiftCertificate(1L, "first", "for men", new BigDecimal("128.01"), 11, 1,
                        LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER), null),
                new GiftCertificate(2L, "second", "children", new BigDecimal("250.20"), 7, 1,
                        LocalDateTime.parse("2021-03-06 20:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-11 20:11:10", DATE_TIME_FORMATTER), null),
                new GiftCertificate(4L, "first", "children", new BigDecimal("48.50"), 3, 1,
                        LocalDateTime.parse("2021-03-20 19:11:10", DATE_TIME_FORMATTER),
                        LocalDateTime.parse("2021-03-28 20:11:10", DATE_TIME_FORMATTER), null));
    }

    @Autowired
    GiftCertificateRepository repository;

    @BeforeAll
    static void initAllFields() {
        initCertificateListGetAll();
        initCertificateListFindByTagOrDescriptionOrName();
    }

    @Test
    void testGetAllShouldReturnListCertificates() {
        //then
        assertEquals(repository.findAll(), expectedCertificateListGetAll);
    }

    @Test
    public void testGetByIdShouldReturnCertificate() {
        //given
        Optional<GiftCertificate> expectedCertificate = Optional.of(expectedCertificateListGetAll.get(0));
        //then
        assertEquals(expectedCertificate, repository.findById(CERTIFICATE_ID));
    }

    @Test
    public void testFindByNameOrDescriptionShouldReturnListCertificates() {
        //then
        assertEquals(repository.findAllAvailableByNameOrDescription("en", Pageable.unpaged()),
                new PageImpl<>(expectedCertificateListFindByTagOrDescriptionOrName));
    }

    @Test
    public void testFindByTagNameOrNameOrDescriptionShouldReturnListCertificates() {
        //then
        assertEquals(repository.findByTagAndPartNameOrDescription(SET_TAGS_NAMES, 1L, NAME_OR_DESCRIPTION, Pageable.unpaged()),
                new PageImpl<>(expectedCertificateListFindByTagOrDescriptionOrName));
    }

    @Test
    public void testSaveShouldSaveCertificateInDataBase() {

        //then
        assertEquals(CERTIFICATE_FOR_SAVE, repository.save(CERTIFICATE_FOR_SAVE));
    }
}
