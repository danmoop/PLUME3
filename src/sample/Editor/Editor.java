package sample.Editor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import sample.Editor.EditorElements.Document;
import sample.Editor.EditorElements.Project;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

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
    Label notification;

    @FXML
    JFXListView draftList;

    @FXML
    JFXListView trashList;

    @FXML
    Label activeDocumentLabel;

    private Project project;
    private Document activeDocument = null;

    private ContextMenu menu;

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

        menu = new ContextMenu();

        MenuItem item = new MenuItem("Delete");

        item.setOnAction(event -> removeDocument());

        menu.getItems().add(item);
    }

    public void setProject(Project project)
    {
        this.project = project;
        documentName.setText(project.getName());

        initDraftLists();
        initTrashLists();
    }

    private void initDraftLists()
    {
        project.getDocuments().forEach(document -> draftList.getItems().add(document.getDocumentName()));

        draftList.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            if(event.getButton() == MouseButton.PRIMARY) // DETECT ONLY LMB CLICKS
            {
                int index = draftList.getSelectionModel().getSelectedIndex();

                activeDocument = project.getDocuments().get(index);

                System.out.println(activeDocument.toString());

                activeDocumentLabel.setText(activeDocument.getDocumentName());
                readDocumentFromFolder();

                if(!editor.isVisible())
                {
                    notification.setVisible(false);
                    editor.setVisible(true);

                    WebView webview = (WebView) editor.lookup("WebView");
                    GridPane.setHgrow(webview, Priority.ALWAYS);
                    GridPane.setVgrow(webview, Priority.ALWAYS);

                    editor.setOnKeyReleased(event1 -> writeDocumentToFolder());
                }
            }
        });

        // DETECT ONLY RMB CLICKS
        draftList.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            int index = draftList.getSelectionModel().getSelectedIndex();
            activeDocument = project.getDocuments().get(index);

            menu.show(draftList, event.getScreenX(), event.getScreenY());
        });
    }

    private void initTrashLists()
    {
        System.out.println(project.getRemovedDocs().size());
        project.getRemovedDocs().forEach(document -> trashList.getItems().add(document.getDocumentName()));
    }

    private void addDraftDocument() throws IOException
    {
        int index = project.getDocumentsFolder().list().length;


        // UUID is used here to avoid same names for documents (previously I used doc's index, so in theory there could be the same docs
        File doc = new File(project.getDocumentsFolder() + "\\" + UUID.randomUUID().toString() + ".html");

        doc.createNewFile();

        Document document = new Document(doc, "List " + index, 0, 0);

        project.addDocument(document);
        saveProject();

        draftList.getItems().add(document.getDocumentName());
    }

    private void readDocumentFromFolder()
    {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(activeDocument.getPath()));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = contentBuilder.toString();

        editor.setHtmlText(content);
    }

    private void writeDocumentToFolder()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(activeDocument.getPath()));
            writer.write(editor.getHtmlText());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeDocument()
    {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(project.getTrashFolder() + "\\" + activeDocument.getDocumentName() + ".html"));
            writer.write(editor.getHtmlText());
            writer.close();

            project.getDocuments().remove(draftList.getSelectionModel().getSelectedIndex());
            project.getRemovedDocs().add(activeDocument);

            draftList.getItems().remove(draftList.getSelectionModel().getSelectedIndex());
            editor.setHtmlText("");
            editor.setVisible(false);

            activeDocument.getPath().delete();
            activeDocumentLabel.setText("No list selected");

            trashList.getItems().add(activeDocument.getDocumentName());

            saveProject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProject() throws IOException
    {
        FileOutputStream fileOut = new FileOutputStream(project.getProjectFolder() + "\\" + project.getName() + ".pl3");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(project);
        objectOut.close();
    }
}