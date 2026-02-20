# Aeolian Chatbot

Aeolian is a task-management chatbot written in Java with persistence storage for the tasks.

## Run
- Download the provided jar (for example `aeolian.jar`) into your desired folder.
- In terminal, go to that folder and run:
```bash
java -jar aeolian.jar
```

## Storage
- Tasks are saved to `./data/aeolian.txt`.
- Data is saved when you enter `bye`.

## Chatbot Commands

### `list`
- Show all tasks.

### `todo <description>`
- Add a todo task.
- Example: `todo read book`

### `deadline <description> /by <yyyy-MM-dd>`
- Add a deadline task.
- Date must be in `yyyy-MM-dd`.
- Example: `deadline return book /by 2026-03-01`

### `event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>`
- Add an event task with start and end dates.
- Dates must be in `yyyy-MM-dd`.
- `from` date must be before or equal to `to` date.
- Example: `event project meeting /from 2026-03-10 /to 2026-03-11`

### `mark <index>`
- Mark a task as done.
- Example: `mark 1`

### `unmark <index>`
- Mark a task as not done.
- Example: `unmark 1`

### `delete <index>`
- Delete a task.
- Example: `delete 2`

### `find <keyword>`
- Find tasks containing the keyword.
- Example: `find book`

### `help`
- Show command help.

### `bye`
- Save tasks and exit.
