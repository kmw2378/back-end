package org.kakaoshare.backend.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.kakaoshare.backend.domain.cart.dto.CartRegisterResponse;
import org.kakaoshare.backend.domain.cart.service.CartService;
import org.kakaoshare.backend.domain.funding.dto.RegisterRequest;
import org.kakaoshare.backend.domain.funding.dto.RegisterResponse;
import org.kakaoshare.backend.jwt.util.LoggedInMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/{productId}")
    public ResponseEntity<?> registerItem(@PathVariable Long productId, @LoggedInMember String providerId) {
        CartRegisterResponse response = cartService.registerItem(productId,providerId);
        return ResponseEntity.ok(response);
    }
}
