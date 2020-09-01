package UnitCalculator;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UnitConversion extends Application 
{
	//GUI Components are used in most panes
	GridPane centerPane = new GridPane();
	VBox bottomPane = new VBox(10);
	Text title2 = new Text();
	TextField tfFrom = new TextField();
	TextField tfTo = new TextField();
	Label from = new Label("From", tfFrom);
	Label to = new Label("To", tfTo);
	ComboBox<String> cboFrom = new ComboBox<>();
	ComboBox<String> cboTo = new ComboBox<>();
	ArrayList<String> options = new ArrayList<>();
	Button btCalculate = new Button("Calculate");
	
	//Menu pane
	@Override
	public void start(Stage primaryStage)
	{
		//We use a ComboBox to give the user their choices
		BorderPane pane = new BorderPane();
		VBox center = new VBox(10);
		Text title = new Text("Unit Conversion Calculator");
		Text prompt = new Text("Choose a parameter for unit conversion");
		ComboBox<String> cbo = new ComboBox<>();
		Button btOk = new Button("OK");
		
		center.setAlignment(Pos.CENTER);
		
		cbo.getItems().addAll("Temperature", "Distance", "Speed", "Weight", "Area", "Liquid");
		center.getChildren().addAll(title, prompt, cbo, btOk);
		
		//We set default value to avoid an empty string
		cbo.setValue("Temperature");
		
		pane.setCenter(center);
		pane.setTop(title);
		
		title.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
		
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		pane.setStyle("-fx-background-color: lightgreen");
		
		//Event handlers for each type of unit
		//We create new scenes for each view, and change them in our stage depending on the user's choice
		btOk.setOnAction(e-> {
			
			if (cbo.getValue().equals("Temperature"))
			{
				Scene scene2 = new Scene(getTempPane(), 400, 200);
				primaryStage.setScene(scene2);
			}
			
			else if (cbo.getValue().equals("Distance"))
			{
				Scene scene3 = new Scene(getDistancePane(), 400, 200);
				primaryStage.setScene(scene3);
			}
			
			else if (cbo.getValue().equals("Speed"))
			{
				Scene scene4 = new Scene(getSpeedPane(), 400, 200);
				primaryStage.setScene(scene4);
			}
			
			else if (cbo.getValue().equals("Weight"))
			{
				Scene scene4 = new Scene(getWeightPane(), 400, 200);
				primaryStage.setScene(scene4);
			}
			
			else if (cbo.getValue().equals("Area"))
			{
				Scene scene5 = new Scene(getAreaPane(), 400, 200);
				primaryStage.setScene(scene5);
			}
			
			else
			{
				Scene scene6 = new Scene(getLiquidPane(), 400, 200);
				primaryStage.setScene(scene6);
			}
			
		});
		
		Scene scene = new Scene(pane, 400, 125);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Unit Conversion Calculator");
		primaryStage.show();
	}
	
	//All panes after the menu pane will have the same structure, so we create a base pane for them
	private BorderPane getBasePane()
	{
		BorderPane basePane = new BorderPane();
		
		BorderPane.setAlignment(title2, Pos.TOP_CENTER);
		centerPane.setAlignment(Pos.CENTER);
		bottomPane.setAlignment(Pos.BOTTOM_CENTER);
		
		from.setContentDisplay(ContentDisplay.BOTTOM);
		to.setContentDisplay(ContentDisplay.BOTTOM);
		
		centerPane.setHgap(20);
		centerPane.setVgap(20);
		
		//The result cannot be edited
		tfTo.setEditable(false);
		
		centerPane.add(from, 0, 0);
		centerPane.add(to, 1, 0);
		centerPane.add(cboFrom, 0, 1);
		centerPane.add(cboTo, 1, 1);
		
		bottomPane.getChildren().add(btCalculate);
		
		basePane.setTop(title2);
		basePane.setCenter(centerPane);
		basePane.setBottom(bottomPane);
		
		title2.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
		basePane.setStyle("-fx-background-color: lightgreen");
		
		return basePane;
	}
	
	//Pane for temperature
	private BorderPane getTempPane()
	{
		BorderPane tempPane = getBasePane();
		title2.setText("Temperature conversion");
		
		cboFrom.setValue("Celsius");
		cboTo.setValue("Fahrenheit");
		
		//We add our choices to the ArrayList to then put them in the ComboBox
		options.add("Celsius");
		options.add("Fahrenheit");
		cboFrom.getItems().addAll(options);
		cboTo.getItems().addAll(options);
		
		//Event handlers to deal with every possible combination
		btCalculate.setOnAction(e-> {
			
			if (cboFrom.getValue() == "Celsius" && cboTo.getValue() == "Fahrenheit")
			{
				//We have to convert the text to double type to pass it to the converter function, in this case toFahrenheit
				//Then we set the TextField to the converted value
				//String.format helps us to get as many or as few decimal points as we desire
				double toF = toFahrenheit(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toF));
			}
			
			else if (cboFrom.getValue() == "Fahrenheit" && cboTo.getValue() == "Celsius")
			{
				double toC = toCelsius(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toC));
			}
			
			//If we get to this point, both units are the same, so we just write the value to the other TextField
			else
				tfTo.setText(tfFrom.getText());
		});
		
		return tempPane;
	}
	
	//Pane for distance
	private BorderPane getDistancePane()
	{	
		BorderPane distPane = getBasePane();
		title2.setText("Distance conversion");
		
		cboFrom.setValue("Kilometers");
		cboTo.setValue("Miles");
				
		options.add("Kilometers");
		options.add("Miles");
		options.add("Feet");
		options.add("Inches");
		cboFrom.getItems().addAll(options);
		cboTo.getItems().addAll(options);
		
		btCalculate.setOnAction(e-> {
			
			//This time we have more units to convert, so we can use nested if statements to deal with it
			if (cboFrom.getValue() == "Kilometers")
			{
				if (cboTo.getValue() == "Miles")
				{
					//Because we have more than two different values to convert, the converter functions need to know which value to convert to
					//We can pass the string to let the function know what we need
					double toMiles = fromKilometers(Double.parseDouble(tfFrom.getText()), "Miles");
					tfTo.setText(String.format("%.3f", toMiles));
				}
				
				else if (cboTo.getValue() == "Feet")
				{
					double toFeet = fromKilometers(Double.parseDouble(tfFrom.getText()), "Feet");
					tfTo.setText(String.format("%.3f", toFeet));
				}
				
				else if (cboTo.getValue() == "Inches")
				{
					double toInches = fromKilometers(Double.parseDouble(tfFrom.getText()), "Inches");
					tfTo.setText(String.format("%.3f", toInches));
				}
				
				else
				{
					tfTo.setText(tfFrom.getText());
				}
			}
			
			else if (cboFrom.getValue() == "Miles")
			{
				if (cboTo.getValue() == "Kilometers")
				{
					double toKm = fromMiles(Double.parseDouble(tfFrom.getText()), "Kilometers");
					tfTo.setText(String.format("%.3f", toKm));
				}
				
				else if (cboTo.getValue() == "Feet")
				{
					double toFeet = fromMiles(Double.parseDouble(tfFrom.getText()), "Feet");
					tfTo.setText(String.format("%.3f", toFeet));
				}
				
				else if (cboTo.getValue() == "Inches")
				{
					double toInches = fromMiles(Double.parseDouble(tfFrom.getText()), "Inches");
					tfTo.setText(String.format("%.3f", toInches));
				}
				
				else
				{
					tfTo.setText(tfFrom.getText());
				}
			}
			
			else if (cboFrom.getValue() == "Feet")
			{
				if (cboTo.getValue() == "Kilometers")
				{
					double toKm = fromFeet(Double.parseDouble(tfFrom.getText()), "Kilometers");
					tfTo.setText(String.format("%.3f", toKm));
				}
				
				else if (cboTo.getValue() == "Miles")
				{
					double toMiles = fromFeet(Double.parseDouble(tfFrom.getText()), "Miles");
					tfTo.setText(String.format("%.3f", toMiles));
				}
				
				else if (cboTo.getValue() == "Inches")
				{
					double toInches = fromFeet(Double.parseDouble(tfFrom.getText()), "Inches");
					tfTo.setText(String.format("%.3f", toInches));
				}
				
				else
				{
					tfTo.setText(tfFrom.getText());
				}
			}
			
			else
			{
				if (cboTo.getValue() == "Kilometers")
				{
					double toKm = fromInches(Double.parseDouble(tfFrom.getText()), "Kilometers");
					tfTo.setText(String.format("%.3f", toKm));
				}
				
				else if (cboTo.getValue() == "Miles")
				{
					double toMiles = fromInches(Double.parseDouble(tfFrom.getText()), "Miles");
					tfTo.setText(String.format("%.3f", toMiles));
				}
				
				else if (cboTo.getValue() == "Feet")
				{
					double toFeet = fromInches(Double.parseDouble(tfFrom.getText()), "Feet");
					tfTo.setText(String.format("%.3f", toFeet));
				}
				
				else
				{
					tfTo.setText(tfFrom.getText());
				}
			}
		});
		
		return distPane;
	}
	
	//Pane for speed
	private BorderPane getSpeedPane()
	{
		BorderPane speedPane = getBasePane();
		title2.setText("Speed conversion");
		
		cboFrom.setValue("Km/h");
		cboTo.setValue("Mph");
				
		options.add("Km/h");
		options.add("Mph");
		cboFrom.getItems().addAll(options);
		cboTo.getItems().addAll(options);
		
		//Since the conversion also uses km and mi, we can use the same function we used for the distance
		//The rest is similar to the temperature pane
		btCalculate.setOnAction(e-> {
			
			if (cboFrom.getValue() == "Km/h" && cboTo.getValue() == "Mph")
			{
				double toMiles = fromKilometers(Double.parseDouble(tfFrom.getText()), "Miles");
				tfTo.setText(String.format("%.3f", toMiles));
			}
			
			else if (cboFrom.getValue() == "Mph" && cboTo.getValue() == "Km/h")
			{
				double toKm = fromMiles(Double.parseDouble(tfFrom.getText()), "Kilometers");
				tfTo.setText(String.format("%.3f", toKm));
			}
			
			else
				tfTo.setText(tfFrom.getText());
		});
		
		return speedPane;
	}
	
	//Pane for weight
	private BorderPane getWeightPane()
	{
		BorderPane weightPane = getBasePane();
		title2.setText("Weight conversion");
		
		cboFrom.setValue("Kilograms");
		cboTo.setValue("Pounds");
				
		options.add("Kilograms");
		options.add("Pounds");
		cboFrom.getItems().addAll(options);
		cboTo.getItems().addAll(options);
		
		//Logic similar to temperature pane
		btCalculate.setOnAction(e-> {
			
			if (cboFrom.getValue() == "Kilograms" && cboTo.getValue() == "Pounds")
			{
				double toLb = toPounds(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toLb));
			}
			
			else if (cboFrom.getValue() == "Pounds" && cboTo.getValue() == "Kilograms")
			{
				double toKg = toKilograms(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toKg));
			}
			
			else
				tfTo.setText(tfFrom.getText());
			
		});
		
		return weightPane;
	}
	
	//Pane for area
	private BorderPane getAreaPane()
	{
		BorderPane areaPane = getBasePane();
		title2.setText("Area conversion");
		
		cboFrom.setValue("Sq. Meters");
		cboTo.setValue("Sq. Feet");
				
		options.add("Sq. Meters");
		options.add("Sq. Feet");
		cboFrom.getItems().addAll(options);
		cboTo.getItems().addAll(options);
		
		//Logic similar to temperature pane
		btCalculate.setOnAction(e-> {
			
			if (cboFrom.getValue() == "Sq. Meters" && cboTo.getValue() == "Sq. Feet")
			{
				double toFt2 = toSqFt(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toFt2));
			}
			
			else if (cboFrom.getValue() == "Sq. Feet" && cboTo.getValue() == "Sq. Meters")
			{
				double toM2 = toSqM(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toM2));
			}
			
			else
				tfTo.setText(tfFrom.getText());
			
		});
		
		return areaPane;
	}
	
	//Pane for liquid
	private BorderPane getLiquidPane()
	{
		BorderPane liquidPane = getBasePane();
		title2.setText("Liquid conversion");
		
		cboFrom.setValue("Liters");
		cboTo.setValue("US Gallons");
				
		options.add("Liters");
		options.add("US Gallons");
		cboFrom.getItems().addAll(options);
		cboTo.getItems().addAll(options);
		
		//Logic similar to temperature pane
		btCalculate.setOnAction(e-> {
			
			if (cboFrom.getValue() == "Liters" && cboTo.getValue() == "US Gallons")
			{
				double toGal = toGallons(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toGal));
			}
			
			else if (cboFrom.getValue() == "US Gallons" && cboTo.getValue() == "Liters")
			{
				double toL = toLiters(Double.parseDouble(tfFrom.getText()));
				tfTo.setText(String.format("%.2f", toL));
			}
			
			else
				tfTo.setText(tfFrom.getText());
			
		});
		
		return liquidPane;
	}
	
	
	//Converter functions
	//These functions use formulas to calculate the result of the conversion
	public static double toCelsius(double fahrenheit)
	{
		return (fahrenheit - 32) * 5 / 9;
	}
	
	public static double toFahrenheit(double celsius)
	{
		return (celsius * 9 / 5) + 32;
	}
	
	//As mentioned before, we can use the string to determine which unit to convert to
	public static double fromKilometers(double km, String toUnit)
	{
		//Results calculated are based on formulas
		if (toUnit == "Miles")
			return km * 0.6213;
		
		else if (toUnit == "Feet")
			return km * 3280.84;
		
		else if (toUnit == "Inches")
			return km * 39370.1;
		
		else
			return km;
	}
	
	public static double fromMiles(double mi, String toUnit)
	{
		if (toUnit == "Kilometers")
			return mi * 1.6093;
		
		else if (toUnit == "Feet")
			return mi * 5280;
		
		else if (toUnit == "Inches")
			return mi * 63330;
		
		else
			return mi;
	}
	
	public static double fromFeet(double ft, String toUnit)
	{
		if (toUnit == "Kilometers")
			return ft / 3280.8398;
		
		else if (toUnit == "Miles")
			return ft / 5280;
		
		else if (toUnit == "Inches")
			return ft * 12;
		
		else
			return ft;
	}
	
	public static double fromInches(double in, String toUnit)
	{
		if (toUnit == "Kilometers")
			return in / 39370.0787;
		
		else if (toUnit == "Miles")
			return in / 63360;
		
		else if (toUnit == "Feet")
			return in / 12;
		
		else
			return in;
	}
	
	public static double toPounds(double kg)
	{
		return kg * 2.2046;
	}
	
	public static double toKilograms(double lb)
	{
		return lb / 2.2046;
	}
	
	public static double toSqFt(double sqM)
	{
		return sqM * 10.7639;
	}
	
	public static double toSqM(double sqFt)
	{
		return sqFt / 10.7639;
	}
	
	public static double toGallons(double l)
	{
		return l / 3.7854;
	}
	
	public static double toLiters(double gal)
	{
		return gal * 3.7854;
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
