Name: Kenning Spath (jct9dr)

A few notes:
- I assumed that location ids are never going to change (so if Olsson is id 3, it will always be id 3 in the API)
- I assumed tag names will always be lowercase in the API
- I assumed tags will never be removed (no ghost data will be created)
    - If tags could be removed or ids could change, I would have first deleted the database contents before inserting, rather than not deleting and just handling with on conflict ignore/replace
- I assumed it is fine that the camera position is just tracked at the activity level 

References:

- Android and Kotlin Docs
- Gemini 3.1 Pro and Claude Opus 4.6 extended for help with architectural design patterns, for debugging, as an API reference, and code generation (cited in the code). Gemini and Claude also helped me with best practices for things like flows, viewmodels, room, and retrofit
- [Google Mars code lab](https://developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet#4)
- My previous homeworks (specifically Android App 2 as a lot of the code was nearly identical so I reused/altered my implementations)
- Counters lab and in-class examples
- Everything else is cited in-line and all generated code is cited in-line

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/e4rOHRfR)
