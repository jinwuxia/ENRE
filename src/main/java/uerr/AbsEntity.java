package uerr;

import util.Tuple;

import java.util.ArrayList;

public class AbsEntity {
    protected String name;
    protected String simpleName;
    protected int id;
    protected int parentId = -1;
    protected ArrayList<Integer> childrenIds = new ArrayList<Integer>();
    protected ArrayList<Tuple<String,Integer>> relations = new ArrayList<Tuple<String, Integer>>();


    public String getSimpleName() {
        return simpleName;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSimpleName() {
        String [] arr = name.split("/|\\\\");
        simpleName = arr[arr.length - 1];
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void addRelation(Tuple<String,Integer> tuple) {
        relations.add(tuple);
    }

    public void addRelations(ArrayList<Tuple<String, Integer>> relations) {
        this.relations.addAll(relations);
    }

    public ArrayList<Tuple<String, Integer>> getRelations() {
        return relations;
    }

    public void addChildId(Integer id) {
        childrenIds.add(id);
    }

    public void addChildrenIds(ArrayList<Integer> ids) {
        childrenIds.addAll(ids);
    }

    public ArrayList<Integer> getChildrenIds() {
        return childrenIds;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(";
        str += ("name:" + name + ',');
        str += ("id:" + id + ',');
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";
        return str;
    }

    @Deprecated
    protected ArrayList<AbsEntity> includedEntities = new ArrayList<AbsEntity>();

    @Deprecated
    public ArrayList<AbsEntity> getIncludedEntities() {
        return includedEntities;
    }

    @Deprecated
    public void addIncludedEntity(AbsEntity entity) {
        includedEntities.add(entity);
    }

    @Deprecated
    public void addIncludedEntities(ArrayList<AbsEntity> entities) {
        includedEntities.addAll(entities);
    }

}
