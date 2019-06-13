package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Editor.Editor;
import sample.Editor.EditorElements.Project;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    JFXButton createBlankBtn;

    @FXML
    JFXButton createBookBtn;

    @FXML
    JFXButton openBtn;

    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Create action handlers for buttons
        createBlankBtn.setOnAction(event ->
        {
            try {
                createProject("blank");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        createBookBtn.setOnAction(event ->
        {
            try {
                createProject("book");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        openBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.showOpenDialog(pane.getScene().getWindow());
        });
    }

    private void createProject(String type) throws IOException
    {
        FileChooser fileChooser = new FileChooser();

        Window window = pane.getScene().getWindow();

        // Dialog for creating a project
        File file = fileChooser.showSaveDialog(window);

        // Create project folder in a path chosen with a dialog
        File createdFileDirectory = new File(file.getPath());
        createdFileDirectory.mkdir();

        createFilesAndDirectories(createdFileDirectory, type);
    }

    private void createFilesAndDirectories(File createdFileDirectory, String type) throws IOException
    {
        // Create folders where all the required files are stored
        File docsFolder = new File(createdFileDirectory.getPath() + "\\Documents");
        docsFolder.mkdir();

        File projectNotesFolder = new File(createdFileDirectory.getPath() + "\\ProjectNotes");
        projectNotesFolder.mkdir();

        File settingsFolder = new File(createdFileDirectory.getPath() + "\\Settings");
        settingsFolder.mkdir();

        Project project = new Project(new ArrayList<>(), createdFileDirectory, docsFolder, settingsFolder, projectNotesFolder, type);

        // Create project .pl3 file
        new File(createdFileDirectory + "\\" + createdFileDirectory.getName() + ".pl3").createNewFile();

        // Write project attributes to this newly created .pl3 project file
        PrintWriter writer = new PrintWriter(createdFileDirectory + "\\" + createdFileDirectory.getName() + ".pl3", "UTF-8");
        writer.println(project.toString());
        writer.close();

        // Open editor screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Editor/editor.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        Editor editor = loader.getController();
        editor.setProject(project);
        stage.setScene(scene);
    }
}