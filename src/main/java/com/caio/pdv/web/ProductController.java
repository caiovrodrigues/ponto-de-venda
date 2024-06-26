package com.caio.pdv.web;

import com.caio.pdv.entities.Product;
import com.caio.pdv.infra.security.CustomUserDetails;
import com.caio.pdv.services.ProductService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ){
        String username = userDetails.getUser().getName();
        System.out.println("ACESSO A ROTA DE PRODUTOS CEDIDO À " + username);
        if(page <= 0){
            throw new IllegalArgumentException("Número da página é menor ou igual a zero.");
        }
        if(size <= 0){
            throw new IllegalArgumentException("Tamanho da página é menor ou igual a zero.");
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Product> products = productService.findAll(pageRequest);
        return ResponseEntity.ok(products);
    }

}
