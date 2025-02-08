# 📌 Yapper Usage Guide
---

## 💬 General Commands
- **`bye`** - End the conversation with the chatbot
- **`help`** - Show this help menu
- **`list`** - Show the current task list

---

## 📝 Task Management
- **`todo <task_name>`**
  - Create a new task with `<task_name>`

- **`deadline <task_name> /by <deadline>`**
  - Create a Deadline task with `<deadline>`
  - **Format:** `dd-MM-yyyy HHmm`

- **`event <task_name> /from <start_time> /to <end_time>`**
  - Create an Event task with `<start_time>` and `<end_time>`
  - **Format:** `dd-MM-yyyy HHmm`

---

## 🕒 Task Rescheduling
- **`reschedule <task_index> { /from <start_time> /to <end_time> | /by <end_time> }`**
  - Reschedule an Event or Deadline task
  - **Format:** `dd-MM-yyyy HHmm`

---

## 🔍 Task Searching & Editing
- **`find <search_term>`**
  - Search for tasks containing `<search_term>`

- **`mark <task_number>`**
  - Mark task with `<task_number>` as done

- **`unmark <task_number>`**
  - Unmark task with `<task_number>` as incomplete

- **`delete <task_number>`**
  - Delete task with `<task_number>` from the list

---

### 💡 Tip: Use the correct date format (`dd-MM-yyyy HHmm`)
