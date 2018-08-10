
# Assignment 1

Design of Recoverable / Fault Tolerant File System

## Problem Statement

Current file systems have directory structures which keep the File attributes and pointers to data blocks. In case of a block read error, the information lost depends on information content of the block. In case the block contains very critical information of the directory structure, the whole file systems may become inaccessible.

(1) Design a file system where the loss of information is minimised (and is ideally proportional to the no of blocks which are unreadable.)

(2) Design a completely fault tolerant file system (there is no loss of information in case some blocks are not readable)

The report should contain:

-One page brief on one the current file systems including the impact of block read error

-One/Two page description of the proposed design for (1) and its pron and cons
-One/Two page description of the proposed design for (2) and its pron and cons

Recommended process: The team works together for two hours, discusses the current file system chosen for analysis. Brain storms the new design (s) . The proposed design (s) are also analysed to evaluate to what extend the problem is addressed. And write the report for submission.

## Notes

* Read up on all kinds of filesystems that exist
    - Microsoft
        + DOS
        + NTFS

    - Linux
        + ext3/4
        + btrfs

    - Apple
        + APFS
        + The new one

* Understand their strengths & weakness

* Damages of various kinds that can happen
    - and their impact
    - how can they be recovered from
