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

public class Main extends Application {
	
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(),Color.SEASHELL);
        stage.setWidth(500);
        stage.setHeight(460);
        stage.setTitle("Movie System");


 
        final Button movies = new Button("Movies");
        movies.setStyle("-fx-font: 24 arial; -fx-base: #b6e7c9;");
        movies.setMinSize(140, 80);
    
        
        final Button cinemas = new Button("Cinemas");
        cinemas.setStyle("-fx-font: 24 arial; -fx-base: #b6e7c9;");
        cinemas.setMinSize(140, 80);
  
  
   
        final HBox hbox = new HBox();
        hbox.setSpacing(40);
        hbox.setPadding(new Insets(80, 0, 0, 60));
        hbox.getChildren().addAll(movies,cinemas);
      
        
        final PasswordField text = new PasswordField();
        text.setPromptText("Eneter password");
        text.setStyle("-fx-font: 14 arial; -fx-base: #BDB76B;");
        
        
        text.setMaxWidth(140);
        final Button admin = new Button("Admin");
        admin.setStyle("-fx-font: 22 arial; -fx-base: #BDB76B;");
        admin.setMinSize(140, 40);
        
  
        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(0, 0, 0, 140));
        vbox.getChildren().addAll(text,admin);
        

        final VBox vbox2 = new VBox();
        vbox2.setSpacing(80);
        vbox2.setPadding(new Insets(0, 0, 0, 30));
        vbox2.getChildren().addAll(hbox,vbox);
        
        
        
        movies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                	Stage menuStage = new Stage();
                	AllMovies movies = new AllMovies();
                	movies.start(menuStage);
                	menuStage.show();
            }
 		
        });
        
        
        cinemas.setOnAction((ActionEvent e) -> {
        	Stage menuStage = new Stage();
        	AllCinemas allCinemas = new AllCinemas();
        	allCinemas.start(menuStage);
        	menuStage.show();
        
         });
       
        
        
        admin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
         	   
         	   if(text.getText().equals("admin")) {
                	Stage menuStage = new Stage();
                	Admin admin = new Admin();
                	admin.start(menuStage);
                	menuStage.show();
                	
                }
         	   else {
         		   Alert alert = new Alert(AlertType.INFORMATION);
         		   alert.setHeaderText(null);
         		   alert.setContentText("Wrong password. Please try again.");
         		   alert.showAndWait();
         	   }
         		   
     
            }
 		
        });
       

 
        ((Group) scene.getRoot()).getChildren().addAll(vbox2);
 
        stage.setScene(scene);
        stage.show();
    }
 

   
   
    
    public static void main(String[] args) {
        launch(args);
    }
} 