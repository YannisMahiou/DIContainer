package example.service.impl;

import example.service.TextFormatterService;

public class SimpleTextFormatterService implements TextFormatterService {

    @Override
    public String format(String text) {
        return "Simple text: " + text;
    }

}
