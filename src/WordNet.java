/*
 *  WordNet.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 2 - WordNet.
 *  Alex Terikov (teraliv@uw.edu)
 *  12/8/15
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class to represent WordNet.
 */
public class WordNet {

    // The HashMap to store WordNet noun and its ID.
    private Map<String, Integer> nounsMap;

    // The HashMap to store WordNet ID and a noun associate to it.
    private Map<Integer, String> nounsID;

    // Digraph.
    private Digraph dag;

    // The shortest ancestral path of a WordNet.
    private SAP sap;


    /**
     * Construcs a new WordNet object.
     *
     * @param synsets The synsets file name with all nouns.
     * @param hypernyms The hypernyms file name that contains the hypernym relations.
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new NullPointerException("Bad file name(s)");

        nounsMap = new HashMap<>();
        nounsID = new HashMap<>();

        dag = new Digraph(hypernyms);
        sap = new SAP(dag);

        if (!isRootedDag())
            throw new IllegalArgumentException("No Root in DAG");

        readSynsets(synsets);
    }

    /**
     * A method to return all WordNet nouns.
     *
     * @return Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {
        return nounsMap.keySet();
    }

    /**
     * A method to check if the word a WordNet noun.
     *
     * @param word The name of the word.
     * @return Returns true if it is noun, otherwise false.
     */
    public boolean isNoun(String word) {
        return nounsMap.containsKey(word);
    }

    /**
     * A method to get distance between nounA and nounB. Distance
     * is the minimum length of any ancestral path between any
     * synset v of A and any synset w of B.
     *
     * @param nounA The first word that participates in a distance.
     * @param nounB The second word that participates in a distance.
     * @return Returns the distance between nounA and nounB.
     */
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new NullPointerException("noun(s) not found");

        int idA = nounsMap.get(nounA);
        int idB = nounsMap.get(nounB);

        int distance = sap.length(idA, idB);

        return  distance;
    }

    /**
     *  A method to get a synset that is the common ancestor
     *  of nounA and nounB in a shortest ancestral path.
     *
     * @param nounA The first word that participates in a distance.
     * @param nounB The second word that participates in a distance.
     * @return Returns common ancestor of noun A and B.
     */
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new NullPointerException("noun(s) not found");

        int idA = nounsMap.get(nounA);
        int idB = nounsMap.get(nounB);

        String ancestor = nounsID.get(sap.ancestor(idA, idB));

        return ancestor;
    }

    /**
     * A helper method to read sysets data.
     *
     * @param synsets The sysets file name.
     */
    private void readSynsets(String synsets) {
        Scanner fs = null;

        try {
            fs = new Scanner(new FileInputStream(synsets));

            String line;
            while (fs.hasNextLine()) {
                line = fs.nextLine();

                StringTokenizer st = new StringTokenizer(line, ",");

                int id = Integer.parseInt(st.nextToken());
                String noun = st.nextToken();

                nounsMap.put(noun, id);
                nounsID.put(id, noun);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
        finally {
            if (fs != null) {
                fs.close();
            }
        }
    }


    private boolean isRootedDag() {

        boolean foundRoot = false;

        for (Vertex v : dag.getVertexMap().values()) {
            if (v.adj.size() == 0)
                foundRoot = true;
        }

        return foundRoot;
    }


    /**
     * Unit test for WordNet.
     */
    public static void main(String[] args) {

        System.out.println();

        //WordNet wordnet = new WordNet("sample.txt", "input.txt");
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");

        String nounA = args[0];
        String nounB = args[1];

        String ancestor = wordnet.sap(nounA, nounB);
        int length = wordnet.distance(nounA, nounB);

        System.out.printf("Ancestor = %s\nLength = %d\n", ancestor, length);

    }

}
