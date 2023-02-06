package de.icw.cui.test.generator.impl;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("javadoc")
@EqualsAndHashCode(exclude = { "noObjectIdentitiyString", "badstring" })
@ToString(exclude = { "noObjectIdentitiyString", "badstring" })
public class ComplexBean implements Serializable {

    private static final long serialVersionUID = -7914292255779711820L;

    public static final String ATTRIBUTE_STRING = "string";
    public static final String ATTRIBUTE_TRANSIENT_STRING = "transientString";
    public static final String ATTRIBUTE_NO_OBJECT_IDENTITY_STRING = "noObjectIdentitiyString";
    public static final String ATTRIBUTE_STRING_WITH_DEFAULT = "stringWithDefault";
    public static final String STRING_WITH_DEFAULT_VALUE = "stringWithDefault";
    public static final String ATTRIBUTE_BAD_STRING = "badstring";
    public static final String ATTRIBUTE_STRING_LIST = "stringList";
    public static final String ATTRIBUTE_STRING_SET = "stringSet";
    public static final String ATTRIBUTE_STRING_SORTED_SET = "stringSortedSet";
    public static final String ATTRIBUTE_STRING_COLLECTION = "stringCollection";
    public static final String ATTRIBUTE_BOOLEAN_OBJECT = "booleanObject";
    public static final String ATTRIBUTE_BOOLEAN_PRIMITIVE = "booleanPrimitive";

    @Getter
    @Setter
    private String string;

    @Getter
    @Setter
    private String noObjectIdentitiyString;

    @Getter
    @Setter
    private transient String transientString;

    @Getter
    @Setter
    private String stringWithDefault = STRING_WITH_DEFAULT_VALUE;

    @Setter
    private String badstring;

    @Getter
    @Setter
    private List<String> stringList;

    @Getter
    @Setter
    private Set<String> stringSet;

    @Getter
    @Setter
    private SortedSet<String> stringSortedSet;

    @Getter
    @Setter
    private Collection<String> stringCollection;

    @Getter
    @Setter
    private Boolean booleanObject;

    @Getter
    @Setter
    private boolean booleanPrimitive;

    @Getter
    @Setter
    private ZonedDateTime zonedDateTime;

    @Getter
    @Setter
    private ZoneId zoneId;



}
