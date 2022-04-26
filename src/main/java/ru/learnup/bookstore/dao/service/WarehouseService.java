package ru.learnup.bookstore.dao.service;

import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Warehouse;
import ru.learnup.bookstore.dao.repository.WarehouseRepository;
import java.util.List;

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

}
