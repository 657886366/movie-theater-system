package FinalProject4;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import FinalProject4.ShowMovie.MovieDetails;
import FinalProject4.ShowMovie.SelectionListener;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class AllCinemas extends Application {

	private static final String Dialogs = null;
	final TextField fld1 = new TextField();
	final TextField fld2 = new TextField();
	final TextField fld3 = new TextField();
	final Label labelX =  new Label("Enter X: ");
	final Label labelY =  new Label("Enter Y: ");
	final Label labelR =  new Label("Enter radius: ");
	
	final Label label1 =  new Label("Search Cinemas: ");
	final Button btn =  new Button("  Search ");

	
	 private final TableView<MovieDetails> table = new TableView<>();
	 private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();
           final HBox hb = new HBox();
           
    

   @Override
   public void start(Stage stage) {
	   Scene scene = new Scene(new Group(),Color.SEASHELL);
       stage.setTitle("Cinemas List");
       stage.setWidth(550);
       stage.setHeight(600);
    
       TableColumn firstCol = new TableColumn("Cinemas Name");
       firstCol.setMinWidth(240);
       firstCol.setCellValueFactory( new PropertyValueFactory<>("cinemasName"));
       
       TableColumn secondCol = new TableColumn("Address X");
       secondCol.setMinWidth(100);
       secondCol.setCellValueFactory( new PropertyValueFactory<>("addressX"));
       
       TableColumn thirdCol = new TableColumn("Address Y");
       thirdCol.setMinWidth(100);
       thirdCol.setCellValueFactory( new PropertyValueFactory<>("addressY"));



      
       try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
			  while(re.next()) {
		
						 data.add(new MovieDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y")));
			  }
	  
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}

       table.setMaxHeight(300);
       table.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
       table.setItems(data);
       table.getColumns().addAll(firstCol,secondCol,thirdCol);
       
     
       fld1.setPromptText("X Value");
       fld1.setMaxWidth(80);
       
       fld2.setPromptText("Y Value");
       fld2.setMaxWidth(80);
       
       fld3.setPromptText("Radius");
       fld3.setMaxWidth(80);
       label1.setFont(new Font("Time", 18));
     
       
       HBox hb2 = new HBox();
       hb2.getChildren().addAll(labelX,fld1,labelY,fld2,labelR,fld3);
       hb2.setSpacing(8);
       
    
     
     
  
       btn.setOnAction((ActionEvent e) -> {
  
    	   data.clear();
    	   int x=0;
    	   int y=0;
    	   int z=0;
    	   try {
    		   x = Integer.parseInt(fld1.getText());
        	   y = Integer.parseInt(fld2.getText());
        	   z = Integer.parseInt(fld3.getText());
    		  
    		}catch (NumberFormatException e1) {
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Warning!");
    			alert.setHeaderText(null);
    			alert.setContentText("Please enter an integer, try again! ");
    			alert.showAndWait();
    		}
    	   
		   
    	   try {  
           	Connection conn = getConnection();
           	Statement st = conn.createStatement();
 			ResultSet re = st.executeQuery("SELECT * FROM project4.location");
 				while(re.next()) {
 				   int a = Integer.parseInt(re.getString("X"));
 				   int b = Integer.parseInt(re.getString("Y"));
 			   
 				   if(a>=(x-z) && a<=(x+z) && b>=(x-z) && b<=(x+z))
 					  data.add(new MovieDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y")));
 			   	   		 		
 				   if(x == 0 && y== 0 && z ==0){
 					  data.add(new MovieDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y")));
 				   }
 					
 				 }
 			}catch(Exception ex){
 				System.out.println("ERROR: " + ex.getMessage());
 			}
    	   
    	   fld1.clear();
    	   fld2.clear();
    	   fld3.clear();
       
        });
      
       hb.setSpacing(10);
       final VBox vbox = new VBox();
       vbox.setSpacing(20);
       vbox.setPadding(new Insets(30, 30, 0, 30));
       vbox.getChildren().addAll(table, hb,label1,hb2,btn);
     
       
  

       ((Group) scene.getRoot()).getChildren().addAll(vbox);

       stage.setScene(scene);
       stage.show();
   }

  
  
   public static class MovieDetails {
   
       private final SimpleStringProperty cinemasName;
       private final SimpleStringProperty addressX;
       private final SimpleStringProperty addressY;
    

       private MovieDetails(String col1, String col2, String col3) {  
           this.cinemasName = new SimpleStringProperty(col1);
           this.addressX = new SimpleStringProperty(col2);
           this.addressY = new SimpleStringProperty(col3);
       }
         

       public String getCinemasName() {
           return cinemasName.get();
       }

       public void setCinemasName(String col2) {
    	   cinemasName.set(col2);
       }
       public String getAddressX() {
           return addressX.get();
       }

       public void setAddressX(String col2) {
       	addressX.set(col2);
       }
       
       public String getAddressY() {
           return addressY.get();
       }

       public void setAddressY(String col2) {
       	addressY.set(col2);
       }
   
   }
   
   public class SelectionListener implements ChangeListener<MovieDetails> {
		public void changed(ObservableValue<? extends MovieDetails> observable, MovieDetails oldValue,MovieDetails newValue) {
			System.out.println(newValue.getCinemasName());
			
			Stage menuStage = new Stage();
			Cinemas cinemas = new Cinemas(newValue.getCinemasName());
			cinemas.start(menuStage);
            menuStage.show();	
		}
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