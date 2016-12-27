package com.djikistra;

import java.util.List;

public class Vertex implements Comparable<Vertex> {

    public String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex() {
        adjacencies = new Edge[]{};
    }

      public Vertex(String argName,Edge[] adjacencies,double minDistance,Vertex previous) {
        this.name = argName;
        this.adjacencies = adjacencies;
        this.minDistance = minDistance;
        this.previous = previous;
    }

      
    public String getName() {
        return name;
    }
 public Edge[] getEdges() {
        return adjacencies;
    }
    public boolean addAdjancie(Edge edgeToAdd) {
        Edge[] tab = new Edge[adjacencies.length + 1];
        for (int i = 0; i < adjacencies.length; i++) {
            tab[i] = adjacencies[i];
        }

        tab[tab.length - 1] = edgeToAdd;
        adjacencies = tab;
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vertex(String argName) {
        this.name = argName;
        adjacencies = new Edge[]{};
    }

    @Override
    public String toString() {
        return "City [name=" + name + "]";
    }

    public int compareTo(Vertex other) {

        return Double.compare(minDistance, other.minDistance);
    }

    public static int getIndexFromVertexList(List<Vertex> listVertex, String str) {
        for (int i = 0; i < listVertex.size(); i++) {
            if (listVertex.get(i).getName().equals(str)) {
                return i;
            }
        }
        return 0;
    }
    
    public static String showEdgesOfVertexList(List<Vertex> listVertex){
        String str = new String();
        for (int i=0;i<listVertex.size();i++){
              str+="\n De "+listVertex.get(i).getName();
            for (int j=0;j<listVertex.get(i).getEdges().length;j++){
               str+= " à ["+listVertex.get(i).getEdges()[j].target.getName()+
                       " => "+listVertex.get(i).getEdges()[j].weight+" Km ] ";
            }str+="";
        }
        
        return str;
    }
}
