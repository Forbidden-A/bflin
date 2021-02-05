/*
 * MIT License
 *
 * Copyright (c) 2021 Forbidden A
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package me.forbidden.bflin

class Interpreter(code: String, var input: String = "") {

    val code = code.toCharArray()
    val tape = ByteArray(30000)
    var pointer = 0
        private set
    var inputPointer = 0
        private set
    var output = ""
        private set

    fun run() {
        var unmatchedBrackets = 0
        var index = 0
        while (index < code.size) {
            when (code[index]) {
                '>' -> {
                    if (pointer == 29999)
                        pointer = 0
                    else
                        pointer++

                }
                '<' -> {
                    if (pointer == 0)
                        pointer = 29999
                    else
                        pointer--
                }
                '+' -> {
                    if (tape[pointer] == 255.toByte())
                        tape[pointer] = 0.toByte()
                    else
                        tape[pointer]++
                }
                '-' -> {
                    if (tape[pointer] == 0.toByte())
                        tape[pointer] = 255.toByte()
                    else
                        tape[pointer]--
                }
                ',' -> {
                    tape[pointer] = input[inputPointer].toByte()
                    inputPointer++
                    if (inputPointer == input.length)
                        inputPointer = 0
                }
                '.' -> {
                    output += tape[pointer].toChar()
                }
                '[' -> {
                    if (tape[pointer] == 0.toByte()) {
                        unmatchedBrackets++
                        while (code[index] != ']' || unmatchedBrackets != 0) {
                            index++

                            if (code[index] == '[') {
                                unmatchedBrackets++
                            } else if (code[index] == ']') {
                                unmatchedBrackets--
                            }
                        }
                    }
                }
                ']' -> {
                    if (tape[pointer] != 0.toByte()) {
                        unmatchedBrackets++
                        while (code[index] != '[' || unmatchedBrackets != 0) {
                            index--

                            if (code[index] == ']') {
                                unmatchedBrackets++
                            } else if (code[index] == '[') {
                                unmatchedBrackets--
                            }
                        }
                    }
                }
            }
            index++
        }
    }

}
