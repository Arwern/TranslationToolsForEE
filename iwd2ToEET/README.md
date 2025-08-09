# Merge tra files for polish translation

This small app replace text from dialog2.tra file if string number exists in dialog.tra. 
For items only description is replaced. Everything from "STATISTICS:" is not changed.

There few bugs in original (dialog.tra) and translated files (dialog2.tra) like missing colons.
Several last entries in translated file (eng dialog2.tra)differ in content. There are few item descriptions that original game (dialog.tra) did not have.
And this has to be handled separately

Compilation and usages:

javac ReplaceText.java
java ReplaceText dialog2.tra dialog.tra
