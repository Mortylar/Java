package edu.school21.processor;

import com.google.auto.service.AutoService;
import edu.school21.annotation.HtmlForm;
import edu.school21.annotation.HtmlInput;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes("edu.school21.annotation.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {

    private static final String PATH = "target/classes/";
    private String fileName;
    private String content;

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        for (Element formAnnotation :
             roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm form = formAnnotation.getAnnotation(HtmlForm.class);
            fileName = form.fileName();
            content =
                String.format(getHeaderLine(), form.action(), form.method());
            for (Element inputAnnotation :
                 roundEnv.getElementsAnnotatedWith(HtmlInput.class)) {
                HtmlInput input =
                    inputAnnotation.getAnnotation(HtmlInput.class);
                content += String.format(getContentLine(), input.type(),
                                         input.name(), input.placeholder());
            }
            content += getBottomLine();
        }

        return write();
    }

    private String getHeaderLine() {
        return "<form action = \"%s\" method = \"%s\">\n";
    }

    private String getContentLine() {
        return "\t\t<input type = \"%s\" name = \"%s\" placeholder = \"%s\">\n";
    }

    private String getBottomLine() {
        return "\t\t<input type = \"submit\" value = \"Send\">\n</form>";
    }

    private boolean write() {
        try (BufferedOutputStream writer = new BufferedOutputStream(
                 new FileOutputStream(PATH + fileName))) {
            writer.write(content.getBytes());
            return true;
        } catch (Exception e) {
            System.out.printf("Writing error:\n%s\n", e.getMessage());
        }
        return false;
    }
}
