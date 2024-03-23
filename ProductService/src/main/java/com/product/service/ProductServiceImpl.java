package com.product.service;

import com.product.entity.Product;
import com.product.exception.CustomException;
import com.product.model.ProductRequest;
import com.product.model.ProductResponse;
import com.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;


    @Override
    public long addProduct(ProductRequest productRequest) {

        log.info("Adding Product..");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        log.info("Product created ..");
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for product id : {}",productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product with given id not found !", NOT_FOUND));

        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product,response);

        return response;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return null;
    }
}
