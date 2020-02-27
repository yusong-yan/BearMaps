# BearMaps

BearMaps is a web map application for Berkeley. Similiar to google map, users can search certain place and find shortest
path to their destination with detailed instructions.

### Implemented files

#### [AStar](bearmaps/AStar/AStarSolver.java) [KDtree](bearmaps/KDtree_PQ/KDTree.java) [PriorityQueue](bearmaps/KDtree_PQ/ArrayHeapMinPQ.java)
  - Data structure and Graph algrithom for finding shortest path
#### [Router](bearmaps/Map/Router.java)
  - Utilized Graph algrithom AStar to find shortest path
  - After showing the route, method "routeDirection" provide detailed turn-by-turn navigation
#### [MyTriesSet](BearMap/bearmaps/KDtree_PQ/MyTrieSet.java)
  - Implemented Tries data structure for autocomplete of searching location
#### [AugmentedStreetMapGraph](BearMap/bearmaps/Map/AugmentedStreetMapGraph.java)
  - Utilized KD-tree data structure in order to obtain close vertex which is very important to find shortest path
#### [APIRouteHandler](BearMap/bearmaps/Map/server/handler/impl/RasterAPIHandler.java)
- Present map by calculating longtitude and latitude
# ![1](https://raw.githubusercontent.com/yusong-yan/bearMap/master/BearMap/Screen%20Shot%202020-02-24%20at%208.43.52%20PM.png)
# ![3](https://raw.githubusercontent.com/yusong-yan/bearMap/master/BearMap/Screen%20Shot%202020-02-24%20at%208.46.52%20PM.png)
# ![2](https://raw.githubusercontent.com/yusong-yan/bearMap/master/BearMap/Screen%20Shot%202020-02-24%20at%208.47.53%20PM.png)

