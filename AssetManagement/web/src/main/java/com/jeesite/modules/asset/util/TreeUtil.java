package com.jeesite.modules.asset.util;

import com.jeesite.modules.asset.ding.data.Node;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
    public static void createTree(List<Node> list) {
        String lowLevel = list.get(0).getLevel();
        List<Node> children = new ArrayList<Node>();
        // 当等级最高时，停止排序
        if (!"0".equals(lowLevel)) {
            // 将最低级别的放到数组中
            for (Node node : list) {
                if (lowLevel.equals(node.getLevel()))
                    children.add(node);
                else
                    break;
            }
            // 增加遍历效率 
            list.removeAll(children);
            for (Node child : children) {
                for (Node parent : list) {
                    if (parent.getId().equals(child.getParentId()))
                        parent.addChildren(child);

                }
            }
            createTree(list);
        }
    }

}
