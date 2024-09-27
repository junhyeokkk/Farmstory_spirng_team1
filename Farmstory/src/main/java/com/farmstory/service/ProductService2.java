package com.farmstory.service;

import com.farmstory.dto.ProductDTO;
import com.farmstory.entity.Product;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService2 {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductDTO selectProduct(int pNo) {
        Optional<Product> product = productRepository.findById(pNo);

        if(product.isPresent()){
            ProductDTO productDTO = null;
            productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
        }
        return null;
    }

    public List<ProductDTO> selectProductAll() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productList = products
                                        .stream()
                                        .map(Product -> modelMapper.map(Product, ProductDTO.class))
                                        .collect(Collectors.toList());
        return productList;
    }
}
