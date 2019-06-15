package sample.Editor.EditorElements;

import java.io.File;
import java.io.Serializable;

public class Document implements Serializable
{
    private File path;
    private String documentName;
    private int amountOfWords;
    private int amountOfChars;

    public Document(File path, String documentName, int amountOfWords, int amountOfChars)
    {
        this.path = path;
        this.documentName = documentName;
        this.amountOfWords = amountOfWords;
        this.amountOfChars = amountOfChars;
    }

    public String getDocumentName()
    {
        return documentName;
    }

    public void setDocumentName(String documentName)
    {
        this.documentName = documentName;
    }

    public File getPath()
    {
        return path;
    }

    public void setPath(File path)
    {
        this.path = path;
    }

    public int getAmountOfWords()
    {
        return amountOfWords;
    }

    public void setAmountOfWords(int amountOfWords)
    {
        this.amountOfWords = amountOfWords;
    }

    public int getAmountOfChars()
    {
        return amountOfChars;
    }

    public void setAmountOfChars(int amountOfChars)
    {
        this.amountOfChars = amountOfChars;
    }

    @Override
    public String toString()
    {
        return "Document{" +
                "path=" + path +
                ", documentName='" + documentName + '\'' +
                ", amountOfWords=" + amountOfWords +
                ", amountOfChars=" + amountOfChars +
                '}';
    }
}