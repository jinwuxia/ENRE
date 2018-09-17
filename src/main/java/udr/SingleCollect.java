package udr;

import java.util.ArrayList;

public class SingleCollect {
    // all entities.  set id = index
    private ArrayList<AbsEntity> entities = new ArrayList<AbsEntity>();

    private static SingleCollect singleCollectInstance = new SingleCollect();

    private SingleCollect(){}

    public static SingleCollect getSingleCollectInstance() {
        return singleCollectInstance;
    }

    public int getCurrentIndex() {
        return entities.size();
    }

    public ArrayList<AbsEntity> getEntities() {
        return entities;
    }

    public void addEntity(AbsEntity entity) {
        entities.add(entity);
    }


}
