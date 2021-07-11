package com.epam.esm.controller;

import com.epam.esm.dto.OrderDetails;
import com.epam.esm.dto.Purchase;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.UserModel;
import com.epam.esm.model.assembler.OrderModelAssembler;
import com.epam.esm.model.assembler.UserModelAssembler;
import com.epam.esm.security.SecurityUser;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;
    private final PagedResourcesAssembler<User> userPagedResourcesAssembler;
    private final UserModelAssembler userModelAssembler;
    private final PagedResourcesAssembler<Order> orderPagedResourcesAssembler;
    private final OrderModelAssembler orderModelAssembler;


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PagedModel<UserModel> findAll(Pageable pageable) {
        return userPagedResourcesAssembler.toModel(userService.findAll(pageable), userModelAssembler);
    }

    @GetMapping("{id}")
    public UserModel findById(@PathVariable Long id, Authentication authentication) {
        verifyPrincipleOrAdmin(id, authentication);
        return userModelAssembler.toModel(userService.findById(id));
    }

    @PostMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetails createOrder(@PathVariable Long userId, @RequestBody Purchase purchase, Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        verifyPrinciple(userId, principal);
        return orderService.save(purchase);
    }

    @GetMapping("/{userId}/orders")
    public PagedModel<OrderModel> getUserOrders(@PathVariable Long userId, Pageable pageable, Authentication authentication) {
        verifyPrincipleOrAdmin(userId, authentication);
        return orderPagedResourcesAssembler.toModel(orderService.findAllUserOrders(userId, pageable),
                orderModelAssembler);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public OrderDetails getUserOrder(@PathVariable Long userId, @PathVariable Long orderId, Authentication authentication) {
        verifyPrincipleOrAdmin(userId, authentication);
        return orderService.findUserOrder(userId, orderId);
    }

    @GetMapping("/{userId}/mostUsedTag")
    public MostWidelyUsedTag getMostWidelyUsedTag(@PathVariable Long userId, Authentication authentication) {
        verifyPrincipleOrAdmin(userId, authentication);
        return tagService.getMostWidelyUsedTag(userId);
    }

    private void verifyPrincipleOrAdmin(Long id, Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        if (principal.getAuthorities().stream()
                .map(String::valueOf)
                .noneMatch(role -> role.equals("ROLE_ADMIN"))) {
            verifyPrinciple(id, principal);
        }
    }

    private void verifyPrinciple(Long id, SecurityUser user) {
        if (!user.getId().equals(id)) {
            throw new AccessDeniedException("denied in access!");
        }
    }
}