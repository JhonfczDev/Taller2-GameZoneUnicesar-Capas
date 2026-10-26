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

    /**
     * Creates and registers a controller from its data. The compatible consoles of a
     * controller are not part of this operation, exactly as they were not stored
     * when the controller was registered with the previous form.
     *
     * @param id             id of the controller
     * @param title          title of the controller
     * @param price          price of the controller
     * @param stockQuantity  stock quantity
     * @param connectionType type of connection
     */
    public void registerController(String id, String title, double price, int stockQuantity, String connectionType) {
        registerAccessory(new Controller(id, title, price, stockQuantity, connectionType));
    }

    /**
     * Creates and registers a cable from its data.
     *
     * @param id            id of the cable
     * @param title         title of the cable
     * @param price         price of the cable
     * @param stockQuantity stock quantity
     * @param lengthMeters  length in meters
     * @param connectorType type of connector
     */
    public void registerCable(String id, String title, double price, int stockQuantity,
                              double lengthMeters, String connectorType) {
        registerAccessory(new Cable(id, title, price, stockQuantity, lengthMeters, connectorType));
    }

    /**
     * Creates and registers a memory from its data.
     *
     * @param id                 id of the memory
     * @param title              title of the memory
     * @param price              price of the memory
     * @param stockQuantity      stock quantity
     * @param capacityGB         capacity in GB
     * @param memoryType         type of memory
     * @param compatibleConsoles ids of the compatible consoles; {@code null} means none
     */
    public void registerMemory(String id, String title, double price, int stockQuantity,
                               int capacityGB, String memoryType, List<String> compatibleConsoles) {
        Memory memory = new Memory(id, title, price, stockQuantity, capacityGB, memoryType);
        if (compatibleConsoles != null) {
            memory.setCompatibleConsoles(compatibleConsoles);
        }
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

    /**
     * Retrieves the accessories of a given type.
     *
     * @param accessoryType type name to look for (case insensitive)
     * @return the accessories whose type matches
     */
    public List<Accessory> getAccessoriesByType(String accessoryType) {
        List<Accessory> result = new ArrayList<>();

        for (Accessory accessory : repository.loadAll()) {
            if (accessory.getAccessoryType().equalsIgnoreCase(accessoryType)) {
                result.add(accessory);
            }
        }

        return result;
    }

    /**
     * Retrieves the accessories that are compatible with a console.
     *
     * @param consoleId id of the console
     * @return the accessories whose compatible consoles include the given console
     */
    public List<Accessory> getCompatibleAccessories(String consoleId) {
        List<Accessory> result = new ArrayList<>();

        for (Accessory accessory : repository.loadAll()) {
            if (accessory.getCompatibleConsoles().contains(consoleId)) {
                result.add(accessory);
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