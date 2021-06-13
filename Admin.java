package FinalProject4;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
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
import javafx.scene.effect.Effect;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Admin extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(),Color.SEASHELL);
        stage.setWidth(500);
        stage.setHeight(480);
        stage.setTitle("Movie Management System");
        

        final Button addCinemas = new Button("Add New Cinema   ");
        addCinemas.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        addCinemas.setMinSize(120, 40);
          
        final Button addMovies = new Button("Add New Movie      ");
        addMovies.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        addMovies.setMinSize(120, 40);
       
  
        final Button addShowtime = new Button("Add New Showtime");
        addShowtime.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        addShowtime.setMinSize(120, 40);

        
        final Button deleteCinemas = new Button("Delete a Cinema     ");
        deleteCinemas.setStyle("-fx-font: 20 arial; -fx-base: #FF7F50;");
        deleteCinemas.setMinSize(120, 40);
       
  
        final Button deleteMovie = new Button("Delete a Movie        ");
        deleteMovie.setStyle("-fx-font: 20 arial; -fx-base: #FF7F50;");
        deleteMovie.setMinSize(120, 40);
        
   
        final VBox vbox = new VBox();
    
        vbox.setSpacing(35);
        vbox.setPadding(new Insets(50, 0, 0, 130));
        vbox.getChildren().addAll(addCinemas,addMovies,addShowtime,deleteCinemas,deleteMovie);
  
        
        addMovies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                	Stage menuStage = new Stage();
                	AddNewMovies movies = new AddNewMovies();
                	movies.start(menuStage);
                	menuStage.show();
            }
 		
        });
        
        
        addCinemas.setOnAction((ActionEvent e) -> {
        	Stage menuStage = new Stage();
        	AddNewCinemas cinemas = new AddNewCinemas();
        	cinemas.start(menuStage);
        	menuStage.show();
        
         });
        
        addShowtime.setOnAction((ActionEvent e) -> {
        	Stage menuStage = new Stage();
        	AddNewShowTime addShowTime = new AddNewShowTime();
        	addShowTime.start(menuStage);
        	menuStage.show();
        
         });
       
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
} 