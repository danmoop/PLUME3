package sample.Editor;

public abstract class Document
{
    private String documentName;
    private int amountOfWords;
    private int amountOfChars;

    public Document(String documentName, int amountOfWords, int amountOfChars)
    {
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
}