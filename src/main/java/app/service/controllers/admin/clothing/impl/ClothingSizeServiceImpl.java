package app.service.controllers.admin.clothing.impl;

import app.domain.entites.shop.ClothingSize;
import app.repository.ClothingSizeRepository;
import app.service.controllers.admin.clothing.ClothingSizeException;
import app.service.controllers.admin.clothing.ClothingSizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ClothingSizeServiceImpl implements ClothingSizeService {

    @Autowired
    private ClothingSizeRepository clothingSizeRepository;

    @Override
    public ClothingSize findById(Integer id) {
        return clothingSizeRepository.findById(id).orElseThrow(() -> {
            log.info("Clothing size was not found");
            return new ClothingSizeException("Размер не был найден");
        });
    }

    @Override
    public List<ClothingSize> findAll() {
        return clothingSizeRepository.findAll();
    }

    @Override
    public boolean existsById(Integer id) {
        return clothingSizeRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        clothingSizeRepository.deleteById(id);
    }

    @Override
    public void save(ClothingSize size) {
        clothingSizeRepository.save(size);
    }

    @Override
    public boolean existsBySize(ClothingSize size) {
        return clothingSizeRepository.existsBySize(size.getSize());
    }

    @Override
    public ClothingSize findBySize(ClothingSize size) {
        return clothingSizeRepository.findBySize(size.getSize());
    }
}
