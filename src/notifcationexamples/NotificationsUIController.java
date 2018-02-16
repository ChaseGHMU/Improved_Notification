/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import notifcationexamples.NotificationStates.States;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable, StatesInterface {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button task1Button;
    
    @FXML
    private Button task2Button;
    
    @FXML
    private Button task3Button;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    private States task1State = States.STOP;
    private States task2State = States.STOP;
    private States task3State = States.STOP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null){ 
                    task1.end();
                    task1State = States.STOP;
                }
                if (task2 != null){ 
                    task2.end();
                    task2State = States.STOP;
                }
                if (task3 != null){ 
                    task3.end();
                    task3State = States.STOP;
                }
            }
        });
    }
    
    
    @FXML
    public void handleTask1(ActionEvent event){
        if(task1State == States.STOP){
            startTask1(event);
        }else{
            endTask1(event);
        }
    }
    
    @FXML
    public void handleTask2(ActionEvent event){
        if(task2State == States.STOP){
            startTask2(event);
        }else{
            endTask2(event);
        }
    }
    
    @FXML
    public void handleTask3(ActionEvent event){
        if(task3State == States.STOP){
            startTask3(event);
        }else{
            endTask3(event);
        }
    }
    
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.setStateTarget(this);
            task1.start();
            task1Button.setText("Stop Task 1");
            task1State = States.START;
        }
    }
    
    public void endTask1(ActionEvent event){
        task1.end();
        state1Finished();
    }
    
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setStatesTarget(this);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
            });
            
            task2.start();
            task2Button.setText("Stop Task 2");
            task2State = States.START;
        }        
    }
    public void endTask2(ActionEvent event){
        task2.end();
        state2Finished();
    }
    
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            task3.setStatesTarget(this);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
            task3Button.setText("Stop Task 3");
            task3State = States.START;
        }
    } 
    
    public void endTask3(ActionEvent event){
        task3.end();
        state3Finished();
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
        }
        textArea.appendText(message + "\n");
    }
    
    @Override
    public void state1Finished(){
        task1State = States.STOP;
        task1Button.setText("Start Task 1");
        task1 = null;
    }
    
    @Override
    public void state2Finished(){
        task2Button.setText("Start Task 2");
        task2State = States.STOP;
        task2 = null;
    }
    
    @Override
    public void state3Finished(){
        task3Button.setText("Start Task 3");
        task3State = States.STOP;
        task3 = null;
    }
}
