package bearmaps.Map;


import bearmaps.Map.server.handler.APIRouteHandlerFactory;


public class MapServer {



    public static void main(String[] args) {

        MapServerInitializer.initializeServer(APIRouteHandlerFactory.handlerMap);

    }

}
