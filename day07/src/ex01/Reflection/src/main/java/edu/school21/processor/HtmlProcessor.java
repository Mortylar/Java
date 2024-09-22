package edu.school21.processor;

import com.google.auto.service.AutoService;
import java.io.FileOutputStream;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes("edu.school21.annotation.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {

    private static final String PATH = "target/file";

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        System.out.printf("\n\n\n\n\nAAAAAAAAAAaa\n\n\n\n\n");
        try (FileOutputStream writer = new FileOutputStream(PATH)) {
            writer.write("dfhseflkesurghlsergj".getBytes());
            return true;
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        }
        return false;
    }
}
