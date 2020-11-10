// Example Code for parsing XML file
// Dr. Moushumi Sharmin
// CSCI 345

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

public class ParseXML {

    // building a document from the XML file
    // returns a Document object after loading the book.xml file.
    public Document getDocFromFile(String filename) throws ParserConfigurationException {
        {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;

            try {
                doc = db.parse(filename);
                readBoardData(doc);
            } catch (Exception ex) {
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
        } // exception handling

    }

    // reads data from cards file, returns list of cards
    public Card[] readCardData(Document d) {
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");
        Card deck[] = new Card[40];

        for (int i = 0; i < cards.getLength(); i++) {
            // System.out.println("Printing information for card "+(i+1));

            // reads data from the nodes
            Node card = cards.item(i);
            String cardCategory = card.getAttributes().getNamedItem("name").getNodeValue();
            String cardBudget = card.getAttributes().getNamedItem("budget").getNodeValue();

            // System.out.println("Card = " + cardCategory);
            // System.out.println("Budget = " + cardBudget);

            // Initialize each card

            deck[i] = new Card(cardCategory, Integer.parseInt(cardBudget), i + 1);

            // reads data
            NodeList children = card.getChildNodes();
            int index = 0;

            for (int j = 0; j < children.getLength(); j++) {

                Node sub = children.item(j);

                if ("scene".equals(sub.getNodeName())) {
                    String cardNumber = sub.getAttributes().getNamedItem("number").getNodeValue();
                    String scene = sub.getTextContent();

                    // System.out.println("Number = "+cardNumber);
                    // System.out.println("Scene = "+scene);

                    // System.out.println(i);
                    // System.out.println(deck[i].getCardName());
                    deck[i].setSceneDescription(scene);

                } else if ("part".equals(sub.getNodeName())) {
                    NodeList childrenOfChildren = sub.getChildNodes();

                    String partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    String partLevel = sub.getAttributes().getNamedItem("level").getNodeValue();
                    // System.out.println("Part name = " + partName);
                    // System.out.println("Part level = " + partLevel);

                    deck[i].setPartNameLevel(index, Integer.parseInt(partLevel), partName);

                    for (int k = 0; k < childrenOfChildren.getLength(); k++) {
                        Node subOfSub = childrenOfChildren.item(k);

                        if ("area".equals(subOfSub.getNodeName())) {
                            String xVal = subOfSub.getAttributes().getNamedItem("x").getNodeValue();
                            String yVal = subOfSub.getAttributes().getNamedItem("y").getNodeValue();
                            String hVal = subOfSub.getAttributes().getNamedItem("h").getNodeValue();
                            String wVal = subOfSub.getAttributes().getNamedItem("w").getNodeValue();

                            // System.out.println(" X Value = " + xVal);
                            // System.out.println(" Y Value = " + yVal);
                            // System.out.println(" H Value = " + hVal);
                            // System.out.println(" W Value = " + wVal);

                            deck[i].setPartCoords(index, Integer.parseInt(xVal), Integer.parseInt(yVal),
                                    Integer.parseInt(hVal), Integer.parseInt(wVal));

                        } else if ("line".equals(subOfSub.getNodeName())) {
                            String line = subOfSub.getTextContent();
                            // System.out.println(" Line = "+line);
                            // System.out.println();

                            deck[i].setPartLine(index, line);
                        }
                    }
                    index++;
                }
            } // for childnodes
              // System.out.println("\n");
        }
        return deck;
    }

    // reads data from board file and prints data
    public Hashtable<String, Set> readBoardData(Document d) {
        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        NodeList trailer = root.getElementsByTagName("trailer");
        NodeList upgrade = root.getElementsByTagName("office");
        Hashtable<String, Set> dictionary = new Hashtable<String, Set>();

        // Get all sets information
        for (int i = 0; i < sets.getLength(); i++) {
            // System.out.println("Printing information for set "+(i+1));

            // reads data from the nodes
            Node set = sets.item(i);
            String setCategory = set.getAttributes().getNamedItem("name").getNodeValue();
            // System.out.println("Set = " + setCategory);
            Set setInfo = new Set(setCategory);

            // reads data
            NodeList children = set.getChildNodes();

            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);

                if ("neighbors".equals(sub.getNodeName())) {
                    NodeList childOfNeighbors = sub.getChildNodes();
                    // System.out.println(childOfNeighbors.getLength());
                    int index = 0;
                    for (int k = 0; k < childOfNeighbors.getLength(); k++) {
                        Node neigh = childOfNeighbors.item(k);

                        if ("neighbor".equals(neigh.getNodeName())) {
                            String neighborName = neigh.getAttributes().getNamedItem("name").getNodeValue();
                            index++;
                            // System.out.println(neighborName);
                            setInfo.setNeighbors(neighborName, index);
                            setInfo.setNumberOfNeighbors(k + 1);
                        }
                    }
                } else if ("area".equals(sub.getNodeName())) {
                    String areaX = sub.getAttributes().getNamedItem("x").getNodeValue();
                    String areaY = sub.getAttributes().getNamedItem("y").getNodeValue();
                    String areaH = sub.getAttributes().getNamedItem("h").getNodeValue();
                    String areaW = sub.getAttributes().getNamedItem("w").getNodeValue();

                    // System.out.println(areaX);
                    // System.out.println(areaY);
                    // System.out.println(areaH);
                    // System.out.println(areaW);
                    setInfo.setSetArea(Integer.parseInt(areaX), Integer.parseInt(areaY), Integer.parseInt(areaH),
                            Integer.parseInt(areaW));

                } else if ("takes".equals(sub.getNodeName())) {
                    NodeList takes = sub.getChildNodes();

                    for (int k = 0; k < takes.getLength(); k++) {
                        Node take = takes.item(k);
                        if ("take".equals(take.getNodeName())) {
                            String takeNumber = take.getAttributes().getNamedItem("number").getNodeValue();

                            // System.out.println(takeNumber);

                            NodeList area = take.getChildNodes();
                            Node areaVals = area.item(0);

                            String xVal = areaVals.getAttributes().getNamedItem("x").getNodeValue();
                            String yVal = areaVals.getAttributes().getNamedItem("y").getNodeValue();
                            String hVal = areaVals.getAttributes().getNamedItem("h").getNodeValue();
                            String wVal = areaVals.getAttributes().getNamedItem("w").getNodeValue();

                            // System.out.println(" X Value = " + xVal);
                            // System.out.println(" Y Value = " + yVal);
                            // System.out.println(" H Value = " + hVal);
                            // System.out.println(" W Value = " + wVal);

                            setInfo.setTake(Integer.parseInt(takeNumber), Integer.parseInt(xVal),
                                    Integer.parseInt(yVal), Integer.parseInt(hVal), Integer.parseInt(wVal));
                            if (k == 0){
                                setInfo.setShotCounter(Integer.parseInt(takeNumber));
                            }
                            setInfo.setNumberOfTakes(k + 1);
                        }
                    }

                } else if ("parts".equals(sub.getNodeName())) {
                    NodeList parts = sub.getChildNodes();

                    for (int k = 0; k < parts.getLength(); k++) {

                        Node part = parts.item(k);
                        if ("part".equals(part.getNodeName())) {
                            String partName = part.getAttributes().getNamedItem("name").getNodeValue();
                            String partLevel = part.getAttributes().getNamedItem("level").getNodeValue();

                            // System.out.println(partName);
                            // System.out.println(partLevel);

                            setInfo.setPartNameLevel(k, partName, Integer.parseInt(partLevel));

                            NodeList childOfPart = part.getChildNodes();

                            for (int p = 0; p < childOfPart.getLength(); p++) {
                                Node partChild = childOfPart.item(p);

                                if ("area".equals(partChild.getNodeName())) {
                                    String xVal = partChild.getAttributes().getNamedItem("x").getNodeValue();
                                    String yVal = partChild.getAttributes().getNamedItem("y").getNodeValue();
                                    String hVal = partChild.getAttributes().getNamedItem("h").getNodeValue();
                                    String wVal = partChild.getAttributes().getNamedItem("w").getNodeValue();

                                    // System.out.println(" X Value = " + xVal);
                                    // System.out.println(" Y Value = " + yVal);
                                    // System.out.println(" H Value = " + hVal);
                                    // System.out.println(" W Value = " + wVal);

                                    setInfo.setPartArea(k, Integer.parseInt(xVal), Integer.parseInt(yVal),
                                            Integer.parseInt(hVal), Integer.parseInt(wVal));

                                } else if ("line".equals(partChild.getNodeName())) {
                                    String line = partChild.getTextContent();
                                    // System.out.println(line);

                                    setInfo.setPartLine(k, line);
                                }
                            }
                        }
                    }

                }
            } // for childnodes
              // System.out.println("\n");
            dictionary.put(setCategory, setInfo);
        } // for book nodes
        
        // Get trailers information
        Node trailerChild = trailer.item(0);
        NodeList listOfTrailer = trailerChild.getChildNodes();
        Set setInfo = new Set("Trailers");
        for (int i = 0; i < listOfTrailer.getLength(); i++) {
            Node sub = listOfTrailer.item(i);
            if ("neighbors".equals(sub.getNodeName())) {
                NodeList childOfNeighbors = sub.getChildNodes();
                // System.out.println(childOfNeighbors.getLength());
                int index = 0;
                for (int k = 0; k < childOfNeighbors.getLength(); k++) {
                    Node neigh = childOfNeighbors.item(k);

                    if ("neighbor".equals(neigh.getNodeName())) {
                        String neighborName = neigh.getAttributes().getNamedItem("name").getNodeValue();
                        index++;
                        // System.out.println(neighborName);
                        setInfo.setNeighbors(neighborName, index);
                        setInfo.setNumberOfNeighbors(k + 1);
                    }
                }
            } else if ("area".equals(sub.getNodeName())) {
                String areaX = sub.getAttributes().getNamedItem("x").getNodeValue();
                String areaY = sub.getAttributes().getNamedItem("y").getNodeValue();
                String areaH = sub.getAttributes().getNamedItem("h").getNodeValue();
                String areaW = sub.getAttributes().getNamedItem("w").getNodeValue();

                // System.out.println(areaX);
                // System.out.println(areaY);
                // System.out.println(areaH);
                // System.out.println(areaW);
                setInfo.setSetArea(Integer.parseInt(areaX), Integer.parseInt(areaY), Integer.parseInt(areaH),
                        Integer.parseInt(areaW));

            }
        }
        dictionary.put("Trailers", setInfo);

        // Get casting office informatoin
        Node castingChild = upgrade.item(0);
        NodeList listOfCasting = castingChild.getChildNodes();
        Set newSet = new Set("Casting Office");
        for (int i = 0; i < listOfCasting.getLength(); i++) {
            Node sub = listOfCasting.item(i);
            if ("neighbors".equals(sub.getNodeName())) {
                NodeList childOfNeighbors = sub.getChildNodes();
                // System.out.println(childOfNeighbors.getLength());
                int index = 0;
                for (int k = 0; k < childOfNeighbors.getLength(); k++) {
                    Node neigh = childOfNeighbors.item(k);

                    if ("neighbor".equals(neigh.getNodeName())) {
                        String neighborName = neigh.getAttributes().getNamedItem("name").getNodeValue();
                        index++;
                        // System.out.println(neighborName);
                        newSet.setNeighbors(neighborName, index);
                        newSet.setNumberOfNeighbors(k + 1);
                    }
                }
            } else if ("area".equals(sub.getNodeName())) {
                String areaX = sub.getAttributes().getNamedItem("x").getNodeValue();
                String areaY = sub.getAttributes().getNamedItem("y").getNodeValue();
                String areaH = sub.getAttributes().getNamedItem("h").getNodeValue();
                String areaW = sub.getAttributes().getNamedItem("w").getNodeValue();

                // System.out.println(areaX);
                // System.out.println(areaY);
                // System.out.println(areaH);
                // System.out.println(areaW);
                newSet.setSetArea(Integer.parseInt(areaX), Integer.parseInt(areaY), Integer.parseInt(areaH),
                        Integer.parseInt(areaW));

            }
        }
        dictionary.put("Casting Office", newSet);

        return dictionary;
    }
}// class