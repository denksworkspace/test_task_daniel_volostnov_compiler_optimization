## Algorithm analysis

We will solve the task separately for each cargo type. Let the current cargo be C. Consider a station FN. We can deliver cargo C to it if and only if there exists a vertex ST such that:

1) cargo C can be present at ST  
2) ST is reachable from s0  
3) there exists a path from ST to FN that preserves cargo C

### Notes

- ST is reachable from at least one vertex where cargo C was loaded; therefore, as initial ST we should consider only those vertices where cargo C is loaded.

- Suppose we are considering a vertex ST and it has an edge to a station K where unloading of C happens, then there is no sense in going through this edge.
  
  **Proof**  
  Suppose there exists a path from ST to FN passing through K that preserves cargo C. Then there exists a path from K to FN that preserves cargo C, and since at station K it was unloaded, it means there exists a path from K to FN passing through ST', therefore there exists a path from ST' to FN not passing through K. Since we consider all ST vertices, going to K is not optimal. Proof is complete.

- Suppose we are considering a vertex ST and it has an edge to a station ST' where unloading of C happens, then there is no sense in going through this edge.
  
  **Proof**  
  Since we consider all ST, then either ST' has already been considered, and therefore all paths passing through it, or ST' will be considered and will cover these paths later. Proof is complete.

### Algorithm

1) Using DFS, find all vertices reachable from s0 (complexity O(S + T)).

2) Iterate all vertices reachable from s0 and update the answer for load cargo, launching a DFS that does not traverse edges leading to stations where unloading/loading/possible presence of cargo C happens  
   (“possibility of presence” of cargo C leads to the fact that each vertex will be considered no more than 1 time).  
   Thus the amortized complexity is O(S + T) * K, where K is the number of cargo types; it can be estimated as O(S), total complexity O(S^2 + ST).

All structures in the code are arrays and hash tables, operations on which take O(1) / amortized O(1). Therefore, the complexity of the whole algorithm is O(S^2 + ST). However, since all cargoes are considered independently, they can be computed in parallel and achieve complexity O((S^2 + ST)/P), where P is the number of threads.
