package com.seal.ecommerce.product;

import com.seal.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }


    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        List<Integer> productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products not exist");
        }

        List<ProductPurchaseRequest> storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();

        for(int i = 0; i < storedProducts.size(); i ++){
            Product storedProduct = storedProducts.get(i);
            ProductPurchaseRequest productPurchaseRequest = storedRequest.get(i);

            if(storedProduct.getAvailableQuantity() < productPurchaseRequest.quantity()){
                throw new ProductPurchaseException("Product with ID:: " + storedProduct.getId() + " has not enough quantity");
            }
            double newAvailableQuantity = storedProduct.getAvailableQuantity() - productPurchaseRequest.quantity();
            storedProduct.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(storedProduct);

            purchasedProducts.add(productMapper.toProductPurchaseResponse(storedProduct, productPurchaseRequest.quantity()));
        }

        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
