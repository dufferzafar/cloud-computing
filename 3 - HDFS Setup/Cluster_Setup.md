
# Cluster Setup

* **VM Mapping**

```
vm1 | COL733_Chrystle_mcs172102_04   | 10.17.50.109 | Server  | baadalservervm
vm2 | COL733_Harish_mcs172074_04     | 10.17.50.169 | Desktop | baadalvm
vm3 | COL733_Shadab_mcs172076_04     | 10.17.51.41  | Server  | baadalservervm
vm4 | COL733_Keshav_csz178058_04_new | 10.17.6.91   | Desktop | baadalvm
```

## Log

* Changed passwords of all machines to: `Cl0udC`
    - Ran `make root-pass` 
    - Ran `make user-pass`
        + see `Makefile` for these commands

* Hosts file
    - On our laptops
        + `sudo su`; `cat hosts >> /etc/hosts`

    - On Baadal machines
        + `./run-all -sudo "cat /home/baadal*/hdfs-setup/hosts >> /etc/hosts"`

* SSH Config file
    - (because machines have different usernames)

    - From our laptops
        - `cat ssh-config >> ~/.ssh/config`

    - Between Baadal machines
        - `./run-all "cat ~/hdfs-setup/ssh-config > ~/.ssh/config"`

* Passwordless SSH
    - From our laptops
    
        + For `baadal` users
            * `grep -v "^#" hosts | cut -f2 | xargs -I{} ssh-copy-id -i ~/.ssh/id_rsa.pub {}`
        
        + For `root`:
            * `grep -v "^#" hosts | cut -f2 | xargs -I{} ssh-copy-id -i ~/.ssh/id_rsa.pub root@{}`

    - Between baadal machines

        + Generate RSA Keypairs
            * `./run-all ssh-keygen`

        + Copy Public keys to all machines
            * (requires you to enter the password 16 times)

            * `./run vm* "grep -v '^#' ~/hdfs-setup/hosts | cut -f2 | xargs -I{} ssh-copy-id -i ~/.ssh/id_rsa.pub {}"`

---

* Update all machines
    - `./run-all -sudo apt up{date, grade}`
    - `make restart-all`

* Install Java
    - `./run-all -sudo apt install -y "openjdk-7-{jre,jdk}"`

* Install misc. tools
    - (ranger is a CLI file manager, vim is vim)

    - `./run-all -sudo apt install -y ranger vim`

* Send local files to remote machines
    - `./run-all mkdir -p "~/hdfs-setup"`
    - `make sync`

* Send Hadoop tarball to all machines
    - `grep -v "^#" hosts | cut -f2 | xargs -P 0 -I{} rsync -aq /home/dufferzafar/dev/cloud-computing/_Help/hadoop-2.6.5.tar.gz {}:"~/"`

* Extract Hadoop tarball 
    - `./run-all "tar -xvzf ~/hadoop-2.6.5.tar.gz"`
    - `./run-all "mv ~/hadoop-2.6.5 ~/hadoop"`

* Link `.bash_profile`
    - `/run-all "ln -sf ~/hdfs-setup/.bash_profile ~/.bash_profile"`

* Link common config files
    - (could've just `cp`ed the files)

    - `./run-all "for f in \$(ls ~/hdfs-setup/hadoop); do ln -f ~/hdfs-setup/hadoop/\$f ~/hadoop/etc/hadoop/\$f; done"`

* Copy `hdfs-site.xml`
    - (which will be different at all machines)

    - `make edit-hdfs-site`

* Make `hdfs` directory
    - `./run-all "mkdir -p ~/hdfs/namenode && mkdir -p ~/hdfs/datanode"`

* Format namenode
    - `./run vm1 "~/hadoop/bin/hadoop namenode -format"`

* Change hostnames of all machines 
    - (because of some log file issue, which results in only one datanode to be visible on the UI)

    - `make change-hostname`

<!-- 

https://www.edureka.co/blog/setting-up-a-multi-node-cluster-in-hadoop-2.X

JDK 7 || Hadoop 2.6.5

Yarn

-->

## Testing for file blocks etc.

**On VM1**

* Generate 4 200 MB files
    - `seq 4 | xargs -I{} fallocate -l 200M {}.txt`

* Upload to data nodes
    - `seq 4 | xargs -I{} hdfs dfs -put {}.txt /`

* Check block distribution
    - `hdfs fsck / -files -blocks -locations`

* Shutdown VM 2
    - `./run -sudo vm2 shutdown -r now`

* Restore VM 2
    - `./run vm2 "~/hadoop/sbin/hadoop-daemon.sh" start datanode`
