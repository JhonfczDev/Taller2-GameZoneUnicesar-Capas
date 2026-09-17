package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.model.*;
import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.AccessoryRepository;

import java.util.*;
import java.io.*;

public class AccessoryService {

    private final AccessoryRepository repository;

    public AccessoryService(AccessoryRepository repository) {
        this.repository = repository;
    }

    public void registerController(Controller controller) {
        registerAccessory(controller);
    }

    public void registerCable(Cable cable) {
        registerAccessory(cable);
    }

    public void registerMemory(Memory memory) {
        registerAccessory(memory);
    }

    private void registerAccessory(Accessory accessory) {
        if (accessory == null) {
            throw new IllegalArgumentException("the accessory cannot be null.");
        }

        List<Accessory> accessories = repository.loadAll();

        for (Accessory existing : accessories) {
            if (existing.getId().equals(accessory.getId())) {
                throw new IllegalArgumentException(
                        "An accessory already exists registered with the id: " + accessory.getId()
                );
            }
        }

        accessories.add(accessory);
        repository.saveAll(accessories);
    }

    public List<Accessory> getAllAccessories() {
        return repository.loadAll();
    }

    public List<Accessory> getAccessoriesByType(String accessoryType) {
        List<Accessory> result = new ArrayList<>();

        for (Accessory accessory : repository.loadAll()) {
            String type;

            if (accessory instanceof Controller) {
                type = "CONTROLLER";
            } else if (accessory instanceof Cable) {
                type = "CABLE";
            } else if (accessory instanceof Memory) {
                type = "MEMORY";
            } else {
                continue;
            }

            if (type.equalsIgnoreCase(accessoryType)) {
                result.add(accessory);
            }
        }

        return result;
    }

    public List<Accessory> getCompatibleAccessories(String consoleId) {
        List<Accessory> result = new ArrayList<>();

        for (Accessory accessory : repository.loadAll()) {
            if (accessory instanceof Controller) {
                Controller controller = (Controller) accessory;
                if (controller.getCompatibleConsoles().contains(consoleId)) {
                    result.add(controller);
                }
            } else if (accessory instanceof Memory) {
                Memory memory = (Memory) accessory;
                if (memory.getCompatibleConsoles().contains(consoleId)) {
                    result.add(memory);
                }
            }
        }

        return result;
    }

    public Accessory findById(String id) {
        return repository.findById(id);
    }

    public boolean hasStock(Accessory accessory) {
        return accessory != null && accessory.getStockQuantity() > 0;
    }

    public void update(Accessory accessory) {
        if (accessory == null) {
            throw new IllegalArgumentException("The accessory cannot be null.");
        }

        List<Accessory> accessories = repository.loadAll();
        boolean found = false;

        for (int i = 0; i < accessories.size(); i++) {
            if (accessories.get(i).getId().equals(accessory.getId())) {
                accessories.set(i, accessory);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException(
                    "No accessory was found with the id: " + accessory.getId());
        }

        repository.saveAll(accessories);
    }
}
