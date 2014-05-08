package com.ivan.utils.space.quadtree;

import com.ivan.utils.math.geometry.Point2D;

public interface QuadtreeVisitor<T extends Point2D> {
    void visitNode(int depth, QuadtreeNodePosition pos);

    void leaveNode(int depth, QuadtreeNodePosition pos);

    void visitLeaf(T leaf, int depth, QuadtreeNodePosition pos);

    void leaveLeaf(T leaf, int depth, QuadtreeNodePosition pos);
}

/*
// basic quadtree printer
new QuadtreeVisitor<Color>() {
    private String pad(final int depth) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
    
    @Override
    public void visitNode(final int depth, final QuadtreeNodePosition pos) {
        System.out.println(pad(depth) + "node " + pos + ":");
    }
    
    @Override
    public void visitLeaf(final Color leaf, final int depth, final QuadtreeNodePosition pos) {
        System.out.println(pad(depth) + "leaf " + pos + ": " + leaf);
    }
    
    @Override
    public void leaveNode(final int depth, final QuadtreeNodePosition pos) {
    }
    
    @Override
    public void leaveLeaf(final Color leaf, final int depth, final QuadtreeNodePosition pos) {
    }
}
*/
