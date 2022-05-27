package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GoStadium extends Application {
	private Statement stmt;
	private Connection conn;
	private String teamName1;
	private String teamName2;
	private GridPane gpane;
	private Label lblPrice;
	
	private Button btShowSecondWindow = new Button("Comprar Entrades");
	private Button btShowThirdWindow = new Button("Comprar Entrades");
	private ComboBox<String> cboZoneName = new ComboBox<>();
	
	Side prices = new Side();


	@Override
	public void start(Stage primaryStage) {
		try {
			initializeDB();
			getGame();
			getSeat();

			// cboZoneName.setOnAction(e -> getPriceZone(cboZoneName.getValue()));

			cboZoneName.setOnAction(e -> calcPriceZone(cboZoneName.getValue()));

			btShowSecondWindow.setOnAction(e -> showSecondWindow());
			btShowThirdWindow.setOnAction(e -> showThirdWindow());

			gpane = new GridPane();
			gpane.setAlignment(Pos.TOP_LEFT);
			gpane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			gpane.setHgap(5.5);
			gpane.setVgap(5.5);

			gpane.add(new Label("Inici"), 15, 0);

			Image image1 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 100, 100,
					false, false);
			ImageView imageView1 = new ImageView();
			imageView1.setImage(image1);
			imageView1.setX(0);
			imageView1.setY(0);
			gpane.add(new ImageView(image1), 0, 0);

			gpane.add(new Button("Inici"), 0, 4);
			gpane.add(new Label("Maig 2022"), 0, 7);

			Image image2 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 70, 70,
					false, false);
			ImageView imageView2 = new ImageView();
			imageView2.setImage(image2);
			imageView2.setX(0);
			imageView2.setY(0);
			gpane.add(new ImageView(image2), 0, 19);

			Image image3 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/barcelona.png", 70,
					70, false, false);
			ImageView imageView3 = new ImageView();
			imageView3.setImage(image3);
			imageView3.setX(0);
			imageView3.setY(0);
			gpane.add(new ImageView(image3), 7, 19);

			gpane.add(new Label("LocalTeam           VS"), 0, 20);
			gpane.add(new Label(teamName1), 7, 20);
			gpane.add(btShowSecondWindow, 15, 20);

			Image image4 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 70, 70,
					false, false);
			ImageView imageView4 = new ImageView();
			imageView4.setImage(image4);
			imageView4.setX(0);
			imageView4.setY(0);
			gpane.add(new ImageView(image4), 0, 29);

			Image image5 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/madrid.png", 70, 70,
					false, false);
			ImageView imageView5 = new ImageView();
			imageView5.setImage(image5);
			imageView5.setX(0);
			imageView5.setY(0);
			gpane.add(new ImageView(image5), 7, 29);

			gpane.add(new Label("LocalTeam           VS"), 0, 30);
			gpane.add(new Label(teamName2), 7, 30);
			gpane.add(btShowThirdWindow, 15, 30);

			Scene scene = new Scene(gpane, 800, 800);
			primaryStage.setTitle("Inici");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calcPriceZone(String zone) {
		getPriceZone(zone);
		
		lblPrice.setText(String.valueOf(prices.getPrice()));

		System.out.println("Preu abans de pintar-lo: " + lblPrice.getText());
	}

	private void initializeDB() {
		try {
			// 1. crear connexió
			conn = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/GoStadium", "root", "1234");
		} catch (SQLException e) {
			System.out.println("No es connecta!");
			e.printStackTrace();
		}
	}

	private void getGame() throws Exception {
		ResultSet rs = null;
		try {
			// 2. crear objecte statement
			stmt = conn.createStatement();
			// 3. executar SQL
			rs = stmt.executeQuery("SELECT TeamName FROM GAME WHERE TeamName LIKE 'Barcelona'");
			// 4. recorre el resultset
			while (rs.next()) {
				Game game1 = new Game();
				teamName1 = rs.getString("TeamName");
				game1.setTeamName(teamName1);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("No es connecta!");
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			// 3. executar SQL
			rs = stmt.executeQuery("SELECT TeamName FROM GAME WHERE TeamName LIKE 'Madrid'");
			// 4. recorre el resultset
			while (rs.next()) {
				Game game2 = new Game();
				teamName2 = rs.getString("TeamName");
				game2.setTeamName(teamName2);
			}

		} catch (SQLException e) {
			System.out.println("No es connecta!");
			e.printStackTrace();
		} finally {
			rs.close();
		}
	}

	private void getSeat() throws Exception {
		ResultSet rs = null;

		try {
			// 2. crear objecte statement
			stmt = conn.createStatement();
			// 3. executar SQL
			rs = stmt.executeQuery("SELECT SideName FROM SIDE");

			// 4. recorre el resultset
			while (rs.next()) {
				String side = rs.getString("SideName");
				cboZoneName.getItems().add(side);
			}

		} catch (SQLException e) {
			System.out.println("No es connecta!");
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
		}

	}

	private void getPriceZone(String zone) {
		ResultSet rs = null;

		try {
			// 2. crear objecte statement
			stmt = conn.createStatement();
			// 3. executar SQL
			System.out.println("SELECT Price FROM SIDE where SideName='" + zone + "'");

			rs = stmt.executeQuery("SELECT Price FROM SIDE where SideName='" + zone + "'");

			// 4. recorre el resultset
			while (rs.next()) {

				int price = rs.getInt("Price");
				System.out.println("Preu de la BD: " + price);
				prices.setPrice(price);

			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("No es connecta!");
			e.printStackTrace();
		}

	}

	private void showSecondWindow() {
		gpane = new GridPane();
		gpane.setAlignment(Pos.TOP_LEFT);
		gpane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		gpane.setHgap(5.5);
		gpane.setVgap(5.5);

		gpane.add(new Label("Entrada"), 12, 0);

		Image image1 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 100, 100,
				false, false);
		ImageView imageView1 = new ImageView();
		imageView1.setImage(image1);
		imageView1.setX(0);
		imageView1.setY(0);
		gpane.add(new ImageView(image1), 0, 0);

		Image image2 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 70, 70,
				false, false);
		ImageView imageView2 = new ImageView();
		imageView2.setImage(image2);
		imageView2.setX(0);
		imageView2.setY(0);
		gpane.add(new ImageView(image2), 0, 19);

		Image image3 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/barcelona.png", 70, 70,
				false, false);
		ImageView imageView3 = new ImageView();
		imageView3.setImage(image3);
		imageView3.setX(0);
		imageView3.setY(0);
		gpane.add(new ImageView(image3), 1, 19);

		gpane.add(new Label("LocalTeam                  VS"), 0, 20);
		gpane.add(new Label(teamName1), 1, 20);

		gpane.add(new Label("IdFan:"), 0, 24);
		gpane.add(new TextField(), 0, 25);
		gpane.add(new Label("Paycard:"), 0, 26);
		gpane.add(new TextField(), 0, 27);

		gpane.add(new Label("Seients:"), 12, 18);
		gpane.add(cboZoneName, 16, 18);

		gpane.add(new Label("Quantitat:"), 12, 20);
		gpane.add(new TextField(), 16, 20);

		gpane.add(new Label("Preu Total:"), 0, 35);

		lblPrice = new Label();
		gpane.add(lblPrice, 0, 36);

		gpane.add(new Button("Comprar"), 0, 45);

		Stage secondStage = new Stage();
		Scene scene1 = new Scene(gpane, 800, 800);
		secondStage.setTitle("Entrada 1");
		secondStage.setScene(scene1);
		secondStage.show();
	}

	private void showThirdWindow() {
		gpane = new GridPane();
		gpane.setAlignment(Pos.TOP_LEFT);
		gpane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		gpane.setHgap(5.5);
		gpane.setVgap(5.5);

		gpane.add(new Label("Entrada"), 12, 0);

		Image image1 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 100, 100,
				false, false);
		ImageView imageView1 = new ImageView();
		imageView1.setImage(image1);
		imageView1.setX(0);
		imageView1.setY(0);
		gpane.add(new ImageView(image1), 0, 0);

		Image image2 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 70, 70,
				false, false);
		ImageView imageView2 = new ImageView();
		imageView2.setImage(image2);
		imageView2.setX(0);
		imageView2.setY(0);
		gpane.add(new ImageView(image2), 0, 19);

		Image image3 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/madrid.png", 70, 70,
				false, false);
		ImageView imageView3 = new ImageView();
		imageView3.setImage(image3);
		imageView3.setX(0);
		imageView3.setY(0);
		gpane.add(new ImageView(image3), 1, 19);

		gpane.add(new Label("LocalTeam                  VS"), 0, 20);
		gpane.add(new Label(teamName2), 1, 20);

		gpane.add(new Label("IdFan:"), 0, 24);
		gpane.add(new TextField(), 0, 25);
		gpane.add(new Label("Paycard:"), 0, 26);
		gpane.add(new TextField(), 0, 27);

		gpane.add(new Label("Seients:"), 12, 18);
		gpane.add(cboZoneName, 16, 18);

		gpane.add(new Label("Quantitat:"), 12, 20);
		gpane.add(new TextField(), 16, 20);

		gpane.add(new Label("Preu Total:"), 0, 35);

		gpane.add(new Button("Comprar"), 0, 45);

		Stage thirdStage = new Stage();
		Scene scene2 = new Scene(gpane, 800, 800);
		thirdStage.setTitle("Entrada 2");
		thirdStage.setScene(scene2);
		thirdStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}