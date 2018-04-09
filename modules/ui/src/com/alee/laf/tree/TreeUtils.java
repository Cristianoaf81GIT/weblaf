/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.laf.tree;

import com.alee.extended.tree.AsyncTreeModel;
import com.alee.extended.tree.walker.AsyncTreeWalker;
import com.alee.laf.tree.walker.SimpleTreeWalker;
import com.alee.laf.tree.walker.TreeWalker;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * This class provides a set of utilities for trees.
 * This is a library utility class and its not intended for use outside of the trees.
 *
 * @author Mikle Garin
 */

public final class TreeUtils
{
    /**
     * todo 1. Change this static utility class into {@link TreeState}-related class
     * todo 2. Proper state restoration for {@link com.alee.extended.tree.WebAsyncTree}
     */

    /**
     * Returns tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree tree to process
     * @return tree expansion and selection states
     */
    public static TreeState getTreeState ( final JTree tree )
    {
        return getTreeState ( tree, true );
    }

    /**
     * Returns tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree          tree to process
     * @param saveSelection whether to save selection states or not
     * @return tree expansion and selection states
     */
    public static TreeState getTreeState ( final JTree tree, final boolean saveSelection )
    {
        return getTreeState ( tree, tree.getModel ().getRoot (), saveSelection );
    }

    /**
     * Returns tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree tree to process
     * @param root node to save state for
     * @return tree expansion and selection states
     */
    public static TreeState getTreeState ( final JTree tree, final Object root )
    {
        return getTreeState ( tree, root, true );
    }

    /**
     * Returns tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree          tree to process
     * @param saveSelection whether to save selection states or not
     * @param root          node to save state for
     * @return tree expansion and selection states
     */
    public static TreeState getTreeState ( final JTree tree, final Object root, final boolean saveSelection )
    {
        if ( !( root instanceof UniqueNode ) )
        {
            throw new RuntimeException ( "To get tree state you must use UniqueNode or any class that extends it as tree elements" );
        }
        final TreeState state = new TreeState ();
        saveTreeStateImpl ( tree, state, ( UniqueNode ) root, saveSelection );
        return state;
    }

    /**
     * Saves tree expansion and selection states into {@link TreeState}.
     *
     * @param tree          tree to process
     * @param state         {@link TreeState} to save states into
     * @param parent        node to save states for
     * @param saveSelection whether to save selection states or not
     */
    protected static void saveTreeStateImpl ( final JTree tree, final TreeState state, final UniqueNode parent,
                                              final boolean saveSelection )
    {
        // Saving children states first
        for ( int i = 0; i < parent.getChildCount (); i++ )
        {
            saveTreeStateImpl ( tree, state, ( UniqueNode ) parent.getChildAt ( i ), saveSelection );
        }

        // Saving parent state
        final TreePath path = new TreePath ( parent.getPath () );
        state.addState ( parent.getId (), tree.isExpanded ( path ), saveSelection && tree.isPathSelected ( path ) );
    }

    /**
     * Restores tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree      tree to process
     * @param treeState tree expansion and selection states
     */
    public static void setTreeState ( final JTree tree, final TreeState treeState )
    {
        setTreeState ( tree, treeState, true );
    }

    /**
     * Restores tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree             tree to process
     * @param treeState        tree expansion and selection states
     * @param restoreSelection whether to restore selection states or not
     */
    public static void setTreeState ( final JTree tree, final TreeState treeState, final boolean restoreSelection )
    {
        setTreeState ( tree, treeState, tree.getModel ().getRoot (), restoreSelection );
    }

    /**
     * Restores tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree      tree to process
     * @param treeState tree expansion and selection states
     * @param root      node to restore state for
     */
    protected static void setTreeState ( final JTree tree, final TreeState treeState, final Object root )
    {
        setTreeState ( tree, treeState, root, true );
    }

    /**
     * Restores tree expansion and selection states.
     * Tree nodes must be instances of UniqueNode class.
     *
     * @param tree             tree to process
     * @param treeState        tree expansion and selection states
     * @param root             node to restore state for
     * @param restoreSelection whether to restore selection states or not
     */
    protected static void setTreeState ( final JTree tree, final TreeState treeState, final Object root, final boolean restoreSelection )
    {
        if ( !( root instanceof UniqueNode ) )
        {
            throw new RuntimeException ( "To set tree state you must use UniqueNode or any class that extends it as tree elements" );
        }
        if ( treeState != null )
        {
            restoreTreeStateImpl ( tree, treeState, ( UniqueNode ) root, restoreSelection );
        }
    }

    /**
     * Restores tree expansion and selection states from {@link TreeState}.
     *
     * @param tree             tree to process
     * @param treeState        tree expansion and selection states
     * @param parent           node to restore states for
     * @param restoreSelection whether to restore selection states or not
     */
    private static void restoreTreeStateImpl ( final JTree tree, final TreeState treeState, final UniqueNode parent,
                                               final boolean restoreSelection )
    {
        // Restoring children states first
        for ( int i = 0; i < parent.getChildCount (); i++ )
        {
            restoreTreeStateImpl ( tree, treeState, ( UniqueNode ) parent.getChildAt ( i ), restoreSelection );
        }

        // Restoring parent state
        final TreePath path = new TreePath ( parent.getPath () );
        if ( treeState.isExpanded ( parent.getId () ) )
        {
            tree.expandPath ( path );
        }
        else
        {
            tree.collapsePath ( path );
        }
        if ( restoreSelection )
        {
            if ( treeState.isSelected ( parent.getId () ) )
            {
                tree.addSelectionPath ( path );
            }
            else
            {
                tree.removeSelectionPath ( path );
            }
        }
    }

    /**
     * Returns appropriate {@link TreeWalker} implementation for the specified {@link JTree}.
     *
     * @param tree {@link JTree} to return appropriate {@link TreeWalker} implementation for
     * @param <N>  tree node type
     * @return appropriate {@link TreeWalker} implementation for the specified {@link JTree}
     */
    public static <N extends TreeNode> TreeWalker<N> getTreeWalker ( final JTree tree )
    {
        final TreeWalker treeWalker;
        if ( tree.getModel () instanceof AsyncTreeModel )
        {
            treeWalker = new AsyncTreeWalker ( tree );
        }
        else
        {
            treeWalker = new SimpleTreeWalker ( tree );
        }
        return ( TreeWalker<N> ) treeWalker;
    }

    /**
     * Returns {@link TreePath} from the root to the specified {@link TreeNode}.
     * The last element in the path is the specified {@link TreeNode}.
     *
     * @param node {@link TreeNode} to get {@link TreePath} for
     * @return {@link TreePath} from the root to the specified {@link TreeNode}
     */
    public static TreePath getTreePath ( final TreeNode node )
    {
        return new TreePath ( getPath ( node ) );
    }

    /**
     * Returns the path from the root, to get to this node.
     * First element in the path is the root and the last element in the path is this node.
     *
     * @param node {@link TreeNode} to get the path for
     * @return array of {@link TreeNode}s representing the path
     */
    public static TreeNode[] getPath ( final TreeNode node )
    {
        return getPathToRoot ( node, 0 );
    }

    /**
     * Builds the parents of node up to and including the root node, where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the tree.
     *
     * @param node  {@link TreeNode} to get the path for
     * @param depth an int giving the number of steps already taken towards the root (on recursive calls), used to size the returned array
     * @return array of {@link TreeNode}s representing the path from the root to the specified {@link TreeNode}
     */
    public static TreeNode[] getPathToRoot ( final TreeNode node, int depth )
    {
        final TreeNode[] path;
        if ( node == null )
        {
            if ( depth == 0 )
            {
                return null;
            }
            else
            {
                path = new TreeNode[ depth ];
            }
        }
        else
        {
            depth++;
            path = getPathToRoot ( node.getParent (), depth );
            path[ path.length - depth ] = node;
        }
        return path;
    }

    /**
     * Returns whether or not {@code anotherNode} is an ancestor of {@code node}.
     * If {@code anotherNode} is {@code null}, this method returns {@code false}.
     * Note that any node is considered as an ancestor of itself.
     * This operation is at worst O(h) where h is the distance from the root to {@code node}.
     *
     * @param node        tested {@code node}
     * @param anotherNode node to test as an ancestor of {@code node}
     * @return {@code true} if {@code anotherNode} is an ancestor of {@code node}, {@code false} otherwise
     */
    public static boolean isNodeAncestor ( final TreeNode node, final TreeNode anotherNode )
    {
        if ( anotherNode != null )
        {
            TreeNode ancestor = node;
            do
            {
                if ( ancestor == anotherNode )
                {
                    return true;
                }
            }
            while ( ( ancestor = ancestor.getParent () ) != null );
        }
        return false;
    }

    /**
     * Returns number of levels above the specified {@code node}.
     * It is basically the distance from the root to the specified {@code node}.
     * Returns {@code 0} if {@code node} is the root.
     *
     * @param node {@code node}
     * @return number of levels above the specified {@code node}
     */
    public static int getLevel ( final TreeNode node )
    {
        int levels = 0;
        TreeNode ancestor = node;
        while ( ( ancestor = ancestor.getParent () ) != null )
        {
            levels++;
        }
        return levels;
    }
}