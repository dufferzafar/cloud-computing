
## NTFS

NTFS (New Technology File System) is a Windows NT file system designed to quickly perform standard file operations such as read, write, and search -- and even advanced operations such as file-system recovery -- on very large hard disks.

A formatted NTFS file system results in the creation of the following system files:

Partition Boot Sector: Location of MFT and MFT mirror are stored here. To increase file system reliability the very last sector of an NTFS partition contains a spare copy of the boot sector.
Master File Table: Each file represented by a record in MFT. First record is the description of MFT itself. Second is its mirror in case the first record is corrupted. Small directory records reside entirely inside MFT. Large dir are organized into B-trees having pointers to external clusters containing dir entries.
System Files: Include above mentioned MFT, MFTmirro, Log Files, Bad cluster file, cluster bitmap etc.
File area

---

## APFS

APFS is a proprietary file system by Apple, released in 2017, to replace their old one (HFS+) 
It is designed as a general purpose file system (catered to any particular disk size) and running on all Apple operating systems like macOS, iOS, watchOS etc. 

The file system structure itself is similar to that of EXT 4 and uses 64 bit inodes. 

As far as errors are concerned, APFS makes no strict guarantees. The system does have checksums but they’re only for metadata blocks (like inode structures) and not for user’s actual data blocks. Furthermore, by default, APFS uses a copy-on-write scheme which only writes modifications of a copy to disk, which means, that there’s essentially no way of simply creating a duplicate copy of a file. 

This lack of redundancy & checksums means that if a data block in the underlying disk gets corrupted, it would result in all “copies” of that file being damaged with no way of detecting them. Such an approach will work when the disk itself has stricter guarantees about data integrity (as expensive disks usually do.)

---

## HDFS

HDFS is a distributed file system with fault tolerance built-in by design. It makes redundant copies (controlled by a replication factor) of a block in multiple data nodes, assuming commodity hardware by default. The overall procedure can be described as follows:


The assignment of blocks to nodes is controlled by a centralized Name node. 
The client probes the name node, which gives it the address of the data node to write the block. 
The client directly issues the write command to the data node, which ensures redundant copies are written before sending a “done” signal to the client. 


It is a user-level software, designed to work with heterogeneous operating systems in the data nodes.

The inherent redundancy ensures that any sorts of block errors are graciously handled.
