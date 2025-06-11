package com.shop.ecommerce.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Nur Admins dürfen hier zugreifen
public class AdminController {

    // Hier können in Zukunft andere Admin-bezogene Endpunkte eingefügt werden
}
