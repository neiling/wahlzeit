package org.wahlzeit.utils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE) // We need the annotation only at the source code
@Repeatable(PatternInstances.class) // Sometimes a class can belong to several design patterns
public @interface PatternInstance {
    String patternName();
    String[] participants();
}
