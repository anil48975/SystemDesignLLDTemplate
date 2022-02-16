package systemdesignnew.entity;

import systemdesignnew.service.SequenceGenerator;

import java.util.Objects;
import java.util.UUID;

public class Item {

    //@Version- Hibernate
    private int version;
    //@id - Hibernate
    private int id;
    //id before save
    private String itemId;
    private int name;

    public Item() {
        id = SequenceGenerator.getInstance().getSequence();
        itemId = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object item){
        return item != null && item instanceof Item && ((Item) item).itemId.equals(this.itemId) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, itemId, name);
    }

    @Override
    public String toString() {
        return "Item sequence id : " + this.id + " Item UUId: " + this.itemId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
