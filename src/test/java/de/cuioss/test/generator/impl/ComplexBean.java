/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.impl;

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

@EqualsAndHashCode(exclude = { "noObjectIdentityString", "badstring" })
@ToString(exclude = { "noObjectIdentityString", "badstring" })
class ComplexBean implements Serializable {

    private static final long serialVersionUID = -7914292255779711820L;

    public static final String STRING_WITH_DEFAULT_VALUE = "stringWithDefault";
    public static final String ATTRIBUTE_STRING_LIST = "stringList";
    public static final String ATTRIBUTE_STRING_SET = "stringSet";
    public static final String ATTRIBUTE_STRING_SORTED_SET = "stringSortedSet";
    public static final String ATTRIBUTE_STRING_COLLECTION = "stringCollection";

    @Getter
    @Setter
    private String string;

    @Getter
    @Setter
    private String noObjectIdentityString;

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
