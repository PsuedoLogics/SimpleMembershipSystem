package org.michaelmathews.memba_membershipsmadeeasy;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class MainApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException {


        stage.setTitle("'MEMBA' MEMBERSHIPS MADE EASY");

        BorderPane navigationMenu = new BorderPane();
        HBox headerBox = new HBox();
        headerBox.setPadding(new Insets(10, 10, 10, 10));
        headerBox.setSpacing(10);
        navigationMenu.setTop(headerBox);

        Button existingMembershipsButton = new Button("Existing Memberships");
        existingMembershipsButton.setFont(Font.font(16));
        headerBox.getChildren().add(existingMembershipsButton);

        Button newMembershipButton = new Button("New Membership");
        newMembershipButton.setFont(Font.font(16));
        headerBox.getChildren().add(newMembershipButton);


        GridPane existingMembershipsGrid = new GridPane();
        existingMembershipsGrid.setHgap(10);
        existingMembershipsGrid.setVgap(10);
        existingMembershipsGrid.setPadding(new Insets(10, 10, 10, 10));
        existingMembershipsGrid.setAlignment(Pos.CENTER);


        GridPane createMembershipGridPane = new GridPane();
        createMembershipGridPane.setHgap(10);
        createMembershipGridPane.setVgap(20);
        createMembershipGridPane.setPadding(new Insets(25, 25, 25, 25));
        createMembershipGridPane.setAlignment(Pos.CENTER);

//<editor-fold desc="New Membership Creation Grid Data">
        Text title = new Text("MEMBERSHIPS MADE EASY: New Membership Creation");
        title.setFont(new Font("Times New Roman", 30));
        title.setUnderline(true);
        createMembershipGridPane.add(title, 0, 0, 2, 1);

        Text membershipDescriptionText = new Text("Membership Description: ");
        membershipDescriptionText.setFont(new Font("Times New Roman", 18));
        createMembershipGridPane.add(membershipDescriptionText, 0, 1);
        TextField membershipDescriptionTextField = new TextField();
        membershipDescriptionTextField.setPromptText("Short description");
        createMembershipGridPane.add(membershipDescriptionTextField, 1, 1);

        Text membershipFrequencyText = new Text("Membership Frequency (In Days): ");
        membershipFrequencyText.setFont(new Font("Times New Roman", 18));
        createMembershipGridPane.add(membershipFrequencyText, 0, 2);
        Spinner membershipFrequencySpinner = new Spinner(1,365,1);
        membershipFrequencySpinner.setEditable(true);
        createMembershipGridPane.add(membershipFrequencySpinner, 1, 2);

        Text recurringOrNonRecurringText = new Text("Recurring Membership? (Based On Frequency ^): ");
        recurringOrNonRecurringText.setFont(new Font("Times New Roman", 18));
        createMembershipGridPane.add(recurringOrNonRecurringText, 0, 3);
        ToggleGroup recurringGroup = new ToggleGroup();
        RadioButton recurringRadioButton = new RadioButton("Recurring");
        RadioButton nonRecurringRadioButton = new RadioButton("One Time");
        recurringRadioButton.setToggleGroup(recurringGroup);
        nonRecurringRadioButton.setToggleGroup(recurringGroup);
        createMembershipGridPane.add(recurringRadioButton, 1,3);
        createMembershipGridPane.add(nonRecurringRadioButton, 2, 3);

        Text membershipCost = new Text("Membership Cost: $");
        membershipCost.setFont(new Font("Times New Roman", 18));
        createMembershipGridPane.add(membershipCost, 0, 4);
        Spinner membershipCostSpinner = new Spinner(1.00,999.00,5.00);
        membershipCostSpinner.setEditable(true);
        createMembershipGridPane.add(membershipCostSpinner, 1, 4);

        Text signUpFee = new Text("Sign Up Fee (Always Leave 0 for Non-Recurring): $");
        signUpFee.setFont(new Font("Times New Roman", 18));
        createMembershipGridPane.add(signUpFee, 0, 5);
        Spinner signUpFeeSpinner = new Spinner(1.00,999.00,5.00);
        signUpFeeSpinner.setEditable(true);
        createMembershipGridPane.add(signUpFeeSpinner, 1, 5);

        Button createMembershipButton = new Button("Create Membership");
        createMembershipButton.setFont(new Font("Times New Roman", 18));
        createMembershipGridPane.add(createMembershipButton, 1, 6,2,1);

        createMembershipButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                boolean isRecurring = false;
                if(recurringRadioButton.isSelected()) { isRecurring = true; }
                if(nonRecurringRadioButton.isSelected()) { isRecurring = false; }
                GenerateMembershipFromData.authorize(membershipDescriptionTextField.getText(), parseInt(membershipFrequencySpinner.getEditor().getText()), isRecurring, parseDouble(membershipCostSpinner.getEditor().getText()), parseDouble(signUpFeeSpinner.getEditor().getText()));
            }

        });
//</editor-fold>

//<editor-fold desc="Existing Memberships Grid Data">
        Text existingMembershipsTitle = new Text("MEMBERSHIPS MADE EASY: Membership Templates");
        existingMembershipsTitle.setFont(new Font("Times New Roman", 30));
        existingMembershipsTitle.setUnderline(true);
        existingMembershipsGrid.add(existingMembershipsTitle, 0, 0, 2, 1);
        HBox memberShipDetailsBox = new HBox();
        memberShipDetailsBox.setPadding(new Insets(10, 10, 10, 10));
        memberShipDetailsBox.setSpacing(10);
        Text membershipDetailsText = new Text("Membership Details: ");
        memberShipDetailsBox.getChildren().add(membershipDetailsText);
        existingMembershipsGrid.add(memberShipDetailsBox, 0, 1);

//</editor-fold>

//<editor-fold desc="Navigation Buttons and default screen">
        newMembershipButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
                    public void handle(ActionEvent event) {
                    navigationMenu.setCenter(createMembershipGridPane);
            }
        });

        existingMembershipsButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event) {

               try {
                   GenerateMembershipFromData.getExistingMembershipsFromFile();

                   for (int i = 0; i < GenerateMembershipFromData.membershipInfoList.size(); i++) {
                       String[] temp = GenerateMembershipFromData.membershipInfoList.get(i).split(",");
                       Text desc = new Text("Description: " + temp[0]);
                       Text frequency = new Text("Frequency: " + temp[1] + " Days");
                       Text recurring = new Text("Recurring Billing? " + temp[2]);
                       Text cost = new Text("Price Per Bill $" + temp[3]);
                       Text oneTimeFee = new Text("One Time Fee $" + temp[4]);

                       Button useButton = new Button("USE");
                       useButton.setFont(new Font("Times New Roman", 14));
                       useButton.setPadding(new Insets(5, 5, 5, 5));

                       Button deleteButton = new Button("DELETE");
                       deleteButton.setFont(new Font("Times New Roman", 14));
                       deleteButton.setPadding(new Insets(5, 5, 5, 5));


                       HBox tempHBox = new HBox();
                       tempHBox.setPadding(new Insets(10, 10, 10, 10));
                       tempHBox.setSpacing(10);
                       tempHBox.setAlignment(Pos.CENTER);
                       tempHBox.getChildren().add(desc);
                       tempHBox.getChildren().add(frequency);
                       tempHBox.getChildren().add(recurring);
                       tempHBox.getChildren().add(cost);
                       tempHBox.getChildren().add(oneTimeFee);
                       tempHBox.getChildren().add(useButton);
                       tempHBox.getChildren().add(deleteButton);
                       existingMembershipsGrid.add(tempHBox, 1, i + 1);
                       int finalI = i;
                       deleteButton.setOnAction(deleteEvent -> { existingMembershipsGrid.getChildren().remove(tempHBox); GenerateMembershipFromData.eraseData(GenerateMembershipFromData.membershipInfoList.get(finalI));});
                      //TODO -- OPEN PAYMENT SCREEN ON USE
                       useButton.setOnAction(useEvent -> {});
                   }
               }catch(Exception e){
                   System.out.println("No Memberships Found");
               }
                navigationMenu.setCenter(existingMembershipsGrid);
           }
        });

        //CHANGE TO SET DEFAULT SCREEN WHEN APP OPENS
        navigationMenu.setCenter(createMembershipGridPane);

        //</editor-fold>

        //Base Properties - Probably will not change from here on out
        Scene baseScene = new Scene(navigationMenu, 1920,1080);
        stage.setScene(baseScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}