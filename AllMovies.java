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

public class AllMovies extends Application {
	private final TableView<MovieDetails> table = new TableView<>();
	 private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();
            final HBox hb = new HBox();
         
 
    @Override
    public void start(Stage stage) {
    	  Scene scene = new Scene(new Group(),Color.SEASHELL);
        stage.setWidth(500);
        stage.setHeight(600);
        stage.setTitle("Movie List");

        TableColumn firstCol = new TableColumn("Movie Name");
        firstCol.setMinWidth(200);
        firstCol.setCellValueFactory( new PropertyValueFactory<>("movieName"));
 
        TableColumn secondCol = new TableColumn("Rating");
        secondCol.setMinWidth(200);
        secondCol.setCellValueFactory( new PropertyValueFactory<>("rating"));

        
        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
			  while(re.next()) {
				  data.add(new MovieDetails(re.getString("title"),re.getString("rating")));
			  }
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}
        
        
        table.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
        table.setItems(data);
        table.getColumns().addAll(firstCol, secondCol);

        hb.setSpacing(10);
        
        
        final Label label =  new Label("Filter by rating: ");
        label.setFont(new Font("Arial", 18));
  
        final ComboBox<String> ratingComboBox = new ComboBox();   
        ratingComboBox.getItems().addAll("ALL","G", "PG", "PG-13", "R", "NC-17");
        ratingComboBox.setValue("All");
        
        ratingComboBox.valueProperty().addListener(
        		new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
			            data.clear();
			            try {
			    			  Connection conn = getConnection();
			    			  Statement st = conn.createStatement();
			    			  ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
			    			  while(re.next()) {
			    				  if(t1.equals("ALL")) {
			    					  data.add(new MovieDetails(re.getString("title"),re.getString("rating")));
			    				  }
			    			  }
			    		}catch(Exception ex){
			    			System.out.println("ERROR: " + ex.getMessage());
			    		}

			            try {
			            	Connection conn = getConnection();
			            	Statement st = conn.createStatement();
			  			  	ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
			  				while(re.next()) {
			  					if(t1.equals(re.getString("rating"))) {
			  						 data.add(new MovieDetails(re.getString("title"),re.getString("rating")));
			  					}
			  				 }
			  			}catch(Exception ex){
			  				System.out.println("ERROR: " + ex.getMessage());
			  			}
			           
		            } 
        		}
        );
 
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        vbox.getChildren().addAll(label,ratingComboBox);
        vbox.getChildren().addAll(table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
   public class SelectionListener implements ChangeListener<MovieDetails> {
		public void changed(ObservableValue<? extends MovieDetails> observable, MovieDetails oldValue,MovieDetails newValue) {
			System.out.println(newValue.getMovieName());
			
			Stage menuStage = new Stage();
			ShowMovie showMovie = new ShowMovie(newValue.getMovieName());
			showMovie.start(menuStage);
            menuStage.show();	
		}
    }
   
    public static class MovieDetails {
 
   
    	private final SimpleStringProperty movieName;
        private final SimpleStringProperty rating;
 
        private MovieDetails(String col1, String col2) {
            this.movieName = new SimpleStringProperty(col1);
            this.rating = new SimpleStringProperty(col2);
        }
 
        public String getMovieName() {
            return movieName.get();
        }
 
        public void setMovieName(String col1) {
        	movieName.set(col1);
        }
 
        public String getRating() {
            return rating.get();
        }
 
        public void setRating(String col2) {
        	rating.set(col2);
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