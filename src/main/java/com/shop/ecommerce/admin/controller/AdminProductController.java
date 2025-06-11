package com.shop.ecommerce.admin.controller;

import com.shop.ecommerce.product.dto.ProductRequestDTO;
import com.shop.ecommerce.product.dto.ProductResponseDTO;
import com.shop.ecommerce.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')") // Nur Admins dürfen hier zugreifen
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ 1. Alle Produkte (Admin-Sicht)
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProductsForAdmin());
    }

    // ✅ 2. Neues Produkt erstellen
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO created = productService.createProduct(requestDTO);
        return ResponseEntity.ok(created);
    }

    // ✅ 3. Produkt aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO updated = productService.updateProduct(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    // ✅ 4. Produkt löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ 5. Produktsuche
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<ProductResponseDTO> results = productService.searchProducts(name, minPrice, maxPrice);
        return ResponseEntity.ok(results);
    }
}
