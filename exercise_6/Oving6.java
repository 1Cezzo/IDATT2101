import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Oving6 {
    public static void main(String[] args) {
        Graph g = createGraph("exercise_6/testdata.txt"); //replace with desired filename
        System.out.println(g.runBFS(g.nodes[5], g)); //replace 5 with desired start node
    }

    public static Oving6.Graph createGraph(String filePath) {
        try (Scanner file = new Scanner(new File(filePath))) {
            int numberOfNodes = Integer.parseInt(file.nextLine().split(" ")[0]);
            Graph graph = new Graph(numberOfNodes);
            while (file.hasNextLine()) {
                String line = file.nextLine().trim();
                String[] fromTo = line.split("\\s+");
                if (fromTo.length == 2) {
                    int from = Integer.parseInt(fromTo[0]);
                    int to = Integer.parseInt(fromTo[1]);
                    graph.addEdge(from, to);
                } else {
                    System.out.println("Invalid line: " + line);
                }
            }
            return graph;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }
    
    private static class Graph {
        private Node[] nodes;

        public Graph(int amountOfNodes) {
            nodes = new Node[amountOfNodes];
            for (int i = 0; i < amountOfNodes; i++) {
                nodes[i] = new Node(i, null);
            }
        }
        
        public void addEdge(int fromNode, int toNode) {
            nodes[fromNode].addEdge(toNode);
        }

        public StringBuilder runBFS(Node startNode, Graph graph) {
            boolean[] visited = new boolean[graph.nodes.length];
            int[] distance = new int[graph.nodes.length];
            int[] predecessor = new int[graph.nodes.length];
            Queue<Node> queue = new LinkedList<>();
        
            queue.add(startNode);
            visited[startNode.number] = true;
            distance[startNode.number] = 0;
        
            int maxNodeWidth = String.valueOf("Node:").length();
            int maxPredecessorWidth = String.valueOf("Predecessor:").length();
            int maxDistanceWidth = String.valueOf("Distance:").length();
            
            while (!queue.isEmpty()) {
                Node currentNode = queue.poll();
        
                Edge currentEdge = currentNode.firstEdge;
                while (currentEdge != null) {
                    int neighborNodeNumber = currentEdge.toNode;
                    if (!visited[neighborNodeNumber]) {
                        queue.add(graph.nodes[neighborNodeNumber]);
                        visited[neighborNodeNumber] = true;
                        distance[neighborNodeNumber] = distance[currentNode.number] + 1;
                        predecessor[neighborNodeNumber] = currentNode.number;
                    }
                    currentEdge = currentEdge.next;
                }
            } 
        
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-" + (maxNodeWidth + 1) + "s%-" + (maxPredecessorWidth + 1) + "s%-" + (maxDistanceWidth + 1) + "s%n", "Node:", "Predecessor:", "Distance:"));
            for (int i = 0; i < graph.nodes.length; i++) {
                sb.append(String.format("%-" + (maxNodeWidth + 1) + "d%-" + (maxPredecessorWidth + 1) + "d%-" + (maxDistanceWidth + 1) + "d%n", i, predecessor[i], distance[i]));
            }
            return sb;
        }        
    }

    private static class Node {
        private Edge firstEdge;
        private int number;

        public Node(int number, Edge firstEdge) {
            this.number = number;
            this.firstEdge = firstEdge;
        }

        public void addEdge(int to) {
            if (firstEdge == null) {
                firstEdge = new Edge(null, to);
            } else {
                Edge newEdge = new Edge(firstEdge, to);
                firstEdge = newEdge;
            }
        }
    }

    private static class Edge {
        private Edge next;
        private int toNode;

        public Edge(Edge next, int toNode) {
            this.next = next;
            this.toNode = toNode;
        }
    }
}