# Yapper

Yapper is a chatbot created as part of NUS CS2103T Software Engineering Module.

---

# 📖 Usage Guide

---

## ⚡ General Commands

| Command | Description |
|---------|-------------|
| ``list {task | note}`` | Show the current task or note list. |
| ``find {task | note} <search_term>`` | Find tasks or notes containing `<search_term>`. |
| ``mark <task_number>`` | Mark task `<task_number>` as done. |
| ``unmark <task_number>`` | Unmark task `<task_number>` as incomplete. |
| ``delete {task | note} <index>`` | Delete task or note with `<index>` from the list. |
| ``bye`` | End the conversation with the chatbot. |
| ``help`` | Show this help menu. |

---

## 📝 Task Creation

| Command | Description |
|---------|-------------|
| ``todo <task_name>`` | Create a new task with `<task_name>`. |
| ``deadline <task_name> /by <deadline>`` | Create a Deadline task with `<deadline>`. <br>_(Format: `dd-MM-yyyy HHmm`)_ |
| ``event <task_name> /from <start_time> /to <end_time>`` | Create an Event task with `<start_time>` and `<end_time>`. <br>_(Format: `dd-MM-yyyy HHmm`)_ |

---

## 🗒️ Note Creation

| Command | Description |
|---------|-------------|
| ``note /title <title> /content <content>`` | Create a new note with `<title>` and `<content>`. |

---

## 🔄 Task Modification

| Command | Description |
|---------|-------------|
| ``reschedule <event-index> {/from <start_date_time> /to <end_date_time> | /by <end_date_time>}`` | Reschedule an event task to a new time frame. <br>_(Format: `dd-MM-yyyy HHmm`)_ |

---

> 💡 **Tip:** Always use the correct date format → `dd-MM-yyyy HHmm`
