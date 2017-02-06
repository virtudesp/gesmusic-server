/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * sisane-server: Helps you to develop easily AJAX web applications 
 *                   by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/sisane-server
 * 
 * sisane-server is distributed under the MIT License (MIT)
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
package net.daw.helper.statics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.servlet.ServletException;

/**
 *
 * @author Alvaro
 */
public class TextParserHelper {

    /**
     *
     * @param text
     * @param url
     * @return
     * @throws Exception
     */
    public static String toHtml(String text, String url) throws Exception {
        ParserConverterHelper oParserConverter = new ParserConverterHelper();
        try {
            if (text != null) {
                if (!text.equals("")) {
                    text = oParserConverter.tag(text, "======", "h6");
                    text = oParserConverter.tag(text, "=====", "h5");
                    text = oParserConverter.tag(text, "====", "h4");
                    text = oParserConverter.tag(text, "===", "h3");
                    text = oParserConverter.tag(text, "==", "h2");
                    text = oParserConverter.tag(text, "=", "h1");
                    text = oParserConverter.tag(text, "\\*\\*", "b");
                    text = oParserConverter.tag(text, "\\$\\$", "i");
                    text = oParserConverter.a(text, url);
                    text = oParserConverter.p(text);
                }
            }
        } catch (Exception e) {
            throw new ServletException("TextParser Error: " + e.getMessage());
        }
        return text;
    }

    /**
     *
     * @param textEncode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String textEncode(String textEncode) throws UnsupportedEncodingException, ServletException {
        String encode = "";
        try {
            encode = URLEncoder.encode(textEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ServletException("Encode: " + e.getMessage());
        }
        return encode;
    }

    /**
     *
     * @param textDecode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String textDecode(String textDecode) throws UnsupportedEncodingException, ServletException {
        String decode = "";
        try {
            decode = URLDecoder.decode(textDecode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ServletException("Decode: " + e.getMessage());
        }
        return decode;
    }

    public static String forHTML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '&') {
                result.append("&amp;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\t') {
                addCharEntity(9, result);
            } else if (character == '!') {
                addCharEntity(33, result);
            } else if (character == '#') {
                addCharEntity(35, result);
            } else if (character == '$') {
                addCharEntity(36, result);
            } else if (character == '%') {
                addCharEntity(37, result);
            } else if (character == '\'') {
                addCharEntity(39, result);
            } else if (character == '(') {
                addCharEntity(40, result);
            } else if (character == ')') {
                addCharEntity(41, result);
            } else if (character == '*') {
                addCharEntity(42, result);
            } else if (character == '+') {
                addCharEntity(43, result);
            } else if (character == ',') {
                addCharEntity(44, result);
            } else if (character == '-') {
                addCharEntity(45, result);
            } else if (character == '.') {
                addCharEntity(46, result);
            } else if (character == '/') {
                addCharEntity(47, result);
            } else if (character == ':') {
                addCharEntity(58, result);
            } else if (character == ';') {
                addCharEntity(59, result);
            } else if (character == '=') {
                addCharEntity(61, result);
            } else if (character == '?') {
                addCharEntity(63, result);
            } else if (character == '@') {
                addCharEntity(64, result);
            } else if (character == '[') {
                addCharEntity(91, result);
            } else if (character == '\\') {
                addCharEntity(92, result);
            } else if (character == ']') {
                addCharEntity(93, result);
            } else if (character == '^') {
                addCharEntity(94, result);
            } else if (character == '_') {
                addCharEntity(95, result);
            } else if (character == '`') {
                addCharEntity(96, result);
            } else if (character == '{') {
                addCharEntity(123, result);
            } else if (character == '|') {
                addCharEntity(124, result);
            } else if (character == '}') {
                addCharEntity(125, result);
            } else if (character == '~') {
                addCharEntity(126, result);
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    private static void addCharEntity(Integer aIdx, StringBuilder aBuilder) {
        String padding = "";
        if (aIdx <= 9) {
            padding = "00";
        } else if (aIdx <= 99) {
            padding = "0";
        } else {
            //no prefix
        }
        String number = padding + aIdx.toString();
        aBuilder.append("&#" + number + ";");
    }
}
