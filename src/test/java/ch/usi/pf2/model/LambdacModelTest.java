package ch.usi.pf2.model;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import ch.usi.pf2.model.interpreter.Interpreter;
import ch.usi.pf2.model.parser.ParseException;


public class LambdacModelTest {

    private LambdacModel model;
    private String testFileContents;
    private File testFile;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        model = new LambdacModel();
        testFileContents = "This is a text file.\nIt is used to test the model.";
        testFile = folder.newFile("lambdactest.txt");
        try (final FileWriter writer = new FileWriter(testFile)) {
            writer.write(testFileContents);
        }
    }

    @Test
    public void testOpen() throws IOException {
        model.setFileName(testFile.getAbsolutePath());
        model.open();
        assertEquals(testFileContents, model.getTextToInterpret());
    }

    @Test
    public void testSaveWithFileName() throws IOException {
        model.setFileName(testFile.getAbsolutePath());
        final String newContents = "The new contents in the text file.";
        model.setTextToInterpret(newContents);
        model.save();
        assertEquals(newContents.length(), testFile.length());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSaveWithoutFileName() throws IOException {
        model.setTextToInterpret("This is a text file.\nIt is used to test the model.");
        model.save();
    }

    @Test
    public void testInterpret() throws ParseException {
        String textToInterpret = "\\x.x";
        model.setTextToInterpret(textToInterpret);
        model.interpret();
        assertEquals(new Interpreter().interpret(textToInterpret), model.getInterpretedText());
    }

    @Test
    public void testSetGetFileName() {
        model.setFileName("fileName");
        assertEquals("fileName", model.getFileName());
    }

    @Test
    public void testSetGetTextToInterpret() {
        model.setTextToInterpret("textToInterpret");
        assertEquals("textToInterpret", model.getTextToInterpret());
    }

    @Test
    public void testSetGetInterpretedText() {
        model.setInterpretedText("interpretedText");
        assertEquals("interpretedText", model.getInterpretedText());
    }

    @Test
    public void testGetHelp() {
        assertEquals(Interpreter.HELP, LambdacModel.getHelp());
    }

    @Test
    public void testAddPropertyChangeListener() {
        String fileName = "fileName";
        String textToInterpret = "textToInterpret";
        String interpretedText = "interpretedText";
        class LambdacModelListener implements PropertyChangeListener {

            public boolean gotNotifiedForFileName = false;
            public boolean gotNotifiedForTextToInterpret = false;
            public boolean gotNotifiedForInterpretedText = false;

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.FILE_NAME_PROPERTY)) {
                    gotNotifiedForFileName = evt.getNewValue() == fileName;
                } else if (evt.getPropertyName().equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY)) {
                    gotNotifiedForTextToInterpret = evt.getNewValue() == textToInterpret;
                } else {
                    gotNotifiedForInterpretedText = evt.getNewValue() == interpretedText;
                }
            }

        }
        LambdacModelListener li = new LambdacModelListener();
        model.addPropertyChangeListener(li);
        model.setFileName(fileName);
        assertTrue(li.gotNotifiedForFileName);
        model.setTextToInterpret(textToInterpret);
        assertTrue(li.gotNotifiedForTextToInterpret);
        model.setInterpretedText(interpretedText);
        assertTrue(li.gotNotifiedForInterpretedText);
    }
    
    @Test
    public void testRemovePropertyChangeListener() {
        String fileName = "fileName";
        String textToInterpret = "textToInterpret";
        String interpretedText = "interpretedText";
        class LambdacModelListener implements PropertyChangeListener {

            public boolean gotNotifiedForFileName = false;
            public boolean gotNotifiedForTextToInterpret = false;
            public boolean gotNotifiedForInterpretedText = false;

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.FILE_NAME_PROPERTY)) {
                    gotNotifiedForFileName = evt.getNewValue() == fileName;
                } else if (evt.getPropertyName().equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY)) {
                    gotNotifiedForTextToInterpret = evt.getNewValue() == textToInterpret;
                } else {
                    gotNotifiedForInterpretedText = evt.getNewValue() == interpretedText;
                }
            }

        }
        LambdacModelListener li = new LambdacModelListener();
        model.addPropertyChangeListener(li);
        model.removePropertyChangeListener(li);
        model.setFileName(fileName);
        assertFalse(li.gotNotifiedForFileName);
        model.setTextToInterpret(textToInterpret);
        assertFalse(li.gotNotifiedForTextToInterpret);
        model.setInterpretedText(interpretedText);
        assertFalse(li.gotNotifiedForInterpretedText);
    }

}
