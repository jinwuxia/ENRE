package visitor;

import entity.Entity;
import java.util.ArrayList;

public class SingleCollect {
    // all entities.  set id = index
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    private static SingleCollect singleCollectInstance = new SingleCollect();

    private SingleCollect(){}

    public static SingleCollect getSingleCollectInstance() {
        return singleCollectInstance;
    }

    public int getCurrentIndex() {
        return entities.size();
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }


}
