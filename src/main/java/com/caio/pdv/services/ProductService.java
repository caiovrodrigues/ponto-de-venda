package com.caio.pdv.services;

import com.caio.pdv.entities.Product;
import com.caio.pdv.entities.repositories.ProductRepository;
import com.caio.pdv.web.dto.ProductCreateDTO;
import com.caio.pdv.web.dto.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(PageRequest pageRequest){
        return productRepository.findAll(pageRequest);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Produto com o id %d não existe.", id)));
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product create(ProductCreateDTO product) {
        return productRepository.save(product.toProduct());
    }
}
