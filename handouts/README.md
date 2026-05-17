# Workshop Handouts

This folder contains attendee-safe handouts that match the exercise assets in this repo.

Use the handouts here together with the module reset tags and prepared review branch.

## Available Modules

- `module-01`
- `module-02`
- `module-04.1`
- `module-04.2`
- `module-05`
- `module-06.1`
- `module-06.2`

## Git Start Commands

Use a fresh local branch per code-changing module.

```bash
# module 01 is optional because it is the intro module
git switch -c workshop/module-01 module-01-start

git switch -c workshop/module-02 module-02-start
git switch -c workshop/module-04-1 module-04-1-start
git switch -c workshop/module-04-2 module-04-2-start
git switch -c workshop/module-05 module-05-start
git switch -c workshop/module-06-2 module-06-2-start

# module 06.1 uses a prepared review branch
git fetch --all --tags
git switch -c workshop/module-06-1-review review/module-06-1-candidate
```

If a module branch drifts too far, delete it and recreate it from the tag or prepared branch instead of trying to untangle it.