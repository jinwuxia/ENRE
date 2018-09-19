package udr;

import sun.security.krb5.Config;
import util.Configure;
import util.Tuple;

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


    public boolean isFolder(int id) {
        if(id == -1) {
            return false;
        }
        if (singleCollectInstance.getEntities().get(id) instanceof AbsFLDEntity) {
            return true;
        }
        return false;
    }

    public boolean isFile(int id) {
        if(id == -1) {
            return false;
        }
        if(singleCollectInstance.getEntities().get(id) instanceof AbsFILEntity) {
            return true;
        }
        return false;
    }

    public boolean isClass(int id) {
        if(id == -1) {
            return false;
        }
        if(singleCollectInstance.getEntities().get(id) instanceof AbsCLSEntity) {
            return true;
        }
        return false;
    }

    public boolean isFunction(int id) {
        if(id == -1) {
            return false;
        }
        if(singleCollectInstance.getEntities().get(id) instanceof AbsFUNEntity) {
            return true;
        }
        return false;

    }

    public boolean isCaredEntity(int id) {
        if(id == -1) {
            return false;
        }
        if(isFolder(id)) {
            return true;
        }
        if(isClass(id)) {
            return true;
        }
        if(isFile(id)) {
            return true;
        }
        if(isFunction(id)) {
            return true;
        }

        return false;
    }



}
