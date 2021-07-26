package com.example.kotlin2swiftapp.controllers;

import com.example.kotlin2swiftapp.VisitorKotlin;
import com.example.kotlin2swiftapp.grammar.KotlinLexer;
import com.example.kotlin2swiftapp.grammar.KotlinParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class TranslateController {

    @PostMapping("translate")
    public String translateKotlin(String input, @RequestParam(name="text") String text) throws IOException {

        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);
        KotlinLexer lexer = new KotlinLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        KotlinParser parser = new KotlinParser(tokens);
        ParseTree tree = parser.block();
        VisitorKotlin<Object> loader = new VisitorKotlin<Object>();
        loader.visit(tree);

        return new String (Files.readAllBytes(Paths.get("./out.txt")));
    }
}
