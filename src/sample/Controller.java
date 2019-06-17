package sample;

import com.jfoenix.controls.JFXButton;
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

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PLUME 3 projects (*.pl3)", "*.pl3");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show open project dialog
            File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

            // If we open file, not cancel, convert file to object
            if(file != null)
            {
                try {

                    FileInputStream fi = new FileInputStream(new File(file.getPath()));
                    ObjectInputStream oi = new ObjectInputStream(fi);

                    Project project = (Project) oi.readObject();

                    openEditorScreen(project);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createProject(String type) throws IOException
    {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PLUME 3 projects (*.pl3)", "*.pl3");
        fileChooser.getExtensionFilters().add(extFilter);

        Window window = pane.getScene().getWindow();

        // Dialog for creating a project
        File file = fileChooser.showSaveDialog(window);

        if(file != null)
        {
            // Create project folder in a path chosen with a dialog
            File createdFileDirectory = new File(file.getPath());

            createdFileDirectory.mkdir();

            createFilesAndDirectories(createdFileDirectory, type);
        }
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

        File trashFolder = new File(createdFileDirectory.getPath() + "\\Trash");
        trashFolder.mkdir();

        String fullFileName = createdFileDirectory.getName();

        Project project = new Project(type, new ArrayList<>(), new ArrayList<>(), createdFileDirectory, docsFolder, settingsFolder, projectNotesFolder, trashFolder, fullFileName.substring(0, fullFileName.length() - 4));

        System.out.println(project.toString());

        // Write & save project attributes to this newly created .pl3 project file
        FileOutputStream fileOut = new FileOutputStream(createdFileDirectory + "\\" + createdFileDirectory.getName());
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(project);
        objectOut.close();

        openEditorScreen(project);
    }

    private void openEditorScreen(Project project) throws IOException
    {
        // Open editor screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Editor/editor.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setResizable(true);

        stage.setWidth(1100);
        stage.setHeight(750);
        stage.setMinWidth(900);
        stage.setMinHeight(750);

        Scene scene = new Scene(loader.load());
        Editor editor = loader.getController();

        editor.setProject(project);
        stage.setScene(scene);
    }
}