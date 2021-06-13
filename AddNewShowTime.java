package FinalProject4;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddNewShowTime extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(),Color.SEASHELL);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setTitle(" Add New Show Time ");

        final Label label =  new Label("Choose Movie ");
        label.setFont(new Font("Arial", 18));
  
        final ComboBox<String> movieName = new ComboBox();  
        movieName.setMinWidth(180);
        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
			  while(re.next()) {
				  movieName.getItems().addAll(re.getString("title"));
			
			  }
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}

        movieName.valueProperty().addListener(
        		new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
		        	
		        		System.out.println(	movieName.getValue());
		            } 
        		}
        );
        
        
        final Label label2 =  new Label("Choose Cinemas");
        label2.setFont(new Font("Arial", 18));
  
        final ComboBox<String> cinemasName = new ComboBox();   
        cinemasName.setMinWidth(180);
        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
			  while(re.next()) {
				  cinemasName.getItems().addAll(re.getString("cinemas"));
			
			  }
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}

        
        cinemasName.valueProperty().addListener(
        		new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
		        		System.out.println(	cinemasName.getValue());
		        		
		            } 
        		}
        );
        
        
        
        final Label label3 =  new Label("Choose ShowTime");
        label3.setFont(new Font("Arial", 18));
  
        final ComboBox<String> showTime = new ComboBox();   
        showTime.setMinWidth(180);
        showTime.getItems().addAll("06:00","06:30","07:00","07:30","08:00","08:30",
        		"09:00", "09:30","10:00", "10:30","11:00","11:30","12:00","12:30","13:00",
        		"13:30","14:00", "14:30","15:00","15:30","16:00","16:00","17:00",
        		"17:30","18:00","19:00", "19:30","20:00","20:30","21:00","21:30",
        		"22:00","22:30","23:00","23:30");
   
        
        showTime.valueProperty().addListener(
        		new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
		        		System.out.println(	showTime.getValue());
		            } 
        		}
        );
 
 

        
        
        final VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(30, 0, 0, 30));
        vbox1.getChildren().addAll(label,movieName,label2,cinemasName,label3,showTime);
    
 

        final Button btn = new Button("Add");
        btn.setFont(new Font("Arial", 18));
        btn.setMinWidth(80);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	try {
          		  Connection conn = getConnection();
          		Statement st = conn.createStatement();
  			    ResultSet re = st.executeQuery("SELECT * FROM project4.cinemas");
  
  			  while(re.next()) {
  		
  					if(re.getString("location").equals(cinemasName.getValue()) && re.getString("title").equals(movieName.getValue()) ) {
  					   String query =  "UPDATE cinemas SET time = ? WHERE location = ? AND title = ?";
  				
  					   String time=re.getString("time")+ " "+showTime.getValue();
  						String[] splited = time.split(" ");
  						Arrays.sort(splited);

  						StringBuilder sb = new StringBuilder();
  					
  						for(String sp: splited) {
  							sb.append(sp);
  							sb.append(" ");
  						}
  							
  					String concatTime = sb.toString();
  						
			
  						
       			      PreparedStatement preparedStmt = conn.prepareStatement(query);
       			      
       			      preparedStmt.setString (1, concatTime);
       			      preparedStmt.setString (2, cinemasName.getValue());
       			      preparedStmt.setString (3, movieName.getValue());
       			      preparedStmt.execute();
       			      conn.close();
       			      
       				   Alert alert = new Alert(AlertType.INFORMATION);
                		   alert.setHeaderText(null);
                		   alert.setContentText("Congrats, you have inserted!");
                		   alert.showAndWait();
                		   stage.close();
                		   
                		   return;
                		  
  					}
  	
  			  }

          	
          	} catch (Exception ex) {
          		System.out.println("ERROR: " + ex.getMessage());
          	}
            	
            	
            	try {
            		  Connection conn = getConnection();

            		  String query = " insert into cinemas (location, title, time)"
            			        + " values (?, ?, ?)";

            			      PreparedStatement preparedStmt = conn.prepareStatement(query);
            			      preparedStmt.setString (1, cinemasName.getValue());
            			      preparedStmt.setString (2, movieName.getValue());
            			      preparedStmt.setString (3, showTime.getValue());
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
        vbox2.setPadding(new Insets(30, 0, 0, 40));
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



