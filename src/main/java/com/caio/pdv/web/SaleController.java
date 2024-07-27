package com.caio.pdv.web;

import com.caio.pdv.entities.ItemSale;
import com.caio.pdv.entities.Sale;
import com.caio.pdv.entities.User;
import com.caio.pdv.infra.security.CustomUserDetails;
import com.caio.pdv.infra.utils.ModelMapperSingleton;
import com.caio.pdv.services.SaleService;
import com.caio.pdv.web.dto.ItemSaleDTO;
import com.caio.pdv.web.dto.SaleRequestDTO;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales(JwtAuthenticationToken token){
//        User user = ModelMapperSingleton.getUserFromContext();
        System.out.println("ROTA PRIVADA ACESSADA POR " + token.getName());
        List<Sale> sales = saleService.findAll();
        return ResponseEntity.ok(sales);
    }

    @PostMapping
    public ResponseEntity<Sale> save(@Valid @RequestBody SaleRequestDTO newSale){
        Sale saleSaved = saleService.save(newSale);
        return ResponseEntity.ok(saleSaved);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ItemSale> addItemToSale(@PathVariable("id") Long idSale, @Valid @RequestBody ItemSaleDTO newItem){
        ItemSale itemSale = saleService.addItemToSale(idSale, newItem);
        return ResponseEntity.ok(itemSale);
    }

    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<Void> deleteItemFromSale(@PathVariable Long id){
        saleService.deleteItemFromSale(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id){
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

}
