# AI Usage Record

## Tool
- `Codex (GPT-5 coding agent)`

## AI is used for the A-AiAssisted increment
- Implemented bug fixes for:
  - `find` command boundary parsing (rejecting inputs like `findbook`).
  - Whitespace normalization across command dispatch and parser methods.
  - More robust parsing for `mark`/`unmark`/`delete` with flexible spaces.
  - Storage save error propagation (no longer silently swallowed in save).
  - Centralized goodbye text in `Ui` as a shared constant so UI and window exit check stay in sync.
- Added tests in `src/test/java/aeolian/` and verified all tests pass.
- Improved codebase through minor refactoring.

## Production Methods Changed by AI
- `src/main/java/aeolian/Aeolian.java`: `processCommand(String input)`
- `src/main/java/aeolian/Parser.java`: `isCommandWord(String userInput, String command)` (new)
- `src/main/java/aeolian/Parser.java`: `isTask(String userInput)`
- `src/main/java/aeolian/Parser.java`: `isMarkCommand(String userInput)`
- `src/main/java/aeolian/Parser.java`: `isDeleteCommand(String userInput)`
- `src/main/java/aeolian/Parser.java`: `isUnmarkCommand(String userInput)`
- `src/main/java/aeolian/Parser.java`: `isFindCommand(String userInput)`
- `src/main/java/aeolian/Parser.java`: `parseFindKeyword(String userInput)`
- `src/main/java/aeolian/Parser.java`: `parseMarkUnmarkDelete(String userInput)`
- `src/main/java/aeolian/Parser.java`: `parseTask(String userInput)`
- `src/main/java/aeolian/Storage.java`: `saveTasksToFile(String filePath, TaskList tasks)`
- `src/main/java/aeolian/MainWindow.java`: `handleUserInput()`
- `src/main/java/aeolian/Ui.java`: `GOODBYE_MESSAGE` (new constant), `showGoodbye()`

## Interesting Observations
- What worked:
  - AI was able to quickly identify and fix bugs in the codebase.
  - Wrote tests for many different cases quickly.
- What did not work / friction:
  - Running Gradle tests required elevated access to `~/.gradle` in this environment.
  - Some terminal commands were run, and it could be difficult to understand what these commands were doing.
- Time saved (estimate):
  - Roughly 90 minutes versus fully manual investigation, patching, and validation.
