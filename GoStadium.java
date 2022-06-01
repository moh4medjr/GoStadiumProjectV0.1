package application;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.time.LocalDate;
import java.util.ArrayList;
//import javax.swing.JOptionPane;
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
	private ResultSet rs;

	private String teamName1, teamName2, gameDate1, gameDate2, gameHour1, gameHour2, competition1, competition2, url,
			user, pwd/*,side*/;

	//private LocalDate date;

	private int price, totalPrice, quantity/*, seatNumber*/;
	//private static int nextSeatNumber = 1;

	private Stage secondStage, thirdStage;
	private GridPane gpane;

	private Label lblPrice, lblTotalPrice;

	private Button btBuy = new Button("Comprar"), btBackHome1 = new Button("Inici"), btBackHome2 = new Button("Inici"),
			btShowSecondWindow = new Button("Comprar Entrades"), btShowThirdWindow = new Button("Comprar Entrades"),
			btMultiplicate = new Button("Afegir Seints");
	private TextField seatQuantity = new TextField(), putIdFan = new TextField(), putPayCard = new TextField();

	private ComboBox<String> cboZoneName = new ComboBox<>();
	private ArrayList<String> allLines = new ArrayList<String>();

	@Override
	public void start(Stage primaryStage) {
		try {
			readFiles();
			initializeDB();
			getGameInfos();
			getSeat();
			getTotalPrice();

			cboZoneName.setOnAction(e -> calcPriceZone(cboZoneName.getValue()));
			btBackHome1.setOnAction(e -> secondStage.close());
			btBackHome2.setOnAction(e -> thirdStage.close());
			btShowSecondWindow.setOnAction(e -> showSecondWindow());
			btShowThirdWindow.setOnAction(e -> showThirdWindow());
			btMultiplicate.setOnAction(e -> getTotalPrice());
			//cboZoneName.setOnAction(e -> side = cboZoneName.getValue());
			//btBuy.setOnAction(e -> recordBooking());

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

			gpane.add(new Label("Juny 2022"), 0, 7);

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
			gpane.add(new ImageView(image3), 10, 19);

			gpane.add(new Label("LocalTeam                 VS"), 0, 20);
			gpane.add(new Label(teamName1), 10, 20);

			gpane.add(new Label("Competició"), 20, 18);
			gpane.add(new Label(competition1), 20, 19);
			gpane.add(new Label("Data"), 22, 18);
			gpane.add(new Label(gameDate1), 22, 19);
			gpane.add(new Label("Hora"), 24, 18);
			gpane.add(new Label(gameHour1), 24, 19);

			gpane.add(btShowSecondWindow, 20, 20);

			Image image4 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/logo.png", 70, 70,
					false, false);
			ImageView imageView4 = new ImageView();
			imageView4.setImage(image4);
			imageView4.setX(0);
			imageView4.setY(0);
			gpane.add(new ImageView(image4), 0, 31);

			Image image5 = new Image("file:///home/moha/eclipse-workspace/GoStadium/src/application/madrid.png", 70, 70,
					false, false);
			ImageView imageView5 = new ImageView();
			imageView5.setImage(image5);
			imageView5.setX(0);
			imageView5.setY(0);
			gpane.add(new ImageView(image5), 10, 31);

			gpane.add(new Label("LocalTeam                 VS"), 0, 32);
			gpane.add(new Label(teamName2), 10, 32);

			gpane.add(new Label("Competició"), 20, 30);
			gpane.add(new Label(competition2), 20, 31);
			gpane.add(new Label("Data"), 22, 30);
			gpane.add(new Label(gameDate2), 22, 31);
			gpane.add(new Label("Hora"), 24, 30);
			gpane.add(new Label(gameHour2), 24, 31);

			gpane.add(btShowThirdWindow, 20, 32);

			Scene scene = new Scene(gpane, 2000, 1000);
			scene.getStylesheets().add("file:///home/moha/eclipse-workspace/GoStadium/src/application/application.css");
			primaryStage.setTitle("Inici");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readFiles() {
		try {
			FileReader inputFile = new FileReader(
					"/home/moha/eclipse-workspace/GoStadium/src/application/connection.txt");
			BufferedReader myBuffer = new BufferedReader(inputFile);
			String line = myBuffer.readLine();
			while (line != null) {
				if (line != null) {
					allLines.add(line);
					line = myBuffer.readLine();
				}
			}
			for (int i = 0; i < allLines.size(); i++) {
				url = allLines.get(0);
				user = allLines.get(1);
				pwd = allLines.get(2);
			}
			inputFile.close();
		} catch (IOException e) {
			System.err.println("No es troba l'arxiu!");
		}
	}

	/*private void writeFiles() {
		String outputFile = "/home/moha/eclipse-workspace/GoStadium/src/application/connection.txt";
		File file = new File(outputFile);
		if (file.exists())
			System.out.println("El fitxer " + outputFile + " ya existe");
		else {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
				
				for (int x = 0; x < 10; x++)
					bw.write("Fila numero " + x + "\n");
				bw.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}*/

	private void initializeDB() {
		try {
			// 1. crear connexió
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			System.out.println("No es connecta a la base de dades");
			e.printStackTrace();
		}
	}

	private void getGameInfos() throws Exception {
		rs = null;
		try {
			// 2. crear objecte statement
			stmt = conn.createStatement();
			// 3. executar SQL
			rs = stmt.executeQuery("SELECT TeamName FROM GAME WHERE TeamName LIKE 'Barcelona'");
			// 4. recorre el resultset
			while (rs.next()) {
				teamName1 = rs.getString("TeamName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT TeamName FROM GAME WHERE TeamName LIKE 'Madrid'");
			while (rs.next()) {
				teamName2 = rs.getString("TeamName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT GameDate FROM GAME WHERE TeamName LIKE 'Barcelona'");
			while (rs.next()) {
				gameDate1 = rs.getString("GameDate");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT GameDate FROM GAME WHERE TeamName LIKE 'Madrid'");
			while (rs.next()) {
				gameDate2 = rs.getString("GameDate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT GameHour FROM GAME WHERE TeamName LIKE 'Barcelona'");
			while (rs.next()) {
				gameHour1 = rs.getString("GameHour");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT GameHour FROM GAME WHERE TeamName LIKE 'Madrid'");
			while (rs.next()) {
				gameHour2 = rs.getString("GameHour");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Competition FROM GAME WHERE TeamName LIKE 'Barcelona'");
			while (rs.next()) {
				competition1 = rs.getString("Competition");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Competition FROM GAME WHERE TeamName LIKE 'Madrid'");
			while (rs.next()) {
				competition2 = rs.getString("Competition");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
	}

	private void getSeat() throws Exception {
		rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT SideName FROM SIDE");
			while (rs.next()) {
				String side = rs.getString("SideName");
				cboZoneName.getItems().add(side);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
		}
	}

	private void getPriceZone(String zone) {
		rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Price FROM SIDE where SideName='" + zone + "'");
			while (rs.next()) {
				price = rs.getInt("Price");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void calcPriceZone(String zone) {
		getPriceZone(zone);
		lblPrice.setText(String.valueOf(price + "€"));
	}

	private void getTotalPrice() {
		try {
			quantity = Integer.parseInt(this.seatQuantity.getText());
			totalPrice = quantity * price;
			lblTotalPrice.setText(String.valueOf(totalPrice + "€"));
		} catch (NumberFormatException e) {
			System.err.println("No s'ha seleccionat ningún seint!!!");
		}
	}

	/**private void recordBooking() {
		rs = null;
		
		
		for(i= 0; i) {
			try {
				Statement stmt = conn.createStatement();
				date = LocalDate.now();
				seatNumber = nextSeatNumber;
				rs = stmt.executeQuery("INSERT INTO `BOOKING` (`SeatNumber`, `Side`, `Price`, `BookingDate`) VALUES("
						+ seatNumber + "," + side + "," + totalPrice + "," + date + ");");
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			nextSeatNumber++;
		}
	}**/
	
	/*public int login(int fanId, int paycard) {

		PreparedStatement pst;
		int state = -1;

		try {

			conn = DriverManager.getConnection(url, user, pwd);

			if (conn != null) {

				String sql = "SELECT * FROM FAN WHERE BINARY FanId=? AND Paycard=?";

				pst = conn.prepareStatement(sql);
				pst.setString(1, fanId);
				pst.setString(2, paycard);

				rs = pst.executeQuery();

				if (rs.next()) {
					state = 1;
				} else {
					state = 0;
				}

			} else {
				JOptionPane.showMessageDialog(null, "Hubo un error al conectarse con la base de datos", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			;
		}

		return state;

	}*/

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

		gpane.add(btBackHome1, 0, 4);

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

		gpane.add(new Label("LocalTeam          VS"), 0, 20);
		gpane.add(new Label(teamName1), 1, 20);

		gpane.add(new Label("IdFan:"), 0, 24);
		gpane.add(putIdFan, 0, 25);
		gpane.add(new Label("Paycard:"), 0, 26);
		gpane.add(putPayCard, 0, 27);

		gpane.add(new Label("Seients:"), 12, 19);
		gpane.add(cboZoneName, 16, 19);
		gpane.add(new Label("Preu:"), 17, 19);
		lblPrice = new Label();
		gpane.add(lblPrice, 18, 19);

		gpane.add(new Label("Quantitat:"), 12, 20);
		gpane.add(seatQuantity, 16, 20);
		gpane.add(btMultiplicate, 12, 22);

		gpane.add(new Label("Preu Total:"), 0, 35);
		lblTotalPrice = new Label();
		gpane.add(lblTotalPrice, 0, 36);

		gpane.add(btBuy, 0, 45);

		secondStage = new Stage();
		Scene scene1 = new Scene(gpane, 2000, 1000);
		scene1.getStylesheets().add("file:///home/moha/eclipse-workspace/GoStadium/src/application/application.css");
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

		gpane.add(btBackHome2, 0, 4);

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

		gpane.add(new Label("LocalTeam          VS"), 0, 20);
		gpane.add(new Label(teamName2), 1, 20);

		gpane.add(new Label("IdFan:"), 0, 24);
		gpane.add(putIdFan, 0, 25);
		gpane.add(new Label("Paycard:"), 0, 26);
		gpane.add(putPayCard, 0, 27);

		gpane.add(new Label("Seients:"), 12, 19);
		gpane.add(cboZoneName, 16, 19);
		gpane.add(new Label("Preu:"), 17, 19);
		lblPrice = new Label();
		gpane.add(lblPrice, 18, 19);

		gpane.add(new Label("Quantitat:"), 12, 20);
		gpane.add(seatQuantity, 16, 20);
		gpane.add(btMultiplicate, 12, 22);

		gpane.add(new Label("Preu Total:"), 0, 35);
		lblTotalPrice = new Label();
		gpane.add(lblTotalPrice, 0, 36);

		gpane.add(btBuy, 0, 45);

		thirdStage = new Stage();
		Scene scene2 = new Scene(gpane, 2000, 1000);
		scene2.getStylesheets().add("file:///home/moha/eclipse-workspace/GoStadium/src/application/application.css");
		thirdStage.setTitle("Entrada 2");
		thirdStage.setScene(scene2);
		thirdStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}