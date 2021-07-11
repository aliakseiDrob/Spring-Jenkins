package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class OrderRepositoryTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final User USER_INSTANCE = new User(1L, "Ivan", "ivan", null, null);
    private static final List<Order> USER_ORDERS =
            Arrays.asList(new Order(1L, LocalDateTime.parse("2021-05-24 20:11:10", DATE_TIME_FORMATTER),
                            new BigDecimal("340.00"), USER_INSTANCE, null),
                    new Order(2L, LocalDateTime.parse("2021-05-30 20:11:10", DATE_TIME_FORMATTER),
                            new BigDecimal("100.00"), USER_INSTANCE, null));
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testGetAllUserOrdersShouldReturnAllUserOrders() {
        //then
        assertEquals(new PageImpl<>(USER_ORDERS), orderRepository.findAllByUserId(1L, Pageable.unpaged()));
    }

    @Test
    public void testGetUserOrderShouldReturnOrder() {
        //given
        Optional<Order> expectedOrder = Optional.of(USER_ORDERS.get(0));
        //then
        assertEquals(expectedOrder, orderRepository.findByUserIdAndId(1L, 1L));
    }


    @Test
    public void testCreateOrderShouldCreateOrder() {
        //given
        User user = new User(1L, "Ivan","ivan",null,null);
        GiftCertificate giftCertificate = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now(),null);
        Order order = new Order(3L, LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER),
                new BigDecimal("128.01"), user,giftCertificate);

        //then
        Order savedOrder = orderRepository.save(order);

        assertEquals(3L,savedOrder.getId());
        assertEquals(LocalDateTime.now().format(FORMATTER),savedOrder.getOrderDate().format(FORMATTER));
        assertEquals(giftCertificate.getPrice(),savedOrder.getOrderCost());
        assertEquals(user.getId(),savedOrder.getUser().getId());
    }
}
