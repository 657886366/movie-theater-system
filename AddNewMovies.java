package FinalProject4;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import FinalProject4.AllMovies.MovieDetails;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddNewMovies extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(),Color.SEASHELL);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setTitle("Add New Movie");
  
   
        
        final Label label =  new Label("Enter Movie Name ");
        label.setFont(new Font("Arial", 18));
        final TextField text = new TextField();
        text.setMinWidth(200);
      

        
        final Label label2 =  new Label("Choose a Rating");
        label2.setFont(new Font("Arial", 18));
  
        final ComboBox<String> rating = new ComboBox();  
        rating.setMinWidth(200);
        rating. getItems().addAll("G", "PG", "PG-13", "R", "NC-17");
        rating.valueProperty().addListener( new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
		        		
		        		
		        	}
        		});
        

        
        final VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(30, 0, 0, 30));
        vbox1.getChildren().addAll(label,text,label2,rating);
    
 

        final Button btn = new Button("Add");
        btn.setFont(new Font("Arial", 18));
        btn.setMinWidth(80);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

            	try {
            		  Connection conn = getConnection();

            		  String query = " insert into movies (title, rating)"
            			        + " values (?, ?)";

            			      PreparedStatement preparedStmt = conn.prepareStatement(query);
            			      preparedStmt.setString (1, text.getText());
            			      preparedStmt.setString (2, rating.getValue());
            			      preparedStmt.execute();
            			      conn.close();
            			      
            				   Alert alert = new Alert(AlertType.INFORMATION);
                     		   alert.setHeaderText(null);
                     		   alert.setContentText("Congrats, you have inserted!");
                     		   alert.showAndWait();
                     		  stage.close();
                     		   
            	
            	
            	} catch (Exception ex) {
            		System.out.println("ERROR: " + ex.getMessage());
            	}

                
            }
 		
        });
        
        
        
        
        final VBox vbox2 = new VBox();
        vbox2.setSpacing(20);
        vbox2.setPadding(new Insets(30, 0, 0, 30));
        vbox2.getChildren().addAll(btn);
 
        
        final VBox vbox3 = new VBox();
        vbox3.setSpacing(20);
        vbox3.setPadding(new Insets(30, 0, 0, 30));
        vbox3.getChildren().addAll(vbox1,vbox2);
      
        
        
        
        ((Group) scene.getRoot()).getChildren().addAll(vbox3);
 
        stage.setScene(scene);
        stage.show();
    }
 
 
    public static Connection getConnection() {
		  try
		  {
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project4?serverTimezone=UTC","root","Maitian123"); 
		 return conn;
		 }
		  catch(Exception ex)
		  {
		  System.out.println("ERROR: " + ex.getMessage());
		  }
		  return null;
	    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 



