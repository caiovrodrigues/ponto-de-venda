package com.caio.pdv.web;

import com.caio.pdv.entities.Product;
import com.caio.pdv.infra.security.CustomUserDetails;
import com.caio.pdv.services.ProductService;
import com.caio.pdv.web.dto.ProductCreateDTO;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            JwtAuthenticationToken token
    ){
        System.out.println("ACESSO A ROTA DE PRODUTOS CEDIDO À " + token.getName());
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

    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductCreateDTO product){
        Product productSaved = productService.create(product);
        URI uri = UriComponentsBuilder.fromPath("/api/products/{id}").buildAndExpand(productSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(productSaved);
    }

}
