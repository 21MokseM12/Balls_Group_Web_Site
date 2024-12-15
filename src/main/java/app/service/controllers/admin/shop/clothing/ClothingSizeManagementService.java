package app.service.controllers.admin.shop.clothing;

import app.domain.entites.shop.ClothingSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClothingSizeManagementService {

    @Autowired
    private ClothingSizeService clothingSizeService;

    public List<ClothingSize> findAllClothingSizes() {
        return clothingSizeService.findAll();
    }

    public Optional<ClothingSize> findClothingSizeById(Integer id) {
        return Optional.ofNullable(clothingSizeService.findById(id));
    }

    public ResponseEntity<String> addClothingSize(ClothingSize size) {
        if (clothingSizeService.existsBySize(size)) {
            return ResponseEntity.ok("Такой размер уже существует");
        } else {
            clothingSizeService.save(size);
            return ResponseEntity.ok("Размер одежды успешно добавлен!");
        }
    }

    public ResponseEntity<String> deleteClothingSize(Integer id) {
        if (clothingSizeService.existsById(id)) {
            clothingSizeService.deleteById(id);
            return ResponseEntity.ok("Размер был успешно удален!");
        } else {
            return ResponseEntity.ok("Размер не был найден");
        }
    }

    public ResponseEntity<String> updateClothingSize(ClothingSize size) {
        if (clothingSizeService.existsById(size.getId())) {
            clothingSizeService.save(size);
            return ResponseEntity.ok("Размер одежды успешно обновлен");
        } else {
            return ResponseEntity.ok("Размер не был найден");
        }
    }
}
