/*
 *  SAP.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 3 - WordNet.
 *  Alex Terikov (teraliv@uw.edu)
 *  12/8/15
 */

import java.util.*;

/**
 * A class to represent Shortest Ancestral Path in a Digraph.
 */
public class SAP {

    // The digraph.
    private Digraph dag;

    /**
     * Constructs a new shortest ancestral path object.
     *
     * @param theDag The Digraph for the SAP.
     */
    public SAP(Digraph theDag) {
        dag = theDag;
    }


    /**
     * Length of ancestral path between v and w;
     *
     * @param theV The vertex V that participates in a length.
     * @param theW The vertex W that participates in a length.
     * @return Returns a shortest length between V and W or -1 if no such path.
     */
    public int length(int theV, int theW) {

        int result = -1;
        int lca = ancestor(theV, theW);

        if (lca != -1) {
            int distanceV = getDistance(theV, lca);
            int distanceW = getDistance(theW, lca);

            result = distanceV + distanceW;
        }

        return result;
    }


    /**
     * Method to get a common ancestor of V and W that
     * participates in a shortest ancestral path.
     *
     * @param theV The vertex V that participates in a SAP.
     * @param theW The vertex W that participates in a SAP.
     * @return Returns the ancestor of V and W. -1 if no such path.
     */
    public int ancestor(int theV, int theW) {

        int result;

        List<Vertex> ancestorsV = getAncestors(theV);
        List<Vertex> ancestorsW = getAncestors(theW);

        // common ancestors from v and w
        List<Integer> ancestors = new ArrayList<>();

        // find common ancestors
        for (Vertex v : ancestorsV) {
            for (Vertex w : ancestorsW) {
                //if (v.id == w.id) {
                if (v.id.equals(w.id)) {

                    // do not add common ancestors that appear multiple times
                    if (!ancestors.contains(v.id))
                        ancestors.add(v.id);
                }
            }
        }

        result = lca(ancestors, theV, theW);

        return result;
    }

    // find least common ancestor from the list

    /**
     * A method to find least common ancestor (LCA) from the list
     * of common ancestors of V and W.
     *
     * @param theAncestors The list of common ancestors.
     * @param theV The vertex V that participates in a SAP.
     * @param theW The vertex W that participates in a SAP.
     * @return Returns the LCA. -1 if no such ancestor.
     */
    private int lca(List<Integer> theAncestors, int theV, int theW) {
        if (theAncestors == null)
            throw new NullPointerException("List of ancestor is missing");

        if (dag.getVertexWithoutAdding(theV) == null || dag.getVertexWithoutAdding(theW) == null)
            throw new NoSuchElementException("One or both vertices are not found.");

        int lca = -1;
        int distance = 0;
        int distanceV, distanceW;

        for (int i = 0; i < theAncestors.size(); i++) {
            distanceV = getDistance(theV, theAncestors.get(i));
            distanceW = getDistance(theW, theAncestors.get(i));

            if (i == 0) {
                distance = distanceV + distanceW;
                lca = theAncestors.get(i);
            }
            if (distance > distanceV + distanceW) {
                distance = distanceV + distanceW;
                lca = theAncestors.get(i);
            }
        }

        return lca;
    }

    /**
     * A method to ge a traverse distance between a vertex V
     * and its ancestor.
     *
     * @param theBegin The start vertex.
     * @param theEnd The end vertex.
     * @return Return the travetse distance.
     */
    private int getDistance(int theBegin, int theEnd) {

        Vertex start = dag.getVertexWithoutAdding(theBegin);

        if (start == null)
            throw new NoSuchElementException("Start vertex not found!");

        Stack<Vertex> stack = new Stack<>();
        stack.push(start);
        start.dist = 0;

        List<Integer> distances = new ArrayList<>();
        Vertex v, w;

        int result = -1;

        do {
            v = stack.pop();

            if (v.id == theEnd)
                distances.add(v.dist);

            // traverse till the destination, not root
            if (v.id != theEnd) {
                for (int i = 0; i < v.adj.size(); i++) {
                    w = v.adj.get(i).getDest();
                    w.dist = v.dist + 1;
                    w.prev = v;
                    stack.push(w);
                }
            }
        }
        while (!stack.isEmpty());

        // find the minimum distance from the list of all distances
        for (int i = 0; i < distances.size(); i++) {
            if (i == 0)
                result = distances.get(i);

            if (result > distances.get(i))
                result = distances.get(i);
        }

        return result;
    }


    /**
     * A method to get all ancestor of vertex V that
     * participate in traverse from V to Root.
     *
     * @param theID The vertex to find its ancestors.
     * @return Returns the list of all ancestors of vertex V.
     */
    private List<Vertex> getAncestors(int theID) {

        List<Vertex> visited = new ArrayList<>();

        Stack<Vertex> stack = new Stack<>();
        stack.push(dag.getVertex(theID));
        Vertex v;

        do {
            v = stack.pop();
            visited.add(v);

            for (int i = 0; i < v.adj.size(); i++)
                stack.push(v.adj.get(i).getDest());
        }
        while (!stack.isEmpty());

        return visited;
    }

    /**
     * Unit tests for SAP.
     */
    public static void main(String[] args) {

        if (args.length == 0)
            throw new IndexOutOfBoundsException("File was not provided");

        Digraph dag = new Digraph(args[0]);
        SAP sap = new SAP(dag);

        System.out.println("Enter two vertex IDs or -1 to exit");

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        StringTokenizer st = new StringTokenizer(line);
        int v, w, length, ancestor;

        while (st.countTokens() == 2) {
            v = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            if (v == -1 || w == -1)
                return;

            length = sap.length(v, w);
            ancestor = sap.ancestor(v, w);
            System.out.printf("sap = %d, ancestor = %d\n\n", length, ancestor);

            line = in.nextLine();
            st = new StringTokenizer(line);
        }
    }
}
