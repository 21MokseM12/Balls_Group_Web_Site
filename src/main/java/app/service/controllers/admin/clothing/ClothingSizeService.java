package app.service.controllers.admin.clothing;

import app.domain.entites.shop.ClothingSize;

import java.util.List;

public interface ClothingSizeService {
    ClothingSize findById(Integer id);

    List<ClothingSize> findAll();

    boolean existsById(Integer id);

    void deleteById(Integer id);

    void save(ClothingSize size);

    boolean existsBySize(ClothingSize size);

    ClothingSize findBySize(ClothingSize size);
}
