package com.crostifocaccias.crosti_focaccias.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crostifocaccias.crosti_focaccias.entities.Focaccia;
import com.crostifocaccias.crosti_focaccias.repositories.IFocacciaRepository;

@Service
public class FocacciaService implements IFocacciaService {
    @Autowired
    private IFocacciaRepository focacciaRepository;

    @Autowired(required = false)
    private Cloudinary cloudinary;

    @Override
    @Transactional
    public Focaccia createFocaccia(Focaccia focaccia) {
        Focaccia existingFocaccia = focacciaRepository.findByName(focaccia.getName());
        if (existingFocaccia != null) {
            throw new IllegalArgumentException("La focaccia " + focaccia.getName() + " ya existe.");
        }
        return focacciaRepository.save(focaccia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Focaccia> getAllFocaccias() {
        return focacciaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Focaccia getFocacciaById(Long id) {
        return focacciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Focaccia no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public Focaccia updateFocaccia(Long id, Focaccia focaccia) {
        return focacciaRepository.findById(id).map(existingFocaccia -> {
            if (!existingFocaccia.getName().equals(focaccia.getName())) {
                Focaccia focacciaWithSameName = focacciaRepository.findByName(focaccia.getName());
                if (focacciaWithSameName != null) {
                    throw new IllegalArgumentException("La focaccia " + focaccia.getName() + " ya existe.");
                }
            }
            existingFocaccia.setName(focaccia.getName() != null ? focaccia.getName() : existingFocaccia.getName());
            existingFocaccia.setDescription(
                    focaccia.getDescription() != null ? focaccia.getDescription() : existingFocaccia.getDescription());
            existingFocaccia.setPrice(focaccia.getPrice() != null ? focaccia.getPrice() : existingFocaccia.getPrice());
            existingFocaccia.setIsVeggie(
                    focaccia.getIsVeggie() != null ? focaccia.getIsVeggie() : existingFocaccia.getIsVeggie());
            existingFocaccia.setImageUrl(
                    focaccia.getImageUrl() != null ? focaccia.getImageUrl() : existingFocaccia.getImageUrl());
            existingFocaccia.setImagePublicId(focaccia.getImagePublicId() != null ? focaccia.getImagePublicId()
                    : existingFocaccia.getImagePublicId());
            existingFocaccia.setFeatured(
                    focaccia.getFeatured() != null ? focaccia.getFeatured() : existingFocaccia.getFeatured());
            return focacciaRepository.save(existingFocaccia);
        }).orElseThrow(() -> new IllegalArgumentException("Focaccia no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public boolean deleteFocaccia(Long id) {
        try {
            Focaccia focaccia = focacciaRepository.findById(id).orElse(null);
            if (focaccia == null) {
                return false;
            }
            // Eliminar imagen de Cloudinary si hay imagePublicId y Cloudinary está
            // disponible
            if (cloudinary != null && focaccia.getImagePublicId() != null && !focaccia.getImagePublicId().isBlank()) {
                try {
                    cloudinary.uploader().destroy(focaccia.getImagePublicId(), ObjectUtils.emptyMap());
                } catch (Exception e) {
                    // Loguear pero no impedir el borrado de la entidad
                    System.err.println("Error al eliminar imagen de Cloudinary: " + e.getMessage());
                }
            }
            focacciaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la focaccia con id: " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Focaccia> searchByNameContaining(String keyword) {
        return focacciaRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Focaccia> getFocacciasByPriceLessThan(Double price) {
        return focacciaRepository.findByPriceLessThan(price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Focaccia> getFocacciasByPriceGreaterThan(Double price) {
        return focacciaRepository.findByPriceGreaterThan(price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Focaccia> getFocacciasByImageUrl(String imageUrl) {
        return focacciaRepository.findByImageUrl(imageUrl);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Focaccia> getFeaturedFocaccias() {
        return focacciaRepository.findByFeaturedTrue();
    }
}
