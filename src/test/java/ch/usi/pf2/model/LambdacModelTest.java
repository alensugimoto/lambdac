package ch.usi.pf2.model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * The test class LambdacModelTest for the main model of this application.
 *
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class LambdacModelTest {

    private static final LambdacModel model = new LambdacModel();

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void textOpen() throws IOException {
        final File file = folder.newFile();
        model.setFileName(file.getName());
    }

}
