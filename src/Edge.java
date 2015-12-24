/*
 *  Edge.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 3 - WordNet.
 *  Alex Terikov (teraliv@uw.edu)
 *  12/8/15
 */

/**
 * A class to represent an Edge from one vertex to another.
 */
public class Edge {

    // destination Vertex.
    public Vertex dest;

    /**
     * Constructs a new edge to the vertex.
     *
     * @param theDestination The destination vertex.
     */
    public Edge (Vertex theDestination) {
        dest = theDestination;
    }

    /**
     * Returns the destination vertex.
     *
     * @return Returns the destination vertex.
     */
    public Vertex getDest() {
        return dest;
    }
}
