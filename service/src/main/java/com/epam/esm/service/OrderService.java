package com.epam.esm.service;

import com.epam.esm.dto.OrderDetails;
import com.epam.esm.dto.Purchase;
import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for serving Order objects according to the business logic of Order
 */
public interface OrderService {

    /**
     * Saves  Purchase that contains User id and GiftCertificate id
     *
     * @param purchase Purchase entity
     * @return Order id
     */
    OrderDetails save(Purchase purchase);

    /**
     * finds sublist of User's Orders
     *
     * @param userId   User id
     * @param pageable object for pagination information
     * @return a sublist Orders
     */
    Page<Order> findAllUserOrders(Long userId, Pageable pageable);

    /**
     * finds sublist User's Order by User id and Order id
     *
     * @param userId  User id
     * @param orderId Order id
     * @return OrderDetails entity
     */
    OrderDetails findUserOrder(Long userId, Long orderId);
}
