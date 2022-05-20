package ru.learnup.bookstore.dao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Warehouse;
import ru.learnup.bookstore.dao.repository.WarehouseRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class WarehouseService {

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> getWarehouse() {
        return warehouseRepository.findAll();
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    public Warehouse getWarehouseById(long id) {
        return warehouseRepository.getById(id);
    }

    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public void updateWarehouse(Warehouse warehouse) {
        try {
            warehouseRepository.save(warehouse);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for update warehouse {}", warehouse.getId());
        }
    }

}
