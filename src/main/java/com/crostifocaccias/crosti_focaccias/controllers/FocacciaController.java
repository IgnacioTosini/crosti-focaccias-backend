package com.crostifocaccias.crosti_focaccias.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crostifocaccias.crosti_focaccias.dto.ApiResponse;
import com.crostifocaccias.crosti_focaccias.entities.Focaccia;
import com.crostifocaccias.crosti_focaccias.services.IFocacciaService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/focaccias")
public class FocacciaController {

    @Autowired
    private IFocacciaService focacciaService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Focaccia focaccia) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccia creada con éxito", focacciaService.createFocaccia(focaccia)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Error al crear la focaccia: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccias obtenidas con éxito", focacciaService.getAllFocaccias()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener las focaccias: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFocacciaById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccia obtenida con éxito", focacciaService.getFocacciaById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener la focaccia: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Focaccia focaccia) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccia actualizada con éxito", focacciaService.updateFocaccia(id, focaccia)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al actualizar la focaccia: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            boolean deleted = focacciaService.deleteFocaccia(id);
            if (!deleted) {
                return ResponseEntity.status(404).body(ApiResponse.error("Focaccia no encontrada con id: " + id));
            }
            return ResponseEntity.ok(ApiResponse.ok("Focaccia eliminada con éxito", null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al eliminar la focaccia: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByNameContaining(@RequestParam String param) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccias encontradas con éxito",
                            focacciaService.searchByNameContaining(param)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al buscar las focaccias: " + e.getMessage()));
        }
    }

    @GetMapping("/search/price-less")
    public ResponseEntity<?> getFocacciasByPriceLessThan(@RequestParam Double price) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccias encontradas con éxito",
                            focacciaService.getFocacciasByPriceLessThan(price)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al buscar las focaccias: " + e.getMessage()));
        }
    }

    @GetMapping("/search/price-greater")
    public ResponseEntity<?> getFocacciasByPriceGreaterThan(@RequestParam Double price) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccias encontradas con éxito",
                            focacciaService.getFocacciasByPriceGreaterThan(price)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al buscar las focaccias: " + e.getMessage()));
        }
    }

    @GetMapping("/featured")
    public ResponseEntity<?> getFeaturedFocaccias() {
        try {
            return ResponseEntity
                    .ok(ApiResponse.ok("Focaccias destacadas obtenidas con éxito",
                            focacciaService.getFeaturedFocaccias()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener las focaccias destacadas: " + e.getMessage()));
        }
    }
}
