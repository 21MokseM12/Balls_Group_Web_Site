package app.service.controllers.admin.shop.product.impl;

import app.domain.entites.shop.Product;
import app.repository.shop.ProductRepository;
import app.service.controllers.admin.shop.product.ProductException;
import app.service.controllers.admin.shop.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            log.info("Product was not found");
            return new ProductException("Товар не был найден");
        });
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public boolean existsByTitle(String title) {
        return productRepository.existsByTitle(title);
    }
}
