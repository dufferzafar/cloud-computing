
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

* Instal Java
    - `./run-all -sudo apt install -y "openjdk-7-{jre,jdk}"`

* Send local files to remote machines
    - `./run-all mkdir -p "~/hdfs-setup"`
    - `make sync`

* Send Hadoop tarball to all machines
    - `grep -v "^#" hosts | cut -f2 | xargs -P 0 -I{} rsync -aq /home/dufferzafar/dev/cloud-computing/_Help/hadoop-2.6.5.tar.gz {}:"/tmp/"`
