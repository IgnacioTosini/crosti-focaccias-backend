package com.crostifocaccias.crosti_focaccias.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crostifocaccias.crosti_focaccias.entities.Focaccia;

public interface IFocacciaRepository extends JpaRepository<Focaccia, Long> {
    Focaccia findByName(String name);

    Focaccia findByDescription(String description);

    Focaccia findByPrice(Double price);

    List<Focaccia> findByImageUrl(String imageUrl);

    List<Focaccia> findByPriceLessThan(Double price);

    List<Focaccia> findByPriceGreaterThan(Double price);

    List<Focaccia> findByNameContainingIgnoreCase(String keyword);

    List<Focaccia> findByFeaturedTrue();

}
