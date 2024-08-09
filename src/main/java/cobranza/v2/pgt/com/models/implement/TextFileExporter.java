package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.models.services.FileExporter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class TextFileExporter implements FileExporter {

    private final Log log = LogFactory.getLog(TextFileExporter.class);

    @Override
    public Path export(String fileContent, String fileName) throws IOException {
        String tmpdir = Files.createTempDirectory("tmpCAM").toFile().getAbsolutePath();
        //String tmpDirsLocation = System.getProperty("java.io.tmpdir");

        Path filePath = Paths.get(tmpdir, fileName);
        try {
            Path exportedFilePath = Files.write(filePath, fileContent.getBytes(), StandardOpenOption.CREATE);
            return exportedFilePath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
