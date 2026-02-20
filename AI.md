# AI Usage Record

## Tool
- `Codex (GPT-5 coding agent)`

## AI is used for the A-AiAssisted increment
- Implemented bug fixes for:
  - `find` command boundary parsing (rejecting inputs like `findbook`).
  - Whitespace normalization across command dispatch and parser methods.
  - More robust parsing for `mark`/`unmark`/`delete` with flexible spaces.
- Updated tests in `src/test/java/aeolian/ParserTest.java` and verified all tests pass.

## Production Methods Changed
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

## Interesting Observations
- What worked:
  - Existing test coverage was good and caught regressions quickly.
  - Small, targeted parser changes plus tests were straightforward and safe.
- What did not work / friction:
  - Running Gradle tests required elevated access to `~/.gradle` in this environment.
  - Some parser behavior looked correct at first glance but failed on edge whitespace/prefix cases.
- Time saved (estimate):
  - Roughly 45-90 minutes versus fully manual investigation, patching, and validation.
