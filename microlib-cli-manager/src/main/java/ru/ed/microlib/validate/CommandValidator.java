package ru.ed.microlib.validate;

import ru.ed.microlib.exception.CommandValidateException;
import ru.ed.microlib.parser.CommandDetail;

public interface CommandValidator {
    ValidateDetail validate(CommandDetail commandDetail) throws CommandValidateException;
}
