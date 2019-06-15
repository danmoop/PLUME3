package sample.Editor.EditorElements;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Project implements Serializable
{
    private String type;
    private List<Document> documents;
    private File projectFolder;
    private File documentsFolder;
    private File settingsFolder;
    private File projectNotesFolder;
    private File trashFolder;
    private String name;

    public Project(String projectName, List<Document> documents, File projectFolder, File documentsFolder, File settingsFolder, File projectNotesFolder, File trashFolder, String type)
    {
        this.documents = documents;
        this.name = projectName;
        this.projectFolder = projectFolder;
        this.documentsFolder = documentsFolder;
        this.settingsFolder = settingsFolder;
        this.trashFolder = trashFolder;
        this.projectNotesFolder = projectNotesFolder;
        this.type = type;
    }

    public List<Document> getDocuments()
    {
        return documents;
    }

    public String getType()
    {
        return type;
    }

    public File getProjectFolder()
    {
        return projectFolder;
    }

    public File getDocumentsFolder()
    {
        return documentsFolder;
    }

    public String getName()
    {
        return name;
    }

    public File getSettingsFolder()
    {
        return settingsFolder;
    }

    public File getProjectNotesFolder()
    {
        return projectNotesFolder;
    }

    public void addDocument(Document document)
    {
        documents.add(document);
    }

}