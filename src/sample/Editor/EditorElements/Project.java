package sample.Editor.EditorElements;

import java.io.File;
import java.util.List;

public class Project
{
    private long projectSize;
    private String type;
    private List<Document> documents;
    private File projectFolder;
    private File documentsFolder;
    private File settingsFolder;
    private File projectNotesFolder;

    public Project(List<Document> documents, File projectFolder, File documentsFolder, File settingsFolder, File projectNotesFolder, String type)
    {
        this.projectSize = 0;
        this.documents = documents;
        this.projectFolder = projectFolder;
        this.documentsFolder = documentsFolder;
        this.settingsFolder = settingsFolder;
        this.projectNotesFolder = projectNotesFolder;
        this.type = type;
    }

    public long getProjectSize()
    {
        return projectSize;
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

    public File getSettingsFolder()
    {
        return settingsFolder;
    }

    public File getProjectNotesFolder()
    {
        return projectNotesFolder;
    }

    @Override
    public String toString()
    {
        return "Project{" +
                "projectSize=" + projectSize +
                ", type='" + type + '\'' +
                ", documents=" + documents +
                ", projectFolder=" + projectFolder +
                ", documentsFolder=" + documentsFolder +
                ", settingsFolder=" + settingsFolder +
                ", projectNotesFolder=" + projectNotesFolder +
                '}';
    }
}