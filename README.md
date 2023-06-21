# XMLTreeExpressionEvaluator

## Objectives
1. Familiarity with using recursion to evaluate arithmetic expressions.
2. Familiarity with using XMLTree objects and methods.
3. Familiarity with using NaturalNumber objects and methods.

## The Problem
Your job is to implement two versions of the evaluate static method to recursively evaluate arithmetic expressions represented as XMLTrees. One version of the method evaluates the expression with ints and the other version uses NaturalNumbers. The expressions are stored in XML format (see below) so the program can load them as XMLTrees.

### Arithmetic Expressions
The program supports arithmetic expressions with integer operands, binary operators +, –, *, and /, and parentheses (). The precedence rules are the standard ones from arithmetic (i.e., parenthesized expressions are evaluated first, then multiplicative operators, and finally additive operators).

### Format of the Input Expression XML Document
The top-level tag is expression. Nested inside there can be 5 different tags: plus, minus, times, divide, and number. The first four represent the (binary) operators and the last one represents the operands whose value is specified in the required attribute value (assume operands can only be non-negative integers). Each of the operator tags has exactly two nested tags and no attributes. There are no text nodes. Note that the parentheses in the original expression have disappeared because the XML document already "encodes" the correct order of evaluation of the operators in the given expression.
