# BearMaps

BearMaps is a web map application for Berkeley. Similiar to google map, users can search certain place and find shortest
path to their destination with detailed instructions.

### Implemented files
#### [MyTriesSet](BearMap/bearmaps/proj2ab/MyTrieSet.java)
  - Implemented Tries data structure for autocomplete of searching location
#### [Router](BearMap/bearmaps/proj2c/Router.java)
  - Find Shortest Path by implementing AI algrithom AStar
  - show route direction
#### [AugmentedStreetMapGraph](BearMap/bearmaps/proj2c/AugmentedStreetMapGraph.java)   [StreetMapGraph](BearMap/bearmaps/AStar/streetmap/StreetMapGraph.java)
  - Utilized KD-tree data structure in order to obtain close vertex which is very important to find shortest path
#### [APIRouteHandler](BearMap/bearmaps/proj2c/server/handler/impl/RasterAPIHandler.java)
- Present map by calculating longtitude and latitude
# ![1](https://raw.githubusercontent.com/yusong-yan/bearMap/master/BearMap/Screen%20Shot%202020-02-24%20at%208.43.52%20PM.png)
# ![3](https://raw.githubusercontent.com/yusong-yan/bearMap/master/BearMap/Screen%20Shot%202020-02-24%20at%208.46.52%20PM.png)
# ![2](https://raw.githubusercontent.com/yusong-yan/bearMap/master/BearMap/Screen%20Shot%202020-02-24%20at%208.47.53%20PM.png)

