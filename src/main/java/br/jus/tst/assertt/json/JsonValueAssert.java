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

import java.util.EnumMap;
import java.util.ListIterator;
import java.util.function.BiFunction;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.assertj.core.api.AbstractAssert;

/**
 * Custom assertions for <code>javax.json.JsonValue</code>.
 * @since 0.1
 */
public class JsonValueAssert extends AbstractAssert<JsonValueAssert, JsonValue> {

    /**
     * Uses EnumMap instead of 'switch case' to decide which comparisson object
     * will be used.
     */
    private static final EnumMap
        <JsonValue.ValueType,
        BiFunction<JsonValue, JsonValue, Boolean>
        > ACTIONS = new EnumMap<>(JsonValue.ValueType.class);

    static {
        ACTIONS.put(JsonValue.ValueType.ARRAY, new CompareArrays());
        ACTIONS.put(JsonValue.ValueType.FALSE, new ComparePrimitives());
        ACTIONS.put(JsonValue.ValueType.NULL, new CompareNulls());
        ACTIONS.put(JsonValue.ValueType.NUMBER, new ComparePrimitives());
        ACTIONS.put(JsonValue.ValueType.OBJECT, new CompareObjects());
        ACTIONS.put(JsonValue.ValueType.STRING, new ComparePrimitives());
        ACTIONS.put(JsonValue.ValueType.TRUE, new ComparePrimitives());
    }

    /**
     * Main constructor.
     * @param actual The value to be compared against the expected value.
     */
    public JsonValueAssert(final JsonValue actual) {
        super(actual, JsonValueAssert.class);
    }

    /**
     * Entry point. Follows AssertJ idiom.
     * @param actual The value to be compared against the expected value.
     * @return This instance.
     */
    @SuppressWarnings("PMD.ProhibitPublicStaticMethods")
    public static JsonValueAssert assertThat(final JsonValue actual) {
        return new JsonValueAssert(actual);
    }

    /**
     * Verifies that the expected value is equal to the actual value.
     * @param expected Expected value.
     * @return Boolean.
     */
    public JsonValueAssert isEqualTo(final JsonValue expected) {
        if (!this.areEqual(expected)) {
            failWithMessage(
                "Esperava-se que o valor fosse <%s>, mas foi <%s>",
                expected,
                actual
            );
        }
        return this;
    }

    /**
     * Verifies that the expected value is not equal to the actual value.
     * @param expected Expected value.
     * @return Boolean.
     */
    public JsonValueAssert isNotEqualTo(final JsonValue expected) {
        if (this.areEqual(expected)) {
            failWithMessage(
                "Esperava-se que o valor <%s> fosse diferente de <%s>",
                expected,
                actual
            );
        }
        return this;
    }

    /**
     * Verifies if the expected value and the actual value are equal.
     * @param expected EXpected value.
     * @return Boolean.
     */
    private Boolean areEqual(final JsonValue expected) {
        isNotNull();
        return ACTIONS.get(this.actual.getValueType())
            .apply(this.actual, expected);
    }

    /**
     * Compares primitive values.
     * @since 0.1
     */
    private static class ComparePrimitives extends CompareJsonValues {

        @Override
        protected Boolean compare(final JsonValue first, final JsonValue second) {
            return first.toString().equals(second.toString());
        }

    }

    /**
     * Compares array values in the same order they appear.
     * @since 0.1
     */
    private static class CompareArrays extends CompareJsonValues {

        @Override
        protected Boolean compare(final JsonValue first, final JsonValue second) {
            final JsonArray array = (JsonArray) first;
            final JsonArray other = (JsonArray) second;
            final boolean result;
            if (array.size() == other.size()) {
                final ListIterator<JsonValue> iterator = array.listIterator();
                boolean equals = true;
                while (iterator.hasNext()) {
                    final int index = iterator.nextIndex();
                    final JsonValue next = iterator.next();
                    equals = equals && ACTIONS.get(next.getValueType())
                        .apply(next, other.get(index));
                }
                result = equals;
            } else {
                result = false;
            }
            return result;
        }
    }

    /**
     * Makes a deep comparisson.
     * @since 0.1
     */
    private static class CompareObjects extends CompareJsonValues {

        @Override
        protected Boolean compare(final JsonValue first, final JsonValue second) {
            final JsonObject object = (JsonObject) first;
            final JsonObject other = (JsonObject) second;
            final boolean result;
            if (object.entrySet().size() == other.entrySet().size()) {
                result = object.entrySet()
                    .stream()
                    .map(
                        entry -> ACTIONS
                            .get(entry.getValue().getValueType())
                            .apply(entry.getValue(), other.get(entry.getKey()))
                    )
                    .allMatch(val -> Boolean.TRUE.equals(val));
            } else {
                result = false;
            }
            return result;
        }

    }

    /**
     * Compare null values.
     * @since 0.1
     */
    private static class CompareNulls extends CompareJsonValues {

        @Override
        protected Boolean compare(final JsonValue first, final JsonValue second) {
            return first.toString().equals(second.toString());
        }
    }

    /**
     * Verifies if two json values are equal.
     * @since 0.1
     */
    private abstract static class CompareJsonValues
        implements BiFunction<JsonValue, JsonValue, Boolean> {

        @Override
        public final Boolean apply(final JsonValue first, final JsonValue second) {
            final boolean equals = first.getValueType().equals(second.getValueType())
                && this.compare(first, second);
            return equals;
        }

        /**
         * Verifies if two values are equal.
         * @param first First value.
         * @param second Second value.
         * @return Boolean.
         */
        protected abstract Boolean compare(JsonValue first, JsonValue second);

    }

}
