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

    public List<Accessory> getAccessoriesByType(String accessoryType) {
        List<Accessory> result = new ArrayList<>();

        for (Accessory accessory : repository.loadAll()) {
            if (accessory.getAccessoryType().equalsIgnoreCase(accessoryType)) {
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
                if (controller.getCompatibleConsoleIds().contains(consoleId)) {
                    result.add(controller);
                }
            } else if (accessory instanceof Memory) {
                Memory memory = (Memory) accessory;
                if (memory.getCompatibleConsoleIds().contains(consoleId)) {
                    result.add(memory);
                }
            }
        }

        return result;
    }

    public Accessory findById(String id) {
        return repository.findById(id);
    }
}
