package gui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Yatzy;

public class MainApp extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Yatzy");
		GridPane pane = new GridPane();
		this.initContent(pane);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	// -------------------------------------------------------------------------

	// Shows the face values of the 5 dice.
	private TextField[] txfValues  = new TextField[5];
	// Shows the hold status of the 5 dice.
	private CheckBox[] chbHolds = new CheckBox[5];
	// Shows the results previously selected .
	// For free results (results not set yet), the results
	// corresponding to the actual face values of the 5 dice are shown.
	private TextField[] txfResults = new TextField[15];
	// Shows points in sums, bonus and total.
	private Label [] resultNames = new Label[15];
	private TextField txfSumSame, txfBonus, txfSumOther, txfTotal;
	// Shows the number of times the dice has been rolled.
	private Label lblRolled;

	private Button btnRoll;

	private void initContent(GridPane pane) {
		pane.setGridLinesVisible(false);
		pane.setPadding(new Insets(10));
		pane.setHgap(10);
		pane.setVgap(10);

		// ---------------------------------------------------------------------

		GridPane dicePane = new GridPane();
		pane.add(dicePane, 0, 0);
		dicePane.setGridLinesVisible(false);
		dicePane.setPadding(new Insets(10));
		dicePane.setHgap(10);
		dicePane.setVgap(10);
		dicePane.setStyle("-fx-border-color: black");

		// initialize txfValues, chbHolds, btnRoll and lblRolled
		// 
		
		
		//initializes the textfield for dice rolls.
		
		for(int i = 0; i < txfValues.length; i++) {
			txfValues[i] = new TextField();
			txfValues[i].setMaxWidth(100);
			txfValues[i].setMinHeight(100);
			txfValues[i].setEditable(false);
			dicePane.add(txfValues[i],i+1, 1,1,2);
		}
		
		
		for(int i = 0; i < chbHolds.length; i++) {
			chbHolds[i] = new CheckBox();
			chbHolds[i].setText("Hold");
			dicePane.add(chbHolds[i],i+1,3);
		}
		
		
		btnRoll = new Button("Roll");
		btnRoll.setMinSize(50, 50);
		dicePane.add(btnRoll, 4, 4);
		GridPane.setMargin(btnRoll, new Insets(10, 10, 0, 10));
		
		Label lblName = new Label("Rolled:");
		dicePane.add(lblName, 5, 4 );
		
	    lblRolled= new Label("0");
		dicePane.add(lblRolled, 6, 4 );
		
		

		// ---------------------------------------------------------------------

		GridPane scorePane = new GridPane();
		pane.add(scorePane, 0, 1);
		scorePane.setGridLinesVisible(false);
		scorePane.setPadding(new Insets(10));
		scorePane.setVgap(5);
		scorePane.setHgap(10);
		scorePane.setStyle("-fx-border-color: black");
		int w = 50; // width of the text fields

		// Initialize labels for results, txfResults,
		// labels and text fields for sums, bonus and total.
		// 

		for(int i = 0; i < txfResults.length; i++) {
			txfResults[i] = new TextField();
			txfResults[i].setMaxWidth(100);
			txfResults[i].setEditable(false);
			scorePane.add(txfResults[i],3, i +1);
		}
		
		String[] resultType = {"1-s:","2-s:","3-s:","4-s:",
				"5-s:","6-s:","one pairs:","two pairs:","three of a kind:",
				"four of a kind:","full house:","small straight:","large straight:",
				"chance:","yatzy:"};
		
		
		for(int i = 0; i < resultNames.length; i++) {
			resultNames[i] = new Label(resultType[i]);
			scorePane.add(resultNames[i],0, i +1);
		}
		
		txfSumSame = new TextField();
		txfBonus = new TextField();
		txfSumSame.setStyle("-fx-text-inner-color: blue;");
		txfBonus.setStyle("-fx-text-inner-color: blue;");
		
		txfSumSame.setEditable(false);
		txfBonus.setEditable(false);
		
		scorePane.add(txfSumSame, 5, 6);
		scorePane.add(txfBonus, 7, 6);
		
		
		txfSumOther = new TextField();
		txfSumOther.setStyle("-fx-text-inner-color: blue;");
		txfTotal = new TextField();
		txfTotal.setStyle("-fx-text-inner-color: blue;");
		
		
		txfSumOther.setEditable(false);
		txfTotal.setEditable(false);
		
		scorePane.add(txfSumOther, 5, 15);
		scorePane.add(txfTotal, 7, 15);
		
		txfSumSame.setMaxWidth(100);
		txfSumOther.setMaxWidth(100);
		txfBonus.setMaxWidth(100);
		txfTotal.setMaxWidth(100);
		
		Label sum1 = new Label("Sum");
		scorePane.add(sum1, 4, 6);
		
		Label bonus = new Label("Bonus");
		scorePane.add(bonus, 6, 6);
		
		Label sum2 = new Label("Sum");
		scorePane.add(sum2, 4, 15);
		
		Label total2 = new Label("Total");
		scorePane.add(total2, 6, 15);
		
		
		btnRoll.setOnAction(event -> this.roll());
		

			
		for(int i = 0; i < txfResults.length; i++) {
			txfResults[i].setOnMouseClicked(event -> this.chooseFieldAction(event));
		}
		
	}

	// -------------------------------------------------------------------------

	private Yatzy dice = new Yatzy();

	private int sumSame = 0;
	private int bonus = 0;
	private int sumOther = 0;
	private int total = 0;
	private boolean isFinished = false;
	
	private void checkRollCount() {
		lblRolled.setText(String.valueOf(dice.getThrowCount()));
		 if(dice.getThrowCount() == 2) {
			btnRoll.setDisable(true);
		} else  {
			btnRoll.setDisable(false);
			
		}
		
	}
	
	private void roll() {
		
		checkRollCount();
		
		boolean[] hold = new boolean[5];
		for(int i = 0; i < hold.length;i++) {
			hold[i] = chbHolds[i].isSelected();
		}
			
			
		dice.throwDice(hold);
		enableHolds();
		
		int[] values = dice.getValues();
		
		int rollCount = dice.getThrowCount();
		
		
		for(int i = 0; i < txfValues.length;i++) {
			txfValues[i].setText(String.valueOf(values[i]));
		} 
		
		
		lblRolled.setText(String.valueOf(rollCount));
		
		int [] results = dice.getResults();
		
		
		
		for(int i = 0; i < txfResults.length; i++) {
			if(!txfResults[i].isDisabled()) {
				txfResults[i].setText(String.valueOf(results[i]));
			}
		}
		

		}
		
		public void chooseFieldAction(Event event) {
			int chosenIndex =0;
			if(dice.getThrowCount() == 3) {
				dice.resetThrowCount();
				checkRollCount();
				
				
				
				TextField txt = (TextField) event.getSource(); // så kan den samme action bruge til mange tekstfelter......}
				for(int i = 0; i < txfResults.length;i++) {
					if(txt.equals(txfResults[i])) {
						chosenIndex = i;
					}
				}
				
				if(!txfResults[chosenIndex].isDisabled()) {
					if(chosenIndex < 6) {
						sumSame += Integer.valueOf(txfResults[chosenIndex].getText());
						txfSumSame.setText(String.valueOf(sumSame));
					} else {
						sumOther += Integer.valueOf(txfResults[chosenIndex].getText());
						txfSumOther.setText(String.valueOf(sumOther));
					}
				}
				
				if(sumSame >= 63) {
					bonus = 50;
					txfBonus.setText(String.valueOf(bonus));
				}
					
				
				txfResults[chosenIndex].setDisable(true);
			
				
				dice.resetThrowCount();
				resetHolds();
				disableHolds();
				
				total = sumSame + bonus + sumOther;
				txfTotal.setText(String.valueOf(total));
				
				if (!isFinished) {
					int i = 0;
					boolean fieldDisabled = false;
					while(!fieldDisabled && i < txfResults.length) {
						if(!txfResults[i].isDisabled()) {
							fieldDisabled = true;
						}
						i++;
					}
					if(fieldDisabled == false) {
						isFinished = true;
					}
				}
				
					if(isFinished) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Game Finished");
						alert.setHeaderText("The game is finished");
						alert.setContentText("Your score: " + String.valueOf(total));

						alert.showAndWait();
					}
				
				
			}
	
			
		
		
		}
		
		private void resetHolds() {
			
			for(int i = 0; i < chbHolds.length;i++) {
				chbHolds[i].setSelected(false);
			}
			
		}
		private void enableHolds() {
			for(int i = 0; i < chbHolds.length; i++) {
				chbHolds[i].setDisable(false);
			}
			
		}
		
		private void disableHolds() {
			
			for(int i = 0; i < chbHolds.length; i++) {
				chbHolds[i].setDisable(true);
			}
		}
		
	
}

	// Create a method for btnRoll's action.
	// Hint: Create small helper methods to be used in the action method.
	// 

	// -------------------------------------------------------------------------

	// Create a method for mouse click on one of the text fields in txfResults.
	// Hint: Create small helper methods to be used in the mouse click method.
	// 