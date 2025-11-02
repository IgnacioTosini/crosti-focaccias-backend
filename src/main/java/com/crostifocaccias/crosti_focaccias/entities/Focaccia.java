package com.crostifocaccias.crosti_focaccias.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "focaccias")
public class Focaccia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean isVeggie;
    private String imageUrl;
    private String imagePublicId;
    private Boolean featured = false;
    @OneToMany(mappedBy = "focaccia")
    @JsonIgnore
    private List<PedidoFocaccia> pedidoFocaccias;

    public Focaccia() {
        this.pedidoFocaccias = new ArrayList<>();
    }

    public Focaccia(String name, String description, Double price, Boolean isVeggie, String imageUrl,
            String imagePublicId, Boolean featured) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isVeggie = isVeggie;
        this.imageUrl = imageUrl;
        this.imagePublicId = imagePublicId;
        this.featured = featured;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePublicId() {
        return imagePublicId;
    }

    public void setImagePublicId(String imagePublicId) {
        this.imagePublicId = imagePublicId;
    }

    public Boolean getIsVeggie() {
        return isVeggie;
    }

    public void setIsVeggie(Boolean isVeggie) {
        this.isVeggie = isVeggie;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public List<PedidoFocaccia> getPedidoFocaccias() {
        return pedidoFocaccias;
    }

    public void setPedidoFocaccias(List<PedidoFocaccia> pedidoFocaccias) {
        this.pedidoFocaccias = pedidoFocaccias;
    }

    @Override
    public String toString() {
        return "Focaccia [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", isVeggie=" + isVeggie + ", imageUrl=" + imageUrl + ", imagePublicId=" + imagePublicId
                + ", featured=" + featured + "]";
    }
}
