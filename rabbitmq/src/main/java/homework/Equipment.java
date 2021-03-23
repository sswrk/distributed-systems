package homework;

import java.util.Optional;

public enum Equipment {

    OXYGEN,
    BACKPACK,
    BOOTS;

    public static Optional<Equipment> getEquipment(String equipmentName){
        try {
            return Optional.of(valueOf(equipmentName.toUpperCase()));
        } catch (IllegalArgumentException e){
            return Optional.empty();
        }
    }

}
