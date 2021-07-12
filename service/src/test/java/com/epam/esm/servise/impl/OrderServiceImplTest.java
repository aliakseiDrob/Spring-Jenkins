package com.epam.esm.servise.impl;

import com.epam.esm.dto.OrderDetails;
import com.epam.esm.dto.Purchase;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Purchase PURCHASE = new Purchase(1L, 1L);
    private static final User USER = new User(1L, "first", "password", null, null);
    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate(1L, "first", "for men",
            new BigDecimal("128.01"), 11, 1,
            null, null, null);
    private static List<Order> orders;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @InjectMocks
    private OrderServiceImpl service;

    @BeforeAll
    public static void init() {
        orders = Arrays.asList(new Order(1L, LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        new BigDecimal("100"), new User(), new GiftCertificate()),
                new Order(2L, LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                        new BigDecimal("200"), new User(), new GiftCertificate()));
    }

    @Test
    public void testCreateOrderShouldReturnSavedOrderId() {
        //given
        OrderDetails orderDetails = new OrderDetails(1L, LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),new BigDecimal("100"));
        //when
        when(orderRepository.save(any(Order.class))).thenReturn(orders.get(0));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(GIFT_CERTIFICATE));

        //then
        assertEquals(orderDetails, service.save(PURCHASE));

        verify(userRepository, times(1)).findById(anyLong());
        verify(giftCertificateRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any(Order.class));

    }

    @Test
    public void testCreateOrderShouldThrowExceptionWhenUserNotExists() {
        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> service.save(PURCHASE));

        verify(userRepository, times(1)).findById(anyLong());
        verify(giftCertificateRepository, times(0)).findById(anyLong());
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    public void testCreateOrderShouldThrowExceptionWhenCertificateNotExists() {
        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> service.save(PURCHASE));

        verify(userRepository, times(1)).findById(anyLong());
        verify(giftCertificateRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    public void testFindAllUserOrdersShouldReturnAllUserOrders() {
        //given
        Page<Order> orderPage = new PageImpl<>(orders);

        //when
        when(orderRepository.findAllByUserId(1L, Pageable.unpaged())).thenReturn(orderPage);

        //then
        assertEquals(orderPage, service.findAllUserOrders(1L, Pageable.unpaged()));

        verify(orderRepository, times(1)).findAllByUserId(anyLong(), any(Pageable.class));
    }

    @Test
    public void testFindUserOrderShouldReturnUserOrder() {
        //given
        OrderDetails orderDetails = new OrderDetails(1L,LocalDateTime.parse("2021-03-21 20:11:10", DATE_TIME_FORMATTER),
                new BigDecimal("100"));

        //when
        when(orderRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(orders.get(0)));

        //then
        assertEquals(orderDetails, service.findUserOrder(1L, 1L));

        verify(orderRepository, times(1)).findByUserIdAndId(anyLong(), anyLong());
    }

    @Test
    public void testFindUserOrderShouldThrowExceptionWhenOrderNotExists() {
        //when
        when(orderRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> service.findUserOrder(1L, 1L));

        verify(orderRepository, times(1)).findByUserIdAndId(anyLong(), anyLong());
    }
}
