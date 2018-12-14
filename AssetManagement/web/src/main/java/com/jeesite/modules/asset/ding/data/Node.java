package com.jeesite.modules.asset.ding.data;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String id;
    private String name;
    private String level;
    private String parentId;
    private List<Node> children;
    public Node(){
        this.children=new ArrayList<>();
    }
    public void  addChildren(Node node) {
        this.children.add(node);
    }
    @Override
    public String toString(){
        return "{ id:"+this.id+",name"+this.name+",parentId:"+this.parentId+",level:"+this.level+",children:"+this.children+"}";
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
