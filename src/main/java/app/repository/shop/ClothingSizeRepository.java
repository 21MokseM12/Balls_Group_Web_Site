package app.repository.shop;

import app.domain.entites.shop.ClothingSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingSizeRepository extends JpaRepository<ClothingSize, Integer> {
    boolean existsBySize(String size);

    ClothingSize findBySize(String size);
}
