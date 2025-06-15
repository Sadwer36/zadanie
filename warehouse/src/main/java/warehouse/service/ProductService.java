package warehouse.service;

import warehouse.model.Product;
import warehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Не найден товар с id: " + id));
    }

    public Product getProductByArticle(String article) {
        return productRepository.findByArticle(article)
                .orElseThrow(() -> new RuntimeException("Не найден товар с артиклем: " + article));
    }

    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.existsByArticle(product.getArticle())) {
            throw new RuntimeException("Товар с артиклем " + product.getArticle() + " уже существует");
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);

        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }
        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }
        if (productDetails.getQuantity() != null) {
            product.setQuantity(productDetails.getQuantity());
        }

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Не найден товар с id: " + id);
        }
        productRepository.deleteById(id);
    }
}