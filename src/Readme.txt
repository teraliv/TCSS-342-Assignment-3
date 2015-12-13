/******************************************************************************
 *  Name: Alex Terikov       
 
******************************************************************************/


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/
For sysets I have used two HashMaps. One HashMap to store original ordering where key is Integer representing the sysnet ID and value is String representing synset noun. Another HashMap with opposite key value sequence where key is synset noun and value is synset id. The reason why I have chosen HashMap as the data structure is to have immidiate access to the key value sequence which does not requred iteration.


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/
For hypernyms I have dicided to use HashMap and LinkedList. The reason was to have immidiate access to Vertex by its name what in our case is sysnet id. Reading through the hypernyms file I get the hypernym ID and add it to the HashMap. ID works as an key and a value is a Vertex object with its ID. If the hypernym is already in the map I just update the list of its ancestors. If it does not in the HashMap add a new hypernym.




/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. What is the order of growth of
 *  the running time of your methods as a function of the number of
 *  vertices V and the number of edges E in the digraph? What is the
 *  order of growth of the best-case running time?
 *
 *  
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't
 *  forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description: To find shortest common ancestor I am using Depht-First Search algorightm. The reason is because the DFS has an advantage to traverse as fas as you can throgh the pass. While traversing the pass to the root I make an ArrayList of all visited ancestors for every hypernym. Then I compare ancestors for both hypernyms and get common ones. As soon as I get a list of common ancestors calculate the distance for every hypernym and every ancestor and find the shortest path.

                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)				  O(1)			  O((|V|^5 + |E|^5) + n^2)

ancestor(int v, int w)				  O(1)			  O((|V|^3 + |E|^3) + n^2)



/******************************************************************************
Output:
1. Edges of DAG from small sample file:
cat input.txt 
1,0
2,0
0
3,1
4,1
5,1
7,3
8,3
9,5
10,5
11,10
12,10

2. SAP ancestor and length between two noun ID’s:
java SAP hypernyms.txt                
2 3
sap = 11, ancestor = 18840

java SAP hypernyms.txt 
14 15
sap = 2, ancestor = 34093

3. Sematic Relatedness between two nouns passed as console input:
java WordNet 15_May_Organization 1750s
Ancestor = abstraction abstract_entity
Length = 11

java WordNet 1870s 1900s
Ancestor = decade decennary decennium
Length = 2
 *****************************************************************************/
