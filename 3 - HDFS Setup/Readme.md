
# Fault Recovery in HDFS

## Setup

- Download and install HDFS on a set of 4 VMs in Baadal Cloud
    + Try out various features of HDFS

## Tasks

- Store 4 large files (~200 MB each)
    + Find out Data-nodes of the blocks of each file

- Shutdown one VM
    + Find out the new distribution of blocks of each file
    + Retrieve files and compare with original files

- Restore the VM
    + Check distribution of blocks

## Output

The report should contain:

- Steps used to install HDFS on 4VMs
- Distribution of file blocks 
    + before and after one VM shutdown
    + and after VM restoration
