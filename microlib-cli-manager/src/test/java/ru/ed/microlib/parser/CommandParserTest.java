package ru.ed.microlib.parser;

import org.junit.Assert;
import org.junit.Test;

import ru.ed.microlib.exception.CommandParseException;

import java.util.HashMap;
import java.util.Map;

public class CommandParserTest {
    private static final String PRINT_CM = "print";

    private CommandParser commandParser = new CommandParserImpl();

    @Test
    public void parseWithoutArgumentsTest(){
        try {
            CommandDetail commandDetail = commandParser.parse(PRINT_CM);
            Assert.assertNotNull(commandDetail);
            Assert.assertEquals(PRINT_CM, commandDetail.getCommandName());
            Assert.assertEquals(new HashMap<>(), commandDetail.getCommandArguments());
        } catch (CommandParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseWithArgumentsTest(){
        try {
            CommandDetail commandDetail = commandParser.parse("connection -h localhost --port 5432");

            Assert.assertNotNull(commandDetail);
            Assert.assertEquals("connection", commandDetail.getCommandName());

            Map<String, String> commandArguments = commandDetail.getCommandArguments();
            Assert.assertEquals("localhost", commandArguments.get("-h"));
            Assert.assertEquals("5432", commandArguments.get("--port"));

        } catch (CommandParseException e) {
            e.printStackTrace();
        }
    }
}