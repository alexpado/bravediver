package fr.alexpado.bravediver.entities;

import fr.alexpado.bravediver.enums.Currency;
import fr.alexpado.bravediver.enums.StratagemType;
import jakarta.persistence.*;
import org.json.JSONObject;

import java.util.Objects;

@Entity
public final class Stratagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StratagemType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private int cost;

    @Column(nullable = false)
    private int unlockLevel;

    @Column(nullable = false)
    private String image;

    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public StratagemType getType() {

        return this.type;
    }

    public void setType(StratagemType type) {

        this.type = type;
    }

    public Currency getCurrency() {

        return this.currency;
    }

    public void setCurrency(Currency currency) {

        this.currency = currency;
    }

    public int getCost() {

        return this.cost;
    }

    public void setCost(int cost) {

        this.cost = cost;
    }

    public int getUnlockLevel() {

        return this.unlockLevel;
    }

    public void setUnlockLevel(int unlockLevel) {

        this.unlockLevel = unlockLevel;
    }

    public String getImage() {

        return this.image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Stratagem) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                this.type == that.type &&
                this.currency == that.currency &&
                this.cost == that.cost &&
                this.unlockLevel == that.unlockLevel;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.name, this.type, this.currency, this.cost, this.unlockLevel);
    }

    @Override
    public String toString() {

        return "Stratagem{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", type=" + this.type +
                ", currency=" + this.currency +
                ", cost=" + this.cost +
                ", unlockLevel=" + this.unlockLevel +
                '}';
    }

    public JSONObject toJson() {

        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("image", "/assets/images/stratagems/" + this.image);
        json.put("type", this.type.name().toLowerCase());

        if (this.cost > 0) {
            JSONObject jsonCost = new JSONObject();
            jsonCost.put("currency", this.currency);
            jsonCost.put("amount", this.cost);
            json.put("cost", jsonCost);
        }

        if (this.unlockLevel > 0) {
            json.put("unlock_level", this.unlockLevel);
        }

        return json;
    }

}
