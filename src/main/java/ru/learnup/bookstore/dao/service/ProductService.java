package ru.learnup.bookstore.dao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Product;
import ru.learnup.bookstore.dao.repository.ProductRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public void updateProduct(Product product) {
        try {
            productRepository.save(product);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for product {}", product.getId());
        }

    }
}
