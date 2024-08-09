package cobranza.v2.pgt.com.models.services;

import java.io.IOException;
import java.nio.file.Path;

public interface FileExporter {

    Path export(String fileContent, String fileName) throws IOException;

}