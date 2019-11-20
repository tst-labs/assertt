/*
 * The MIT License
 *
 * Copyright 2019 Tribunal Superior do Trabalho.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.jus.tst.assertt.json;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests.
 * @since 0.1
 */
public class JsonValueAssertTest {

    @Test
    public void numericValuesAreEqual() {
        final JsonArray array = Json.createArrayBuilder()
            .add(1)
            .add(1)
            .build();
        final JsonValue first = array.getJsonNumber(0);
        final JsonValue second = array.getJsonNumber(1);
        JsonValueAssert
            .assertThat(first)
            .isEqualTo(second);
    }

    @Test
    public void numericValuesAreNotEqual() {
        final JsonArray array = Json.createArrayBuilder()
            .add(1)
            .add(2)
            .build();
        final JsonValue first = array.getJsonNumber(0);
        final JsonValue second = array.getJsonNumber(1);
        JsonValueAssert
            .assertThat(first)
            .isNotEqualTo(second);
    }

    @Test
    public void objectValuesAreEqual() {
        final String key = "prop1";
        final JsonObject first = Json.createObjectBuilder()
            .add(key, 1)
            .build();
        final JsonObject second = Json.createObjectBuilder()
            .add(key, 1)
            .build();
        JsonValueAssert
            .assertThat(first)
            .isEqualTo(second);
    }

    @Test
    public void objectValuesAreNotEqual() {
        final String key = "prop2";
        final JsonObject first = Json.createObjectBuilder()
            .add(key, 1)
            .build();
        final JsonObject second = Json.createObjectBuilder()
            .add(key, 2)
            .build();
        JsonValueAssert
            .assertThat(first)
            .isNotEqualTo(second);
    }

    @Test
    public void objectsWithDiferentNumberOfPropertiesAreNotEqual() {
        final String key = "prop3";
        final JsonObject first = Json.createObjectBuilder()
            .add(key, 1)
            .build();
        final JsonObject second = Json.createObjectBuilder()
            .add(key, 1)
            .add(key, 2)
            .build();
        JsonValueAssert
            .assertThat(first)
            .isNotEqualTo(second);
    }

    @Test
    public void arrayOfPrimitivesAreEqual() {
        final JsonArray first = Json.createArrayBuilder()
            .add(1)
            .add(2)
            .add(0)
            .build();
        final JsonArray second = Json.createArrayBuilder()
            .add(1)
            .add(2)
            .add(0)
            .build();
        JsonValueAssert
            .assertThat(first)
            .isEqualTo(second);
    }

    @Test
    public void arrayWithDiferentSizesAreNotEqual() {
        final JsonArray first = Json.createArrayBuilder()
            .add(1)
            .add(2)
            .build();
        final JsonArray second = Json.createArrayBuilder()
            .add(1)
            .add(2)
            .add(0)
            .build();
        JsonValueAssert
            .assertThat(first)
            .isNotEqualTo(second);
    }

    @Test
    public void arraysWithSameElementsInDiferentOrderAreNotEqual() {
        final JsonArray first = Json.createArrayBuilder()
            .add(1)
            .add(0)
            .add(2)
            .build();
        final JsonArray second = Json.createArrayBuilder()
            .add(1)
            .add(2)
            .add(0)
            .build();
        JsonValueAssert
            .assertThat(first)
            .isNotEqualTo(second);
    }

    @Test
    public void complexObjectsShouldBeEqual() {
        final String proc = "0001253-24.2016.5.10.0013";
        final String cnj = "numProcCnj";
        final String pecas = "pecas";
        final JsonObject one = new Document(
            0,
            "2018-10-04T10:56:29",
            "application/pdf"
        ).toJson();
        final JsonObject two = new Document(
            1,
            "2018-10-30T22:19:46",
            "text/html"
        ).toJson();
        final JsonObject first = Json.createObjectBuilder()
            .add(cnj, proc)
            .add(
                pecas,
                Json.createArrayBuilder()
                    .add(one)
                    .add(two)
            )
            .build();
        final JsonObject second = Json.createObjectBuilder()
            .add(cnj, proc)
            .add(
                pecas,
                Json.createArrayBuilder()
                    .add(one)
                    .add(two)
            )
            .build();
        JsonValueAssert
            .assertThat(first)
            .isEqualTo(second);
    }

    @Test
    public void nullValuesAreEqual() {
        final JsonArray array = Json.createArrayBuilder()
            .addNull()
            .addNull()
            .build();
        final JsonValue first = array.get(0);
        final JsonValue second = array.get(1);
        JsonValueAssert
            .assertThat(first)
            .isEqualTo(second);
    }

}
