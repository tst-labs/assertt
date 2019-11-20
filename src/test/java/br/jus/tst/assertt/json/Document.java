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
import javax.json.JsonObject;

/**
 * Json document.
 * @since 0.1
 */
public final class Document {

    /**
     * Some id.
     */
    private final int id;

    /**
     * Some date.
     */
    private final String publication;

    /**
     * Some mime type.
     */
    private final String mime;

    /**
     * Main constructor.
     *
     * @param id Some id.
     * @param publication Some date.
     * @param mime Some mime type.
     */
    public Document(final int id, final String publication, final String mime) {
        this.id = id;
        this.publication = publication;
        this.mime = mime;
    }

    /**
     * Creates a JsonObject.
     *
     * @return JsonObject.
     */
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("peca", this.id)
            .add("dataPublicacao", this.publication)
            .add("formatoOriginal", this.mime)
            .build();
    }

}
