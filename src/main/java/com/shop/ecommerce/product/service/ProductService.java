package com.shop.ecommerce.product.service;

import com.shop.ecommerce.cart.entity.Cart;
import com.shop.ecommerce.cart.exception.ProductNotFoundException;
import com.shop.ecommerce.product.dto.ProductRequestDTO;
import com.shop.ecommerce.product.dto.ProductResponseDTO;
import com.shop.ecommerce.product.entity.Product;
import com.shop.ecommerce.product.mapper.ProductMapper;
import com.shop.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Erzeugt ein neues Produkt basierend auf dem ProductRequestDTO.
     * @param //productRequest DTO mit Produktdaten
     * @return das gespeicherte Produkt als ProductResponseDTO
     */
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = ProductMapper.toEntity(dto); // DTO → Entity
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved); // Entity → DTO
    }


    /**
     * Liefert alle Produkte als Entity-Liste.
     * @return Liste aller Produkte
     */
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("Produkte geladen: " + products.size());
        for (Product p : products) {
            System.out.println(p.getId() + " | " + p.getName() + " | " + p.getCategory());
        }
        return products;
    }

    /**
     * Liefert alle Produkte als DTO-Liste (für Admin).
     * @return Liste aller Produkte als ProductResponseDTO
     */
    public List<ProductResponseDTO> getAllProductsForAdmin() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    /**
     * Holt ein Produkt anhand der ID.
     * @param productId ID des Produkts
     * @return Produkt-Entity
     * @throws ProductNotFoundException wenn nicht gefunden
     */
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produkt mit ID " + productId + " nicht gefunden"
                ));
    }

    /**
     * Löscht ein Produkt anhand der ID.
     * @param id ID des zu löschenden Produkts
     * @throws EntityNotFoundException wenn Produkt nicht existiert
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Produkt mit ID " + id + " nicht gefunden");
        }
        productRepository.deleteById(id);
    }

    /**
     * Beispiel-Methode: Aktualisiert Gesamtpreis des Warenkorbs.
     * @param cart der Warenkorb
     */
    private void updateTotalPrice(Cart cart) {
        cart.calculateTotalPrice(); // Nutzt Entity-Methode des Warenkorbs
    }

    // Update Methode
    @Transactional
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produkt mit ID " + productId + " nicht gefunden"));

        // Neue Werte übernehmen
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        // Konvertiere Double zu BigDecimal
        product.setPrice(dto.getPrice() != null ? BigDecimal.valueOf(dto.getPrice()) : null);

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDTO(updatedProduct);
    }

    // Suche Methode - einfache Suche nach Name (Teilstring) und optional Preisbereich
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchProducts(String namePart, Double minPrice, Double maxPrice) {
        BigDecimal min = minPrice != null ? BigDecimal.valueOf(minPrice) : BigDecimal.ZERO;
        BigDecimal max = maxPrice != null ? BigDecimal.valueOf(maxPrice) : BigDecimal.valueOf(Double.MAX_VALUE);

        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndPriceBetween(namePart, min, max);
        return products.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }
}
