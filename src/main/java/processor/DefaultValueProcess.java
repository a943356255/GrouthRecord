package processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

//@SupportedAnnotationTypes({"annotation.MyDefaultValue"})
//@SupportedSourceVersion(SourceVersion.RELEASE_11)
////@AutoService(Processor.class)
//public class DefaultValueProcess extends AbstractProcessor {
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        System.out.println("进入该方法");
//        for (TypeElement typeElement : set) {
//            String annotationName = typeElement.getSimpleName().toString();
//            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(typeElement);
//            for (Element element : elements) {
//                // 获取注解
//                MyDefaultValue myDefaultValue = element.getAnnotation(MyDefaultValue.class);
//                System.out.println(myDefaultValue.value());
//            }
//
//            System.out.println(annotationName);
//            System.out.println(elements.toString());
//        }
//
//        return false;
//    }
//
//}
