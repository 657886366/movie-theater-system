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
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Cinemas extends Application {

	 private final String cinemasName;
	 private final TableView<MovieDetails> table = new TableView<>();
	 private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();
           final HBox hb = new HBox();
           
    public Cinemas(String n) {
    	cinemasName = n;
    }

   @Override
   public void start(Stage stage) {
	   Scene scene = new Scene(new Group(),Color.SEASHELL);
       stage.setWidth(680);
       stage.setHeight(600);


       
       TableColumn firstCol = new TableColumn("Movie Name");
       firstCol.setMinWidth(180);
       firstCol.setCellValueFactory( new PropertyValueFactory<>("movieName"));
       
       TableColumn secondCol = new TableColumn("Rating");
       secondCol.setMinWidth(120);
       secondCol.setCellValueFactory( new PropertyValueFactory<>("rating"));

       TableColumn thirdCol = new TableColumn("Show Time");
       thirdCol.setMinWidth(300);
       thirdCol.setCellValueFactory( new PropertyValueFactory<>("time"));

      
       try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT cinemas.title, cinemas.time,cinemas.location, movies.rating "
				  		+ "from cinemas, movies where cinemas.title = movies.title ");
		
			 
			  while(re.next()) {
				 if(cinemasName.equals(re.getString("location"))) {	
						 data.add(new MovieDetails(re.getString("title"),re.getString("rating"),re.getString("time")));
	
				 }
				 
		 
			  }
	  
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}

   
       table.setItems(data);
       table.getColumns().addAll(firstCol, secondCol,thirdCol);
       
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
   
       private final SimpleStringProperty movieName;
       private final SimpleStringProperty rating;
       private final SimpleStringProperty time;

       private MovieDetails(String col1, String col2,String col3) {  
           this.movieName = new SimpleStringProperty(col1);
           this.rating = new SimpleStringProperty(col2);
           this.time = new SimpleStringProperty(col3);
       }

      
       public String getMovieName() {
           return movieName.get();
       }

       public void setMovieName(String col2) {
    	   movieName.set(col2);
       }
       
       public String getRating() {
           return rating.get();
       }

       public void setRating(String col2) {
    	   rating.set(col2);
       }
       
       public String getTime() {
           return time.get();
       }

       public void setTime(String col3) {
       	time.set(col3);
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