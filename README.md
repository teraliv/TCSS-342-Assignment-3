# TCSS342 WordNet

WordNet is a semantic lexicon for the English language that is used extensively by computational linguists 
and cognitive scientists; for example, it was a key component in IBM's Watson. WordNet groups words 
into sets of synonyms called synsets and describes semantic relationships between them. One such 
relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym 
(more general synset). For example, a plant organ is a hypernym of carrot and plant organ is a hypernym 
of plant root.

WordNet Data: https://wordnet.princeton.edu/

Measuring the semantic relatedness of two nouns. Semantic relatedness refers to the degree to which two concepts are related. 

We define the semantic relatedness of two wordnet nouns A and B as follows:

- distance(A, B) = distance is the minimum length of any ancestral path between any
synset v of A and any synset w of B.

EXAMPLE:  
<b>% java WordNet cat bear</b>  
Ancestor = animal   
Length = 4
