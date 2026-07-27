[my original prompt to an LLM]
Here's my confusion:

-the handouts essentially live on the main branch of the repo: 
https://github.com/im-sandbox-mattm/copilot-workshop-foundations/tree/main/handouts

-seemingly, I need the class to be able to utilize the tagged version of the codebase:  
https://github.com/im-sandbox-mattm/copilot-workshop-foundations/tree/module-05-start AND also add the handout to this tagged version (per the client's request you hopefully caught in the transcript for Unit testing workshop)

Do I need to push the updated version to replace the original via the main branch, and then I have some options? Or essentially, at the the end of the day, to do this, I'll need a new tagged version?



---

[LLM resopnse]

Yes—your understanding is now exactly right.

The client’s complaint in Workshop 3 was that when they switched to the module tag, the handout disappeared because it existed only on main. You acknowledged that future tagged versions should include the lab instructions.

So the end state you want is:

module-05-start-v2
├── vulnerable starter code
├── workshop assets
└── handouts/module-05/README.md
Main versus the tag

Think of them as serving two related purposes:

main contains the canonical, latest collection of handouts.
The Module 05 tag is the frozen participant package for that particular lab.

Updating the handout on main does not update the existing tag. The current module-05-start tag permanently points to the older commit that lacks /handouts.

Therefore, yes: you ultimately need a new tagged version unless you delete and recreate the existing tag, which I do not recommend.

Recommended workflow

Because you still need to test the lab, I would not create the final tag quite yet.

1. Create a preparation branch from the existing tag
git fetch origin --tags

git switch -c prepare/module-05-start-v2 module-05-start

This gives you the exact current starter code.

2. Add the approved handout to that preparation branch
mkdir -p handouts/module-05
code handouts/module-05/README.md

Paste the current approved lab into the file, then:

git add handouts/module-05/README.md
git commit -m "Add Module 05 lab handout to starter package"

At this point, prepare/module-05-start-v2 contains the original starter code plus the handout.

3. Create a separate validation branch

Do not run the lab directly on the preparation branch, because Copilot will modify the vulnerable starter files.

git switch -c workshop/module-05-validation

Run the entire lab on this branch. This branch can contain fixes, generated tests, instruction files, and other participant work without contaminating the clean starter package.

4. Apply any handout corrections back to the preparation branch

After testing:

git switch prepare/module-05-start-v2

Update handouts/module-05/README.md with any corrections discovered during validation and commit them.

5. Synchronize the finalized handout to main

Once the lab is validated, put the same final handout on main:

git switch main
git pull --ff-only origin main

git checkout prepare/module-05-start-v2 -- handouts/module-05/README.md

git add handouts/module-05/README.md
git commit -m "Update Module 05 secure coding lab"
git push origin main

This preserves main as the canonical handout collection.

6. Create the new frozen tag

Return to the clean preparation branch:

git switch prepare/module-05-start-v2

git tag -a module-05-start-v2 \
  -m "Module 05 secure coding starter with participant handout"

git push origin prepare/module-05-start-v2
git push origin module-05-start-v2

Participants would then use:

git fetch origin --tags
git switch -c workshop/module-05 module-05-start-v2

They will receive both the vulnerable starter code and the handout in the same working tree.

Why not merge main into the tag-based branch?

Avoid doing that. main may contain code and files from later modules or other changes that you do not want in the Module 05 baseline.

Bring over only the handout:

git checkout main -- handouts/module-05/README.md

That preserves the exact starter-code state while solving Steven’s documentation problem.

So the answer is:

Update main so the canonical handout is current, but a new tag is still required to provide a frozen Module 05 package containing both the correct starter code and that handout.