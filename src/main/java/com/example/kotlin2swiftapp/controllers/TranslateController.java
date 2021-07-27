package com.example.kotlin2swiftapp.controllers;

import com.example.kotlin2swiftapp.VisitorKotlin;
import com.example.kotlin2swiftapp.grammar.KotlinLexer;
import com.example.kotlin2swiftapp.grammar.KotlinParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class TranslateController {

    @PostMapping("translate")
    public String translateKotlin(@RequestBody String text) throws IOException {
        System.out.println(text);
        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);
        try {
            KotlinLexer lexer = new KotlinLexer(CharStreams.fromString(text));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            KotlinParser parser = new KotlinParser(tokens);
            ParseTree tree = parser.script();
            VisitorKotlin<Object> loader = new VisitorKotlin<Object>();
            loader.visit(tree);
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e);
        }
        return new String(Files.readAllBytes(Paths.get("./out.txt")));
    }
}
