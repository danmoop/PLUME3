package sample.Editor.EditorElements;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Project implements Serializable
{
    private String type;
    private List<Document> documents;
    private List<Document> removedDocs;
    private File projectFolder;
    private File documentsFolder;
    private File settingsFolder;
    private File projectNotesFolder;
    private File trashFolder;
    private String name;

    public Project(String type, List<Document> documents, List<Document> removedDocs, File projectFolder, File documentsFolder, File settingsFolder, File projectNotesFolder, File trashFolder, String name)
    {
        this.type = type;
        this.documents = documents;
        this.removedDocs = removedDocs;
        this.projectFolder = projectFolder;
        this.documentsFolder = documentsFolder;
        this.settingsFolder = settingsFolder;
        this.projectNotesFolder = projectNotesFolder;
        this.trashFolder = trashFolder;
        this.name = name;
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

    public File getTrashFolder()
    {
        return trashFolder;
    }

    public List<Document> getRemovedDocs()
    {
        return removedDocs;
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