package com.ivan.utils.space.octree;

import com.ivan.utils.space.Point3D;

public interface OctreeVisitor<T extends Point3D> {
	void visitNode(int depth, OctreeNodePosition pos);

	void leaveNode(int depth, OctreeNodePosition pos);

	void visitLeaf(T leaf, int depth, OctreeNodePosition pos);

	void leaveLeaf(T leaf, int depth, OctreeNodePosition pos);
}

/*
// basic octree printer
new OctreeVisitor<Color>() {
    private String pad(final int depth) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
    
    @Override
    public void visitNode(final int depth, final OctreeNodePosition pos) {
        System.out.println(pad(depth) + "node " + pos + ":");
    }
    
    @Override
    public void visitLeaf(final Color leaf, final int depth, final OctreeNodePosition pos) {
        System.out.println(pad(depth) + "leaf " + pos + ": " + leaf);
    }
    
    @Override
    public void leaveNode(final int depth, final OctreeNodePosition pos) {
    }
    
    @Override
    public void leaveLeaf(final Color leaf, final int depth, final OctreeNodePosition pos) {
    }
}
*/
