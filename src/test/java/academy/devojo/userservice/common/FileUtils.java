package academy.devojo.userservice.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
public class FileUtils {
    @Autowired
    private ResourceLoader resourceLoader;

    public String readResourceFile(String fileName) throws Exception {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName))
                .getFile();

        return new String(Files.readAllBytes(file.toPath()));
    }
}
