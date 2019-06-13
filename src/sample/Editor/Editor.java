package sample.Editor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.Editor.EditorElements.Project;

import java.net.URL;
import java.util.ResourceBundle;

public class Editor implements Initializable
{
    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public Project getProject()
    {
        return project;
    }
}