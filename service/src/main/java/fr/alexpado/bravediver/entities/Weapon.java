package fr.alexpado.bravediver.entities;

import fr.alexpado.bravediver.enums.WeaponSlot;
import fr.alexpado.bravediver.enums.WeaponType;
import jakarta.persistence.Entity;

//@Entity
public class Weapon {

    private int id;

    private String name;

    private WeaponSlot slot;

    private WeaponType type;



}
