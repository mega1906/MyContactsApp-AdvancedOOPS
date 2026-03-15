package com.mycontactapp.service;

import com.mycontactapp.command.ContactEditCommand;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContactHistoryManager {

    private static final Map<String, Deque<ContactEditCommand>> UNDO_STACKS = new HashMap<>();
    private static final Map<String, Deque<ContactEditCommand>> REDO_STACKS = new HashMap<>();

    private ContactHistoryManager() {
    }

    public static void record(String ownerUserId, ContactEditCommand command) {
        getUndoStack(ownerUserId).push(command);
        getRedoStack(ownerUserId).clear();
    }

    public static Optional<ContactEditCommand> undo(String ownerUserId) {
        Deque<ContactEditCommand> undoStack = getUndoStack(ownerUserId);

        if (undoStack.isEmpty()) {
            return Optional.empty();
        }

        ContactEditCommand command = undoStack.pop();
        command.undo();
        getRedoStack(ownerUserId).push(command);
        return Optional.of(command);
    }

    public static Optional<ContactEditCommand> redo(String ownerUserId) {
        Deque<ContactEditCommand> redoStack = getRedoStack(ownerUserId);

        if (redoStack.isEmpty()) {
            return Optional.empty();
        }

        ContactEditCommand command = redoStack.pop();
        command.redo();
        getUndoStack(ownerUserId).push(command);
        return Optional.of(command);
    }

    private static Deque<ContactEditCommand> getUndoStack(String ownerUserId) {
        return UNDO_STACKS.computeIfAbsent(ownerUserId, key -> new ArrayDeque<>());
    }

    private static Deque<ContactEditCommand> getRedoStack(String ownerUserId) {
        return REDO_STACKS.computeIfAbsent(ownerUserId, key -> new ArrayDeque<>());
    }
}
