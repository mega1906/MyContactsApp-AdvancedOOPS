package com.mycontactapp.command;

import com.mycontactapp.exception.ValidationException;

public interface ProfileCommand {

    void execute() throws ValidationException;
}
