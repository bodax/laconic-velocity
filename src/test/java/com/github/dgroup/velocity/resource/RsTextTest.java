/*
 * MIT License
 *
 * Copyright (c) 2018 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.dgroup.velocity.resource;

import com.github.dgroup.velocity.ResourceException;
import java.io.File;
import java.text.MessageFormat;
import org.cactoos.list.ListOf;
import org.cactoos.map.MapEntry;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for class {@link RsText}.
 *
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle OperatorWrapCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocVariableCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @since 0.1.0
 */
@SuppressWarnings({
    "PMD.AvoidDuplicateLiterals",
    "PMD.StaticAccessToStaticFields"
})
public final class RsTextTest {

    private static final String RHOME = MessageFormat.format(
        "src{0}test{0}resources{0}velocity", File.separator
    );

    @Test
    public void transformHtml() throws ResourceException {
        MatcherAssert.assertThat(
            new RsText(
                "html.vm", RHOME
            ).transform(
                new RsVariable<>(
                    "users", new ListOf<>("Tom", "Bob", "Hank")
                )
            ),
            Matchers.equalTo(
                "<ul>\n            "
                    + "<li>Tom</li>\n            "
                    + "<li>Bob</li>\n            "
                    + "<li>Hank</li>\n    "
                    + "</ul>"
            )
        );
    }

    @Test
    public void transformSql() throws ResourceException {
        MatcherAssert.assertThat(
            new RsText(
                "query.sql", RHOME
            ).transform(
                new RsVariable<>("flag", true)
            ),
            Matchers.equalTo("select 1 from dual\nunion\nselect 2 from dual\n")
        );
    }

    @Test
    public void transformMarkdown() throws ResourceException {
        MatcherAssert.assertThat(
            new RsText(
                "markdown.md", RHOME
            ).transform(
                new RsVariable<>(
                    "systems",
                    new ListOf<>(
                        new MapEntry<>("Windows", 10),
                        new MapEntry<>("Linux", 16),
                        new MapEntry<>("Mac OS", 12)
                    )
                )
            ),
            Matchers.equalTo(
                "| OS | Version |\n"
                    + "|---|---|\n"
                    + "| Windows | 10 |\n"
                    + "| Linux | 16 |\n"
                    + "| Mac OS | 12 |\n"
            )
        );
    }
}
