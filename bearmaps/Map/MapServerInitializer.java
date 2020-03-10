package bearmaps.Map;

import bearmaps.Map.server.handler.APIRouteHandler;
import bearmaps.Map.utils.Constants;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static spark.Spark.*;


public class MapServerInitializer {



    public static void initializeServer(Map<String, APIRouteHandler> apiHandlers){

        Constants.SEMANTIC_STREET_GRAPH = new AugmentedStreetMapGraph(Constants.OSM_DB_PATH);
        staticFileLocation("/page");

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
        });

        Set<String> paths = new HashSet<>();
        for(Map.Entry<String, APIRouteHandler> apiRoute: apiHandlers.entrySet()){
            if(paths.contains(apiRoute.getKey())){
                throw new RuntimeException("Duplicate API Path found");
            }
            get("/"+apiRoute.getKey(), apiRoute.getValue());
            paths.add(apiRoute.getKey());
        }


    }
}
