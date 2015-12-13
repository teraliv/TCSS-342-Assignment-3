/*
 *  Vertex.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 2 - WordNet.
 *  Alex Terikov (teraliv@uw.edu)
 *  12/8/15
 */

import java.util.LinkedList;
import java.util.List;

/**
 * A clss to represent Vertex in a Digraph.
 */
public class Vertex {

    // The ID or name of the vertex.
    public Integer id;

    // The list of edges or ancestors of this vertex.
    public List<Edge> adj;

    // The previous vertex in a digraph traverse.
    public Vertex prev;

    // The distance number of current vertex in a digraph traverse.
    public Integer dist;


    /**
     * Constructs a new Vertex of a Digraph.
     *
     * @param theId the ID or name of the vertex.
     */
    public Vertex(Integer theId) {
        id = theId;
        adj = new LinkedList<>();
        prev = null;
        dist = 0;
    }
}
