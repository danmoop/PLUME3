package sample.Editor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import sample.Editor.EditorElements.Document;
import sample.Editor.EditorElements.Project;

import javax.print.Doc;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Editor implements Initializable
{
    @FXML
    Label documentName;

    @FXML
    AnchorPane pane;

    @FXML
    JFXButton addDraftBtn;

    @FXML
    HTMLEditor editor;

    @FXML
    Text notification;

    @FXML
    JFXListView draftList;

    @FXML
    Label activeDocumentLabel;

    private Project project;
    private Document activeDocument = null;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if(activeDocument == null)
        {
            editor.setVisible(false);
        }

        addDraftBtn.setOnAction(event ->
        {
            try {
                addDraftDocument();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setProject(Project project)
    {
        this.project = project;
        documentName.setText(project.getName());

        initDraftLists();
    }

    private void initDraftLists()
    {
        project.getDocuments().forEach(document -> draftList.getItems().add(document.getDocumentName()));

        draftList.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            if(event.getButton() == MouseButton.PRIMARY) // DETECT ONLY LMB CLICKS
            {
                int index = draftList.getSelectionModel().getSelectedIndex();

                activeDocument = project.getDocuments().get(index);

                activeDocumentLabel.setText(activeDocument.getDocumentName());

                editor.setHtmlText("<h1>" + "List " + (index + 1) + "</h1>");

                if(!editor.isVisible())
                {
                    notification.setVisible(false);
                    editor.setVisible(true);
                }
            }
        });

        // DETECT ONLY LMB CLICKS
        draftList.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            System.out.println("RMB DOWN");
        });
    }

    private void addDraftDocument() throws IOException
    {
        int index = project.getDocumentsFolder().list().length;
        index++;

        File doc = new File(project.getDocumentsFolder() + "\\" + index + ".rtf");

        doc.createNewFile();

        Document document = new Document(doc, "List " + index, 0, 0);

        project.addDocument(document);
        saveProject();

        draftList.getItems().add(document.getDocumentName());
    }

    private void saveProject() throws IOException
    {
        FileOutputStream fileOut = new FileOutputStream(project.getProjectFolder() + "\\" + project.getName() + ".pl3");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(project);
        objectOut.close();
    }
}