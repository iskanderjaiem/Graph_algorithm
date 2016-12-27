
import com.djikistra.Dijkstra;
import com.djikistra.Edge;
import com.djikistra.Vertex;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public Test() {

        /* System.out.println("******TEST 3 FINAL  *****");
        
         System.out.println("******TEST 2***** ");
         test2();*/
    }

    public static String calcul(List<Vertex> vlist, String from, String to) {

        List<Vertex> vertexList = vlist;
        Vertex[] vertices = new Vertex[vertexList.size()];
        for (int i = 0; i < vertexList.size(); i++) {
            System.out.println("\nXXXXX VERTEX SIZE =  " + vertexList.get(i).getEdges().length);
            vertices[i] = vertexList.get(i);
        }

        Dijkstra d = new Dijkstra();
        d.computePaths(vertexList.get(Vertex.getIndexFromVertexList(vertexList, from)));//depart

        List<Vertex> path = d.getShortestPathTo(vertexList.get(Vertex.getIndexFromVertexList(vertexList, to)));

        String str = new String();

        // System.out.println("===> " + CanvasJpanel.vertexList.toString());
        // System.out.println("*******\n****** " + Vertex.showEdgesOfVertexList(CanvasJpanel.vertexList));
        // str +="===> " + CanvasJpanel.vertexList.toString();
        str += "********************************************* "
                + Vertex.showEdgesOfVertexList(CanvasJpanel.vertexList);

        str += "\n************* MEILLEUR CHEMIN ************";
        str += "\nDistance de " + vertexList.get(Vertex.getIndexFromVertexList(vertexList, from)).getName() + " à " + vertexList.get(Vertex.getIndexFromVertexList(vertexList, to)).getName() + ": " + vertexList.get(Vertex.getIndexFromVertexList(vertexList, to)).minDistance + " Km";
        str += "\nChemin: ";
        for (int i = 0; i < path.size(); i++) {
            str += path.get(i).getName();
            if (i < path.size() - 1) {
                str += " => ";
            }
        }
        System.out.println(str);

        return str;
    }

    public void test2() {
        List<Vertex> vertexList = new ArrayList<Vertex>();
        vertexList.add(new Vertex("Redvile"));
        vertexList.add(new Vertex("Blueville"));
        vertexList.add(new Vertex("Greenville"));
        vertexList.add(new Vertex("Orangeville"));
        vertexList.add(new Vertex("Purpleville"));
        vertexList.add(new Vertex("Blackville"));

        /* 
         vertexList.get(Vertex.getIndexFromVertexList(vertexList,"nomdep")).addAdjancie(new Edge(vertexList.get(Vertex.getIndexFromVertexList(vertexList,"arr")), 5));
         */
        vertexList.get(0).addAdjancie(new Edge(vertexList.get(1), 5));
        vertexList.get(0).addAdjancie(new Edge(vertexList.get(2), 10));
        vertexList.get(0).addAdjancie(new Edge(vertexList.get(3), 8));

        vertexList.get(1).addAdjancie(new Edge(vertexList.get(0), 5));
        vertexList.get(1).addAdjancie(new Edge(vertexList.get(2), 3));
        vertexList.get(1).addAdjancie(new Edge(vertexList.get(4), 7));

        vertexList.get(2).addAdjancie(new Edge(vertexList.get(0), 10));
        vertexList.get(2).addAdjancie(new Edge(vertexList.get(1), 3));
        vertexList.get(2).addAdjancie(new Edge(vertexList.get(5), 7));

        vertexList.get(3).addAdjancie(new Edge(vertexList.get(0), 8));
        vertexList.get(3).addAdjancie(new Edge(vertexList.get(4), 2));

        vertexList.get(4).addAdjancie(new Edge(vertexList.get(1), 7));
        vertexList.get(4).addAdjancie(new Edge(vertexList.get(3), 2));
        vertexList.get(4).addAdjancie(new Edge(vertexList.get(5), 15));

        vertexList.get(5).addAdjancie(new Edge(vertexList.get(2), 7));
        vertexList.get(5).addAdjancie(new Edge(vertexList.get(4), 15));
        vertexList.get(5).addAdjancie(new Edge(vertexList.get(5), 7));
        @SuppressWarnings("unused")

        Vertex[] vertices = {vertexList.get(0), vertexList.get(1), vertexList.get(2), vertexList.get(3), vertexList.get(4), vertexList.get(5)};
        Dijkstra d = new Dijkstra();
        d.computePaths(vertexList.get(0));//depart

        String str;

        List<Vertex> path = d.getShortestPathTo(vertexList.get(5));

        System.out.println("Path: " + path);

    }
    /*
     public void test1() {
     List<Vertex> vertexList = new ArrayList<Vertex>();
     vertexList.add(new Vertex("Redvile"));
     vertexList.add(new Vertex("Blueville"));
     vertexList.add(new Vertex("Greenville"));
     vertexList.add(new Vertex("Orangeville"));
     vertexList.add(new Vertex("Purpleville"));
     vertexList.add(new Vertex("Blackville"));

     vertexList.get(0).adjacencies = new Edge[]{new Edge(vertexList.get(1), 5), new Edge(vertexList.get(2), 10), new Edge(vertexList.get(3), 8)};
     vertexList.get(1).adjacencies = new Edge[]{new Edge(vertexList.get(0), 5), new Edge(vertexList.get(2), 3), new Edge(vertexList.get(4), 7)};
     vertexList.get(2).adjacencies = new Edge[]{new Edge(vertexList.get(0), 10), new Edge(vertexList.get(1), 3), new Edge(vertexList.get(5), 7)};
     vertexList.get(3).adjacencies = new Edge[]{new Edge(vertexList.get(0), 8), new Edge(vertexList.get(4), 2)};
     vertexList.get(4).adjacencies = new Edge[]{new Edge(vertexList.get(1), 7), new Edge(vertexList.get(3), 2), new Edge(vertexList.get(5), 15)};
     vertexList.get(5).adjacencies = new Edge[]{new Edge(vertexList.get(2), 7), new Edge(vertexList.get(4), 15)};
     @SuppressWarnings("unused")
     Vertex[] vertices = {vertexList.get(0), vertexList.get(1), vertexList.get(2), vertexList.get(3), vertexList.get(4), vertexList.get(5)};
     Dijkstra d = new Dijkstra();
     d.computePaths(vertexList.get(0));//depart

     System.out.println("Distance to " + vertexList.get(5) + ": " + vertexList.get(5).minDistance);
     List<Vertex> path = d.getShortestPathTo(vertexList.get(5));
     System.out.println("Path: " + path);

     }*/
}
