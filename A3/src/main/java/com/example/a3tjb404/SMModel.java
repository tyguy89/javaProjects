/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

import java.util.ArrayList;

/**
 * Model for state objects
 */
public class SMModel {

    public ArrayList<SMStateNode> nodes;
    private ArrayList<ModelListener> subscribers;
    public ArrayList<SMTransitionLink> links;

    public ArrayList<SelectableObject> objects = new ArrayList<>();

    private SelectableObject lastNode;


    public SMModel() {
        //initialize arraylists
        nodes = new ArrayList<SMStateNode>();
        links = new ArrayList<>();
        subscribers = new ArrayList<ModelListener>();
    }

    /**
     * adds new state node
     * @param x
     * @param y
     * @return new node
     */
    public SMStateNode addNode(double x, double y) {

        lastNode = new SMStateNode(x, y, 80, 60);
        nodes.add((SMStateNode) lastNode);
        objects.add(lastNode);
        notifySubscribers();
        objectExists();

        return (SMStateNode) lastNode;
    }

    /**
     * Adds new transition link
     * @param x
     * @param y
     * @param start start node
     * @return
     */
    public SMTransitionLink addTransitionLink(double x, double y, SMStateNode start) {
        return (new SMTransitionLink(x, y, start));
    }

    /**
     * add new link as object
     * @param link
     */
    public void finalizeTransitionLink(SMTransitionLink link) {
        links.add(link);
        notifySubscribers();
    }

    /**
     * Link wasnt completed, delete it
     * @param link
     */
    public void deletePartialLink(SMTransitionLink link) {
        links.remove(link);
        notifySubscribers();
    }


    /**
     * Delete node off delete key press, delete all attached transitions
     * @param node the node
     */
    public void deleteNode(SMStateNode node) {
        int counter = links.size()-1;
        //iterate without invalid indices
        while (counter > 0) {
            if(links.get(counter).startNode.equals(node)) {
                links.remove(counter);
                counter--;
            }
            if(links.get(counter).endNode.equals(node)) {
                links.remove(counter);

            }
            counter--;
        }

        //get any missed, in case
        for (SMTransitionLink link: links) {
            if(link.startNode.equals(node)) {
                links.remove(link);
                break;
            }
            if(link.endNode.equals(node)) {
                links.remove(link);
                break;
            }
        }
        nodes.remove(node);
        notifySubscribers();
    }

    /**
     * Deletes link
     * @param link
     */
    public void deleteFullLink(SMTransitionLink link) {
        links.remove(link);
        notifySubscribers();
    }

    /**
     * Add the line for link object
     * @param link
     * @param x
     * @param y
     */
    public void addLine(SMTransitionLink link, double x, double y) {
        link.addLine(x, y);
        notifySubscribers();
    }

    /**
     * Connect two nodes together in link
     * @param link
     * @param node
     */
    public void connectNodes(SMTransitionLink link, SMStateNode node) {
        link.connection(node);
        link.set(120, 100);
        objects.add(link);
        link.boxX = link.x+(link.x2-link.x)/3;
        link.boxY = link.y+(link.y2-link.y)/3;
        notifySubscribers();
    }

    /**
     * Move node object, move attached lines!
     * @param object
     * @param dX
     * @param dY
     */
    public void moveNode(SMStateNode object, double dX, double dY) {
        object.clickedAndDragged(dX, dY);
        for (SMTransitionLink link: links) {
            if (link.startNode.equals(object)) {
               link.updateStart(dX, dY);
            }
            if (link.endNode.equals(object)) {
                link.updateEnd(dX, dY);

            }
        }
        notifySubscribers();
    }

    /**
     * Move link object
     * @param link
     * @param dx
     * @param dy
     */
    public void moveLink(SMTransitionLink link, double dx, double dy) {
        link.moveBox(dx, dy);
        notifySubscribers();
    }

    /**
     * Application resized
     */
    public void resize() {
        notifySubscribers();
    }


    public void addSubscriber(ModelListener m) {
        subscribers.add(m);
    }

    private void notifySubscribers() {subscribers.forEach(ModelListener::modelChanged);}

    private void objectExists() {
        subscribers.forEach(ModelListener::modelNotNull);
    }

    public void updateNodeTitle(SMStateNode node, String text) {
        node.title = text;
        notifySubscribers();
    }
    public void updateLinkEvent(SMTransitionLink link, String text) {
        link.boxEvent = text;
        notifySubscribers();
    }
    public void updateLinkContext(SMTransitionLink link, String text) {
        link.boxContext = text;
        notifySubscribers();
    }
    public void updateLinkEffects(SMTransitionLink link, String text) {
        link.boxEffects = text;
        notifySubscribers();
    }
}
