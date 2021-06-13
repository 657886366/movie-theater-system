package FinalProject4;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class ShowMovie extends Application {
	
	 private final String name;
	 private final TableView<MovieDetails> table = new TableView<>();
	 private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();
            final HBox hb = new HBox();
            
     public ShowMovie(String n) {
    	 name = n;
     }
 
    @Override
    public void start(Stage stage) {
    	  Scene scene = new Scene(new Group(),Color.SEASHELL);
        stage.setWidth(680);
        stage.setHeight(600);

 
        
        TableColumn firstCol = new TableColumn("Cinemas");
        firstCol.setMinWidth(180);
        firstCol.setCellValueFactory( new PropertyValueFactory<>("cinemas"));
        
        TableColumn secondCol = new TableColumn("Address X");
        secondCol.setMinWidth(100);
        secondCol.setCellValueFactory( new PropertyValueFactory<>("addressX"));
        
        TableColumn thirdCol = new TableColumn("Address Y");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory( new PropertyValueFactory<>("addressY"));
 
        TableColumn fourthCol = new TableColumn("Show Time");
        fourthCol.setMinWidth(200);
        fourthCol.setCellValueFactory( new PropertyValueFactory<>("time"));


        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT cinemas.title, cinemas.time,cinemas.location, location.x, location.y "
			  		+ "from cinemas, location where cinemas.location = location.cinemas ");
		
		
			  while(re.next()) {

				  
					  if(name.equals(re.getString("title"))) {	
				
						  data.add(new MovieDetails(re.getString("location"),re.getString("x"),re.getString("y"),re.getString("time")));	
		
						 }	 
			  }
			  
		
			
	  
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}

        table.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
        table.setItems(data);
        table.getColumns().addAll(firstCol, secondCol,thirdCol,fourthCol);
        
        hb.setSpacing(10);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        vbox.getChildren().addAll(table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
   
   
    public static class MovieDetails {
    
        private final SimpleStringProperty cinemas;
        private final SimpleStringProperty addressX;
        private final SimpleStringProperty addressY;
        private final SimpleStringProperty time;
 
        private MovieDetails(String col1, String col2,String col3,String col4) {  
            this.cinemas = new SimpleStringProperty(col1);
            this.addressX = new SimpleStringProperty(col2);
            this.addressY = new SimpleStringProperty(col3);
            this.time = new SimpleStringProperty(col4);
        }
 
       
 
        public String getCinemas() {
            return cinemas.get();
        }
 
        public void setCinemas(String col2) {
        	cinemas.set(col2);
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
         
        
        public String getTime() {
            return time.get();
        }
 
        public void setTime(String col3) {
        	time.set(col3);
        }
    }
    
    public class SelectionListener implements ChangeListener<MovieDetails> {
		public void changed(ObservableValue<? extends MovieDetails> observable, MovieDetails oldValue,MovieDetails newValue) {
			System.out.println(newValue.getCinemas());
			
			Stage menuStage = new Stage();
			Cinemas cinemas = new Cinemas(newValue.getCinemas());
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