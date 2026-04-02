Name: Kenning Spath (jct9dr)

A few notes:
- I assumed that when the directions say "Subsequent runs should only synchronize the data, not re-add it (for example, if I run your application twice, your database should still only have 1 'Alderman Library' location)" that just means not to add duplicates, but that it *is* fine to delete everything currently in the database and replace it with the remote data. I handled synchronization this way because it prevents ghost data (where a tag is removed but sticks around in the database).
- I assumed tag names will always be lowercase in the API
- I assumed it is fine that the camera position is just tracked at the activity level 

References:

- Android and Kotlin Docs
- Gemini 3.1 Pro and Claude Opus 4.6 extended for help with architectural design patterns, for debugging, as an API reference, and code generation (cited in the code). Gemini and Claude also helped me with best practices for things like flows, viewmodels, room, and retrofit
- [Google Mars code lab](https://developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet#4)
- My previous homeworks (specifically Android App 2 as a lot of the necessary code was nearly identical so I reused/altered my implementation from that homework for this one)
- Counters lab and in-class examples
- Everything else is cited in-line and all generated code is cited in-line

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/e4rOHRfR)
