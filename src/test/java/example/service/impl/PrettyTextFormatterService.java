package example.service.impl;

import example.service.TextFormatterService;

public class PrettyTextFormatterService implements TextFormatterService {

    @Override
    public String format(String text) {
        return "Pretty text: <" + text + ">";
    }

}
