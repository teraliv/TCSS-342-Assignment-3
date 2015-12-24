/*
 *  Digraph.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 3 - WordNet.
 *  Alex Terikov (teraliv@uw.edu)
 *  12/8/15
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * A class to represent Directed Acyclic Graph.
 */
public class Digraph {

    // The HashMap of vertices and it ids.
    private Map<Integer, Vertex> vertexMap = new HashMap<>();

    // The file name containing Digraph data to read.
    private String fileName;


    /**
     * Construcs a new Digraph.
     *
     * @param theFileName a file name with digraph data.
     */
    public Digraph (String theFileName) {
        if (theFileName == null)
            throw new NullPointerException("Provided file does not exist");

        fileName = theFileName;
        makeDigraph();
    }


    /**
     * A method to ged a vertex from the HashMap. If the vertex
     * does not exist in HashMap add a new one.
     *
     * @param theVertexID The Id of the vertex.
     * @return Returns vertex from a HashMap or a new one.
     */
    public Vertex getVertex(Integer theVertexID) {
        Vertex v = vertexMap.get(theVertexID);

        if (v == null) {
            v = new Vertex(theVertexID);
            vertexMap.put(theVertexID, v);
        }

        return  v;
    }

    /**
     * A method to get a vertex from the HashMap without adding it if it
     * doesn't exist.
     *
     * @param theVertexId The ID of the vertex.
     * @return Returns vertex from HashMap or null if it not there.
     */
    public Vertex getVertexWithoutAdding(int theVertexId) {
        return vertexMap.get(theVertexId);
    }

    /**
     * A method to make an Edge between two vertices.
     *
     * @param sourceID A source vertex.
     * @param destID A destination vertex.
     */
    public void addEdge(Integer sourceID, Integer destID) {
        Vertex v = getVertex(sourceID);
        Vertex w = getVertex(destID);
        v.adj.add(new Edge(w));
    }

    /**
     * A method to get vertex map.
     *
     * @return Returns vertex map.
     */
    public Map<Integer, Vertex> getVertexMap() {
        return vertexMap;
    }

    /**
     * This methods reads the digraph data from the file and creates Digraph.
     */
    private void makeDigraph() {
        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(new FileInputStream(fileName));

            String line;
            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine();

                StringTokenizer st = new StringTokenizer(line, ",");

                int[] tokens = new int[st.countTokens()]; // array to keep tokens data
                int counter = 0;

                // add tokens data into array
                while (st.hasMoreTokens()) {
                    tokens[counter++] = Integer.parseInt(st.nextToken());
                }


                int source = tokens[0]; // first token from hypernyms.txt
                int dest; // all succeeding tokens from hypernyms.txt

                for (int i = 1; i < tokens.length; i++) {
                    dest = tokens[i];
                    addEdge(source, dest);
                }
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
        finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }

}
