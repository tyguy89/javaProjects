/*
Tyler Boechler
 */

package com.example.a3tjb404;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Application controller class
 */
public class AppController {
    private InteractionModel iModel;
    private SMModel model;

    enum State {READY, PREPARE_CREATE, DRAGGING, MAKING_LINK, DRAGGING_LINK, PANNING}

    State currentState = State.READY;

    double prevX, prevY;
    double dX, dY;

    public AppController() {}

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    public void setModel(SMModel model) {
        this.model = model;
    }

    //MOUSE EVENTS STATE MACHINE

    public void handlePressed(MouseEvent event) {
        double mouseX = iModel.viewToWorldX(event.getX());
        double mouseY = iModel.viewToWorldY(event.getY());

        if (currentState == State.READY) {
            if (model.nodes.size() == 0) {
                currentState = State.PREPARE_CREATE;
            }
            else {
                if (model.links.size() != 0) {
                    for (SMTransitionLink link : model.links) {
                        if (mouseX > link.boxX && mouseX < link.boxX + link.width && mouseY > link.boxY && mouseY < link.boxY + link.height) {
                            //Check collision with link
                            currentState = State.DRAGGING_LINK;
                            iModel.setSelected(link);
                            prevX = mouseX;
                            prevY = mouseY;
                            return;
                        }
                    }
                }
                AtomicBoolean flag = new AtomicBoolean(true);
                model.nodes.forEach(smStateNode -> {

                    if (iModel.selectedTool.id == 0) {
                        if (mouseX > smStateNode.x && mouseX < smStateNode.x + smStateNode.width && mouseY > smStateNode.y && mouseY < smStateNode.y + smStateNode.height) {
                            //check collision with node
                            iModel.setSelected(smStateNode);
                            prevX = mouseX;
                            prevY = mouseY;
                            currentState = State.DRAGGING;
                            flag.set(false);
                        } else {
                            if (iModel.getSelectedObject() == null) {
                                currentState = State.PREPARE_CREATE;
                            }
                        }
                    }
                    else if (iModel.selectedTool.id == 2) {
                        //Add link
                        flag.set(false);
                        if (mouseX > smStateNode.x && mouseX < smStateNode.x + smStateNode.width && mouseY > smStateNode.y && mouseY < smStateNode.y + smStateNode.height) {
                            //collision
                            iModel.setSelected(model.addTransitionLink(mouseX, mouseY, (SMStateNode) smStateNode));
                            model.finalizeTransitionLink((SMTransitionLink) iModel.getSelectedObject());
                            prevX = mouseX;
                            prevY = mouseY;
                            currentState = State.MAKING_LINK;
                            model.addLine((SMTransitionLink) iModel.getSelectedObject(), mouseX, mouseY);

                        }
                    }
                });
                if(flag.get()) {
                    iModel.unselect();
                    //clicked off
                }
            }

            }

            if (iModel.selectedTool.id == 1) {
                prevX = mouseX;
                prevY = mouseY;
                currentState = State.PANNING;

            }

        }


        public void handleDragged (MouseEvent event){
            double mouseX = iModel.viewToWorldX(event.getX());
            double mouseY = iModel.viewToWorldY(event.getY());
            if (iModel.selectedTool.id == 0) {
                if (currentState == State.DRAGGING) {
                    dX = mouseX - prevX;
                    dY = mouseY - prevY;
                    prevX = mouseX;
                    prevY = mouseY;
                    if (iModel.getSelectedObject() != null) {
                        model.moveNode((SMStateNode) iModel.getSelectedObject(), dX, dY);
                    }
                }
                else if (currentState == State.DRAGGING_LINK) {
                    dX = mouseX - prevX;
                    dY = mouseY - prevY;
                    prevX = mouseX;
                    prevY = mouseY;
                    if (iModel.getSelectedObject() != null) {
                        model.moveLink((SMTransitionLink) iModel.getSelectedObject(), dX, dY);
                    }
                }

            }
            else if (iModel.selectedTool.id == 1) {
                if (currentState == State.PANNING) {
                    dX = mouseX - prevX;
                    dY = mouseY - prevY;
                    prevX = mouseX;
                    prevY = mouseY;
                    iModel.changeLocation(dX/2, dY/2);

                }
            }
            else {
                if (currentState == State.MAKING_LINK) {
                    SMTransitionLink link = (SMTransitionLink) iModel.getSelectedObject();
                    model.addLine(link, mouseX, mouseY);
                }
            }

        }

        public void handleReleased (MouseEvent event){
            double mouseX = iModel.viewToWorldX(event.getX());
            double mouseY = iModel.viewToWorldY(event.getY());
            if (iModel.selectedTool.id == 0) {
                switch (currentState) {
                    case PREPARE_CREATE -> {
                        iModel.setSelected(model.addNode(mouseX, mouseY));
                        currentState = State.READY;
                    }

                    case DRAGGING, DRAGGING_LINK -> {
                        currentState = State.READY;
                    }
                }

            }
            else if (iModel.selectedTool.id == 1) {
                if (this.currentState == State.PANNING) {
                    currentState = State.READY;
                }
            }

            else {
                //Check if made new link and create it
                AtomicBoolean flag = new AtomicBoolean(true);
                if (currentState == State.MAKING_LINK) {
                    model.nodes.forEach(smStateNode -> {
                        if (mouseX > smStateNode.x && mouseX < smStateNode.x + smStateNode.width && mouseY > smStateNode.y && mouseY < smStateNode.y + smStateNode.height) {
                            SMTransitionLink link = (SMTransitionLink) iModel.getSelectedObject();

                            model.connectNodes(link, smStateNode);
                            iModel.setSelected(link);
                            flag.set(false);
                        }
                        currentState = State.READY;

                    });
                    if (flag.get()) {
                        model.deletePartialLink((SMTransitionLink) iModel.getSelectedObject());
                        iModel.unselect();
                        currentState = State.READY;
                    }
                }
            }
        }


        //OTHER EVENTS


        public void deleteItem (KeyEvent d){
            if (d.getCode().equals(KeyCode.DELETE)) {
                if (iModel.getSelectedObject() != null && model.nodes != null && model.nodes.size() != 0) {
                    if (iModel.getSelectedObject().getClass().getName().equals("com.example.a3tjb404.SMStateNode")) {
                        model.deleteNode((SMStateNode) iModel.getSelectedObject());
                    }
                    else {

                        model.deleteFullLink((SMTransitionLink) iModel.getSelectedObject());
                    }
                }

                iModel.unselect();
                currentState = State.READY;
            }
        }

    public void renameNodeText(KeyEvent keyEvent, String text, SMStateNode node) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            model.updateNodeTitle(node, text);
            iModel.setSelected(node);
            currentState = State.READY;
        }
    }

    public void renameTransitionText(KeyEvent keyEvent, String text, SMTransitionLink link) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            model.updateLinkEvent(link, text);
            iModel.setSelected(link);
            currentState = State.READY;
        }
    }

    public void renameEffects(KeyEvent keyEvent, String text, SMTransitionLink link) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            model.updateLinkEffects(link, text);
            iModel.setSelected(link);
            currentState = State.READY;
        }
    }
    public void renameContext(KeyEvent keyEvent, String text, SMTransitionLink link) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            model.updateLinkContext(link, text);
            iModel.setSelected(link);
            currentState = State.READY;
        }
    }
    public void renameEffects(ActionEvent actionEvent, String text, SMTransitionLink link) {
        model.updateLinkEffects(link, text);
        iModel.setSelected(link);
        currentState = State.READY;
    }
    public void renameContext(ActionEvent actionEvent, String text, SMTransitionLink link) {
        model.updateLinkContext(link, text);
        iModel.setSelected(link);
        currentState = State.READY;
    }
    public void renameTransitionText(ActionEvent actionEvent, String text, SMTransitionLink link) {
        model.updateLinkEvent(link, text);
        iModel.setSelected(link);
        currentState = State.READY;
    }
}