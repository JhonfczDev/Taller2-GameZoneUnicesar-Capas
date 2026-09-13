package co.unicesar.edu.taller2.gamezoneunicesar.capas.service;

import co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence.AccessoryRepository;

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
}
