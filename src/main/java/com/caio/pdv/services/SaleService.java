package com.caio.pdv.services;


import com.caio.pdv.entities.ItemSale;
import com.caio.pdv.entities.Product;
import com.caio.pdv.entities.Sale;
import com.caio.pdv.entities.User;
import com.caio.pdv.entities.repositories.ItemSaleRepository;
import com.caio.pdv.entities.repositories.SaleRepository;
import com.caio.pdv.services.exceptions.ProductEstoqueInsuficiente;
import com.caio.pdv.web.dto.ItemSaleDTO;
import com.caio.pdv.web.dto.SaleRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ItemSaleRepository itemSaleRepository;

    public List<Sale> findAll(){
        return saleRepository.findAll();
    }

    public Sale findById(Long id){
        return saleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Sale com o id: %d não existe.", id)));
    }

    @Transactional
    public Sale save(SaleRequestDTO newSale) {
        User user = userService.findById(newSale.userId());

        Sale sale = new Sale();

        List<ItemSale> itemSaleList = newSale.items().stream().map(item -> {
            Product product = productService.findById(item.productId());

            if(product.getQuantity() < item.quantity()){
                throw new ProductEstoqueInsuficiente(String.format("Quantidade do produto %s (%d) no estoque é menor do que a quantidade pedida (%d).", product.getName(), product.getQuantity(), item.quantity()));
            }

            product.setQuantity(product.getQuantity() - item.quantity());
            productService.save(product);
            return ItemSale.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(item.quantity())
                    .build();
        }).toList();

        sale.setUser(user);
        sale.setItems(itemSaleList);

        return saleRepository.save(sale);
    }

    @Transactional
    public ItemSale addItemToSale(Long idSale, ItemSaleDTO newItem) {
        Sale sale = findById(idSale);
        Product product = productService.findById(newItem.productId());
        ItemSale itemSale = ItemSale.builder()
                .product(product)
                .quantity(newItem.quantity())
                .sale(sale)
                .build();
        return itemSaleRepository.save(itemSale);
    }

    public void deleteItemFromSale(Long itemId){
        itemSaleRepository.deleteById(itemId);
    }

    public void deleteSale(Long id){
        saleRepository.deleteById(id);
    }

}
