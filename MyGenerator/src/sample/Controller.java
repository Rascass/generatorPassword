package sample;

import java.awt.event.TextEvent;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.nio.sctp.MessageInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea passwordTextArea;

    @FXML
    private TextField lengthTextField;

    @FXML
    private CheckBox numbCheckBox;

    @FXML
    private ChoiceBox<String> langChoiceBox;

    @FXML
    private Button generateButton;

    @FXML
    private CheckBox symbCheckBox;

    @FXML
    private CheckBox charLCheckBox;

    @FXML
    private CheckBox charUCheckBox;

    @FXML
    void initialize() {
        ObservableList<String> langs = FXCollections.observableArrayList("русcкий", "английский");
        langChoiceBox.setItems(langs);
        langChoiceBox.setValue("английский");
        lengthTextField.setText("10");
        generateButton.setOnAction(actionEvent -> {
            Generator generator;
            PasswordValidator passwordValidator;
            try{
                int size = Integer.parseInt(lengthTextField.getText());
                String language = langChoiceBox.getValue() == "русcкий" ? "rus" : "eng";
                generator = new Generator(size, language);
                generator.setIsLowCase(charLCheckBox.isSelected());
                generator.setIsUpperCase(charUCheckBox.isSelected());
                generator.setIsNumbs(numbCheckBox.isSelected());
                generator.setIsSymbs(symbCheckBox.isSelected());
                String password;
                do {
                    password = generator.generatePassword();
                    passwordValidator = new PasswordValidator(password);
                } while(passwordValidator.isCommon());
                passwordTextArea.setText(password);
                if(!passwordValidator.isStrong()){
                    showAlert(passwordValidator.getInfo());
                }
            }
            catch (Exception e){
                showErrorAlert();
            }
        });
    }
    public void showAlert(String str){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Your password is not strong");
        alert.setContentText(str);
        alert.showAndWait();
    }
    public void showErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error. You specified the wrong value of length or didn't choose at least one type of symbols.");
        alert.showAndWait();
    }
}
